package org.iaws.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iaws.R;
import org.iaws.adapter.StationAdapter;
import org.iaws.classes.LikeUnlike;
import org.iaws.classes.Station;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

public class VelibdispoFragment extends Fragment {

	private WebService webservice;

	private View rootView;
	private AutoCompleteTextView editName;
	private ImageButton btnSearch;
	private ListView list_view;
	private ProgressBar loading_bar;

	private List<Station> list_stations;
	private List<String> list_string_stations;

	private String station_select;

	public VelibdispoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_velibdispo, container,
				false);

		init_composants();
		init_variables();

		GetStationsTask task = new GetStationsTask();
		task.execute();

		return rootView;
	}

	private void init_composants() {

		list_view = (ListView) rootView
				.findViewById(R.id.velibdispo_listview_liste);
		editName = (AutoCompleteTextView) rootView
				.findViewById(R.id.velibdispo_edittext_nom);
		btnSearch = (ImageButton) rootView
				.findViewById(R.id.velibdispo_imgbutton_search);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				station_select = editName.getText().toString();
				update_view_stations(station_select);
			}
		});
		btnSearch.setEnabled(false);
		btnSearch.setVisibility(View.GONE);
		loading_bar = (ProgressBar) rootView
				.findViewById(R.id.velibdispo_progressBar_load);

	}

	private void init_variables() {
		webservice = new WebService();
		list_string_stations = new ArrayList<String>();
	}

	private class GetStationsTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
			loading_bar.setVisibility(View.VISIBLE);
			String liste_station = webservice.get_stations();
			return liste_station;
		}

		protected void onPostExecute(String json) {

			update_listes(json);

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
			update_like(result);
			activate_btn_search();
			update_view_stations();
		}
	}

	private void activate_btn_search() {
		loading_bar.setVisibility(View.GONE);
		btnSearch.setVisibility(View.VISIBLE);
		btnSearch.setEnabled(true);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line,
				list_string_stations);
		editName.setAdapter(adapter);
	}

	private void update_listes(String json) {
		ParserJson parser = new ParserJson();
		list_stations = parser.jsonToListStation(json);

		for (Station station : list_stations) {
			list_string_stations.add(station.getNom());
			list_string_stations.add(station.getAdresse());
		}

	}

	private void update_view_stations() {
		StationAdapter adapter = new StationAdapter(getActivity(),
				list_stations);
		list_view.setAdapter(adapter);
	}

	private void update_view_stations(String name) {
		List<Station> stations = new ArrayList<Station>();
		for (Station station : list_stations) {
			if (station.getNom().contains(name)
					|| station.getAdresse().contains(name)) {
				stations.add(station);
			}
		}

		StationAdapter adapter = new StationAdapter(getActivity(), stations);
		list_view.setAdapter(adapter);

	}

	private void update_like(String json) {

		if (json == null) {
			return;
		}
		String cle = "station";
		ParserJson parser = new ParserJson();
		Map<String, LikeUnlike> mapLike = parser.jsonToMapLike(json);
		for (Station station : list_stations) {
			if (mapLike.containsKey(cle+station.getIdStation())) {
				LikeUnlike like = mapLike.get(cle+station.getIdStation());
				station.set_nb_like(like.getLike());
				station.set_nb_unlike(like.getUnlike());
				station.setRev(like.getRev());
			}
		}
	}

}