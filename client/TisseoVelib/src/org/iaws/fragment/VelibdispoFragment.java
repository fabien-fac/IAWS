package org.iaws.fragment;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.adapter.LigneAdapter;
import org.iaws.adapter.StationAdapter;
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

public class VelibdispoFragment extends Fragment {

	private WebService webservice;

	private View rootView;
	private AutoCompleteTextView editName;
	private ImageButton btnSearch;
	private ListView list_view;

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

		GetArretsTask task = new GetArretsTask();
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
				update_view_stations();
			}
		});
		btnSearch.setEnabled(false);

	}

	private void init_variables() {
		webservice = new WebService();
		list_string_stations = new ArrayList<String>();
	}

	private class GetArretsTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
			String liste_station = webservice.get_stations();
			return liste_station;
		}

		protected void onPostExecute(String json) {
			update_listes(json);
			activate_btn_search();
		}

	}

	private void activate_btn_search() {
		list_string_stations.add("toulouse");
		list_string_stations.add("24 rue de lolz");
		list_string_stations.add("2 RUE GATIEN ARNOULT");
		list_string_stations.add("BRUNHES-FONTAINES");

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
			// list_string_stations.add(station);
		}

	}

	private void update_view_stations() {
		StationAdapter adapter = new StationAdapter(getActivity(), list_stations);
		list_view.setAdapter(adapter);
	}

	private boolean is_station_affichable(Station station) {
		return true;
	}
}