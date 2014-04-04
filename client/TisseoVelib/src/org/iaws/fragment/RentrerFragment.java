package org.iaws.fragment;

import org.iaws.R;
import org.iaws.BDD.Bdd_Adresse;
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
	
	private String destinationSelect;

	private Bdd_Adresse bdAdresse;

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
				GetTempsTask task = new GetTempsTask();
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
				if(editTextDest.getText().toString().length() >3){
					btnSearch.setEnabled(true);
				}
				else{
					btnSearch.setEnabled(false);
				}
				return false;
			}
		});
		
		listViewRapide = (ListView) rootView.findViewById(R.id.rentrer_listview_plusrapide);
		listViewFiable = (ListView) rootView.findViewById(R.id.rentrer_listview_plusfiable);
	}
	
	private void init_variables(){
		webservice = new WebService();
		bdAdresse = new Bdd_Adresse(getActivity());
		destinationSelect = bdAdresse.get_adresse();
		if (destinationSelect != null) {
			editTextDest.setText(destinationSelect);
			btnSearch.setEnabled(true);
		}else{
			destinationSelect = "";
		}
	}
	
	private class GetTempsTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {
			
			String destination = params[0];
			String liste_like = webservice.get_like_unlike();

			return liste_like;
		}

		protected void onPostExecute(String result) {

			loading.setVisibility(View.INVISIBLE);
			btnSearch.setVisibility(View.VISIBLE);
		}
	}
}