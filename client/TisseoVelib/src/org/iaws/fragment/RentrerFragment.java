package org.iaws.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iaws.R;
import org.iaws.BDD.Bdd_Adresse;
import org.iaws.adapter.PoteauRentrerAdapter;
import org.iaws.adapter.StationRenterAdapter;
import org.iaws.classes.Arret;
import org.iaws.classes.LikeUnlike;
import org.iaws.classes.Poteau;
import org.iaws.classes.ProchainPassage;
import org.iaws.classes.Station;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

public class RentrerFragment extends Fragment {

	private View rootView;
	private ImageButton btnSearch;
	private ProgressBar loading;
	private EditText editTextDest;
	private ListView listViewVelo;
	private ListView listViewBusMetro;

	private TextWatcher inputTextWatcher;

	private WebService webservice;
	private ParserJson parser;

	private String destinationSelect;
	private String tempsVeloBrut;
	private String tempsBusBrut;
	private String idStop;

	private Bdd_Adresse bdAdresse;

	private List<Station> list_stations;
	private List<Arret> liste_arrets_paulSab;
	private Map<String, LikeUnlike> mapLike;
	private List<String> lignesDestination;
	private ArrayList<Poteau> poteauItems;
	private int indicePoteau;

	public RentrerFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.fragment_rentrer, container, false);

		init_composants();
		init_variables();

		return rootView;
	}

	private void init_composants() {
		btnSearch = (ImageButton) rootView
				.findViewById(R.id.rentrer_imagebutton_search);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				indicePoteau = 0;
				loading.setVisibility(View.VISIBLE);
				btnSearch.setVisibility(View.INVISIBLE);
				destinationSelect = editTextDest.getText().toString();
				GetTempsVeloTask task = new GetTempsVeloTask();
				task.execute(destinationSelect);
			}
		});

		btnSearch.setEnabled(false);
		loading = (ProgressBar) rootView
				.findViewById(R.id.rentrer_progressbar_loading);
		loading.setVisibility(View.INVISIBLE);

		editTextDest = (EditText) rootView
				.findViewById(R.id.rentrer_edittext_destination);
		editTextDest.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				return false;
			}
		});

		listViewVelo = (ListView) rootView
				.findViewById(R.id.rentrer_listview_velo);
		listViewBusMetro = (ListView) rootView
				.findViewById(R.id.rentrer_listview_busmetro);
	}

	private void init_variables() {
		webservice = new WebService();
		parser = new ParserJson();
		bdAdresse = new Bdd_Adresse(getActivity());
		destinationSelect = bdAdresse.get_adresse();
		mapLike = new HashMap<String, LikeUnlike>();
		if (destinationSelect != null) {
			editTextDest.setText(destinationSelect);
			btnSearch.setEnabled(true);
		} else {
			destinationSelect = "";
		}

		inputTextWatcher = new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (editTextDest.getText().toString().length() > 3) {
					btnSearch.setEnabled(true);
				} else {
					btnSearch.setEnabled(false);
				}
			}
		};
		editTextDest.addTextChangedListener(inputTextWatcher);
	}

	private class GetTempsVeloTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String destination = params[0];
			String liste_like = webservice.get_temps_trajet(destination,
					"bicycling");

			return liste_like;
		}

		protected void onPostExecute(String json) {
			tempsVeloBrut = parser.jsonToTempsTrajet(json);

			GetLikeUnlikeTask task = new GetLikeUnlikeTask();
			task.execute();
		}
	}

	private class GetLikeUnlikeTask extends AsyncTask<Void, Void, String> {

		protected String doInBackground(Void... param) {
			String liste_like = webservice.get_like_unlike();

			return liste_like;
		}

		protected void onPostExecute(String result) {
			update_like(result);

			GetStationsTask task = new GetStationsTask();
			task.execute();
		}
	}

	private class GetStationsTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
			String liste_station = webservice.get_stations();
			return liste_station;
		}

		protected void onPostExecute(String json) {
			GetTempsBusTask task = new GetTempsBusTask();
			task.execute(destinationSelect);

			update_listes_stations(json);
		}

	}

	private class GetTempsBusTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String destination = params[0];
			String liste_like = webservice.get_temps_trajet(destination,
					"driving");

			return liste_like;
		}

		protected void onPostExecute(String json) {

			tempsBusBrut = parser.jsonToTempsTrajet(json);

			GetArretsPaulSabTask task = new GetArretsPaulSabTask();
			task.execute();

		}
	}

	private class GetArretsPaulSabTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String liste_station = webservice.get_arrets();
			return liste_station;
		}

		protected void onPostExecute(String json) {

			liste_arrets_paulSab = parser.jsonToListArretBus(json);
			GetArretsDestinationTask task = new GetArretsDestinationTask();
			task.execute(destinationSelect);
		}

	}

	private class GetArretsDestinationTask extends
			AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String destination = params[0];
			String liste_arrets = webservice.get_arrets_from_dest(destination);
			return liste_arrets;
		}

		protected void onPostExecute(String json) {
			idStop = parser.jsonElementToIdStop(json);
			GetLignesDestinationTask task = new GetLignesDestinationTask();
			task.execute();
		}

	}

	private class GetLignesDestinationTask extends
			AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String liste_lignes = "";
			if (!idStop.equals("")) {
				liste_lignes = webservice.get_ligne_destination(idStop);
				lignesDestination = parser.jsonToListLigne(liste_lignes);
			}
			return liste_lignes;
		}

		protected void onPostExecute(String json) {
			update_view_liste();
			
			chargement_horaires();
			loading.setVisibility(View.INVISIBLE);
			btnSearch.setVisibility(View.VISIBLE);
		}

	}

	private void update_listes_stations(String json) {

		String cle = "station";

		list_stations = parser.jsonToListStation(json);
		List<Station> stations = new ArrayList<Station>();
		for (Station station : list_stations) {
			
			if (mapLike.containsKey(cle + station.getIdStation())) {
				LikeUnlike like = mapLike.get(cle + station.getIdStation());
				station.set_nb_like(like.getLike());
				station.set_nb_unlike(like.getUnlike());
				station.setRev(like.getRev());
			}
			
			if (is_station_affichable(station)) {
				station.setTemps(tempsVeloBrut);
				stations.add(station);
			}
		}

		StationRenterAdapter adapter = new StationRenterAdapter(getActivity(),
				stations);
		listViewVelo.setAdapter(adapter);

	}

	private boolean is_station_affichable(Station station) {

		if (station.getNbVeloDispo() == 0) {
			return false;
		}

		if (!station.getOuverte()) {
			return false;
		}
		
		if (station.get_nb_unlike() > station.get_nb_like()) {
			return false;
		}

		if (station.getNom().contains("166")
				|| station.getNom().contains("227")
				|| station.getNom().contains("228")
				|| station.getNom().contains("229")
				|| station.getNom().contains("230")
				|| station.getNom().contains("232")
				|| station.getNom().contains("233")) {
			return true;
		}

		return false;
	}

	private void update_like(String json) {

		if (json == null) {
			return;
		}
		mapLike = parser.jsonToMapLike(json);
	}

	private void update_view_liste() {

		Set<Poteau> listePoteaux = new HashSet<Poteau>();
		if (!idStop.equals("")) {
			for (Arret arret : liste_arrets_paulSab) {
				for (String ligne : lignesDestination) {
					if (arret.get_lignes_string().contains(ligne)) {
						for (Poteau poteau : arret.get_poteaux()) {
							if (poteau.getLigne().getNumLigne().equals(ligne)
									&& !contientLigne(ligne, listePoteaux)) {
								listePoteaux.add(poteau);
							}
						}
					}
				}
			}
		}
		poteauItems = new ArrayList<Poteau>();
		for (Poteau poteau : listePoteaux) {
			poteau.setTemps(tempsBusBrut);
			poteauItems.add(poteau);
		}
		
	}
	
	private void afficher_poteaux(){
		PoteauRentrerAdapter adapter = new PoteauRentrerAdapter(getActivity(),
				poteauItems);

		listViewBusMetro.setAdapter(adapter);
	}

	private boolean contientLigne(String ligne, Set<Poteau> listePoteau) {
		for (Poteau poteau : listePoteau) {
			if (poteau.getLigne().getNumLigne().equals(ligne)) {
				return true;
			}
		}
		return false;
	}

	
	private void chargement_horaires(){
		
		if(poteauItems.size() < 1){
			afficher_aucun_bus();
		}
		
		for (Poteau poteau : poteauItems) {
			
			String idArret = poteau.getDestination()
					.getArret().getId();
			String idLigne = poteau.getLigne().getId();
			
			GetHorrairesTask task = new GetHorrairesTask();
			task.execute(idArret, idLigne);
			
		}
	}
	
	private class GetHorrairesTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String numArret = params[0];
			String numLigne = params[1];
			
			String liste_horaires = webservice.get_horaires(numLigne, numArret);

			return liste_horaires;
		}

		protected void onPostExecute(String result) {
			ParserJson parser = new ParserJson();
			
			List<ProchainPassage> prochainPassages = parser
					.jsonToListProchainPassage(result);
			
			String tempsTotal = get_temps_totalBus(prochainPassages);
			poteauItems.get(indicePoteau).setTemps(tempsTotal);
			indicePoteau++;
			afficher_poteaux();
		}
		
		private String get_temps_totalBus(List<ProchainPassage> prochainPassages){
			String temps = tempsBusBrut.replace("mins", "");
			temps = temps.replaceAll(" ", "");
			temps = temps.replaceAll("\"", "");
			int tempsTrajet = Integer.parseInt(temps);
			String nomLigne = poteauItems.get(indicePoteau).getNumLigne();
			
			int temp;
			if(prochainPassages.size() > 0){
				temp = prochainPassages.get(0).calculerProchainPassageTemps(tempsTrajet);
			}
			else{
				if (nomLigne.equals("A") || nomLigne.equals("B")) {
					ProchainPassage p = new ProchainPassage(nomLigne, "", "", null);
					temp = p.calculerProchainPassageTemps(tempsTrajet);
				} else {
					return getResources().getString(R.string.depart_indispo);
				}
			}
			
			String tempsTotal = String.valueOf(temp) + " minutes";
			
			return tempsTotal;
		}
	}
	
	private void afficher_aucun_bus(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getActivity().getResources().getString(
				R.string.busmetro));
		builder.setMessage(getActivity().getResources().getString(
				R.string.aucunresultat));
		builder.setPositiveButton("OK", null);
		builder.show();
	}
}