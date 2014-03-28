package org.iaws.fragment;

import org.iaws.R;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProchainFragment extends Fragment {

	private WebService webservice;

	public ProchainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_prochain, container,
				false);

		webservice = new WebService();
		GetArretsTask c = new GetArretsTask();
		c.execute("");

		return rootView;
	}

	private class GetArretsTask extends
			AsyncTask<String, Void, String> {

		protected String doInBackground(String... urls) {
			String liste_arret = webservice.get_arrets();

			return liste_arret;
		}

		protected void onPostExecute(String result) {
			ParserJson parser = new ParserJson();
			parser.jsonToListArretBus(result);
		}

	}
}