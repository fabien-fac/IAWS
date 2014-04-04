package org.iaws.fragment;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.BDD.Bdd_Adresse;
import org.iaws.adapter.StationAdapter;
import org.iaws.adapter.StationRenterAdapter;
import org.iaws.classes.Arret;
import org.iaws.classes.Station;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
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
	private ListView listViewRapide;
	private ListView listViewFiable;

	private WebService webservice;
	private ParserJson parser;

	private String destinationSelect;

	private Bdd_Adresse bdAdresse;

	private List<Station> list_stations;
	private List<Arret> liste_arrets;

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
				if (editTextDest.getText().toString().length() > 3) {
					btnSearch.setEnabled(true);
				} else {
					btnSearch.setEnabled(false);
				}
				return false;
			}
		});

		listViewRapide = (ListView) rootView
				.findViewById(R.id.rentrer_listview_plusrapide);
		listViewFiable = (ListView) rootView
				.findViewById(R.id.rentrer_listview_plusfiable);
	}

	private void init_variables() {
		webservice = new WebService();
		parser = new ParserJson();
		bdAdresse = new Bdd_Adresse(getActivity());
		destinationSelect = bdAdresse.get_adresse();
		if (destinationSelect != null) {
			editTextDest.setText(destinationSelect);
			btnSearch.setEnabled(true);
		} else {
			destinationSelect = "";
		}
	}

	private class GetTempsVeloTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String destination = params[0];
			String liste_like = webservice.get_temps_trajet(destination,
					"bicycling");

			return liste_like;
		}

		protected void onPostExecute(String json) {
			String result = parser.jsonToTempsTrajet(json);
			System.out.println("temps velo temp : " + result);

			GetTempsBusTask task = new GetTempsBusTask();
			task.execute(destinationSelect);
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
			String result = parser.jsonToTempsTrajet(json);
			System.out.println("temps bus temp : " + result);

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
			// GetArretsTask task = new GetArretsTask();
			// task.execute(destinationSelect);

			update_listes_stations(json);
		}

	}

	private class GetArretsTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String destination = params[0];
			String liste_station = webservice.get_arrets_from_dest(destination);
			return liste_station;
		}

		protected void onPostExecute(String json) {

			liste_arrets = parser.jsonToListArretBus(json);
			loading.setVisibility(View.INVISIBLE);
			btnSearch.setVisibility(View.VISIBLE);
		}

	}

	private void update_listes_stations(String json) {

		list_stations = parser.jsonToListStation(json);
		List<Station> stations = new ArrayList<Station>();
		for (Station station : list_stations) {
			if (is_station_affichable(station)) {
				stations.add(station);
			}
		}

		StationRenterAdapter adapter = new StationRenterAdapter(getActivity(), stations);
		listViewRapide.setAdapter(adapter);

	}

	private boolean is_station_affichable(Station station) {

		if (station.getNbVeloDispo() == 0) {
			return false;
		}
		
		if(station.get_nb_unlike() > station.get_nb_like()){
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
}