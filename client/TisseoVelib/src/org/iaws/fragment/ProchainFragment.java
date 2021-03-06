package org.iaws.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iaws.R;
import org.iaws.adapter.PoteauAdapter;
import org.iaws.classes.Arret;
import org.iaws.classes.Poteau;
import org.iaws.classes.LikeUnlike;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class ProchainFragment extends Fragment {

	private WebService webservice;

	private final String TOUTES = "\"Toutes\"";
	private final String TOUS = "\"Tous\"";

	private Set<String> liste_string_lignes;
	private Set<String> liste_string_arrets;

	private String ligne_select = TOUTES;
	private String arret_select = TOUS;

	private View rootView;
	private Spinner spinner_ligne;
	private Spinner spinner_arret;
	private ListView layout_liste;
	private PoteauAdapter adapter;
	private ArrayList<Poteau> poteauItems;
	private ProgressBar progress_load;

	private List<Arret> liste_arrets;
	private List<Poteau> liste_poteaux;

	private Map<String, LikeUnlike> mapLike;

	public ProchainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_prochain, container,
				false);

		init_variables();
		init_composants();

		GetArretsTask task = new GetArretsTask();
		task.execute();

		return rootView;
	}

	private void init_composants() {

		spinner_ligne = (Spinner) rootView
				.findViewById(R.id.prochain_spinner_ligne);
		spinner_arret = (Spinner) rootView
				.findViewById(R.id.prochain_spinner_arret);
		layout_liste = (ListView) rootView
				.findViewById(R.id.prochain_listview_liste);
		progress_load = (ProgressBar) rootView
				.findViewById(R.id.prochain_progressbar_load);

	}

	private void init_variables() {
		webservice = new WebService();
		liste_string_arrets = new HashSet<String>();
		liste_string_lignes = new HashSet<String>();
		liste_poteaux = new ArrayList<Poteau>();
	}

	private class GetArretsTask extends AsyncTask<Void, Void, String> {

		protected String doInBackground(Void... param) {
			progress_load.setVisibility(View.VISIBLE);
			String liste_arrets = webservice.get_arrets();

			return liste_arrets;
		}

		protected void onPostExecute(String result) {
			
			update_listes(result);
			
			GetLikeUnlikeTask taskLike = new GetLikeUnlikeTask();
			taskLike.execute();			
		}
	}

	private class GetLikeUnlikeTask extends AsyncTask<Void, Void, String> {

		protected String doInBackground(Void... param) {
			String liste_like = webservice.get_like_unlike();

			return liste_like;
		}

		protected void onPostExecute(String result) {
			progress_load.setVisibility(View.GONE);
			update_like(result);
			update_spinners();
			update_view_liste();
		}
	}

	private void update_listes(String json) {
		ParserJson parser = new ParserJson();
		liste_arrets = parser.jsonToListArretBus(json);

		liste_string_arrets.add(TOUS);
		liste_string_lignes.add(TOUTES);
		for (Arret arret : liste_arrets) {
			liste_string_arrets.add(arret.getName());
			liste_string_lignes.addAll(arret.get_lignes_string());
			liste_poteaux.addAll(arret.get_poteaux());
		}

	}

	private void update_spinners() {
		update_spinner_arret();
		update_spinner_ligne();

		spinner_ligne
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						ligne_select = (String) spinner_ligne.getSelectedItem();

						update_liste_arret();
						update_spinner_arret();
						update_view_liste();
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		spinner_arret
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						arret_select = (String) spinner_arret.getSelectedItem();

						update_liste_ligne();
						update_view_liste();
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
	}

	private void update_view_liste() {

		poteauItems = new ArrayList<Poteau>();
		for (Poteau poteau : liste_poteaux) {

			if (is_ligne_affichable(poteau)) {
				poteauItems.add(poteau);
			}
		}
		adapter = new PoteauAdapter(getActivity(), poteauItems);

		layout_liste.setAdapter(adapter);

	}

	private boolean is_ligne_affichable(Poteau poteau) {

		if (ligne_select.equals(TOUTES) && arret_select.equals(TOUS)) {
			return true;
		}

		if (poteau.getLigne().getNumLigne().equals(ligne_select) && arret_select.equals(TOUS)) {
			return true;
		}

		if (poteau.getDestination().getArret().getName().equals(arret_select)
				&& poteau.getLigne().getNumLigne().equals(ligne_select)) {
			return true;
		}

		if (ligne_select.equals(TOUTES)
				&& poteau.getDestination().getArret().getName()
						.equals(arret_select)) {
			return true;
		}

		return false;
	}

	private void update_liste_arret() {

		liste_string_arrets = new HashSet<String>();
		liste_string_arrets.add(TOUS);

		for (Arret arret : liste_arrets) {
			if (arret.is_arret_get_ligne(ligne_select)
					|| ligne_select.equals(TOUTES)) {
				liste_string_arrets.add(arret.getName());
			}
		}
	}

	private void update_liste_ligne() {

		liste_string_lignes = new HashSet<String>();
		liste_string_lignes.add(TOUTES);

		for (Arret arret : liste_arrets) {
			if (arret.getName().equals(arret_select)
					|| arret_select.equals(TOUS)) {
				for (Poteau poteau : arret.get_poteaux()) {
					liste_string_lignes.add(poteau.getLigne().getName());
				}
			}
		}
	}

	private void update_spinner_arret() {

		ArrayAdapter<String> dataAdapter;
		List<String> liste_a = new ArrayList<String>(liste_string_arrets);
		Collections.sort(liste_a);

		dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, liste_a);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_arret.setAdapter(dataAdapter);
	}

	private void update_spinner_ligne() {

		ArrayAdapter<String> dataAdapter;
		List<String> liste_l = new ArrayList<String>(liste_string_lignes);
		Collections.sort(liste_l);

		dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, liste_l);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_ligne.setAdapter(dataAdapter);

	}

	private void update_like(String json) {
		
		if(json == null){
			return;
		}
		
		ParserJson parser = new ParserJson();
		mapLike = parser.jsonToMapLike(json);
		for (Poteau poteau : liste_poteaux) {
			if (mapLike.containsKey(poteau.getNumLigne())) {
				LikeUnlike like = mapLike.get(poteau.getNumLigne());
				poteau.ajout_like_unlike(like);
			}
		}
	}
}