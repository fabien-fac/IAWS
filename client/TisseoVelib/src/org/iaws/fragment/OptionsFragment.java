package org.iaws.fragment;

import org.iaws.R;
import org.iaws.BDD.Bdd_Adresse;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OptionsFragment extends Fragment {

	private Button btn_valide;
	private EditText edit_adresse;
	private View rootView;
	private String adresseSave;
	private Bdd_Adresse bdAdresse;

	public OptionsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.fragment_options, container, false);

		init_composants();
		init_variables();

		return rootView;
	}

	private void init_composants() {

		btn_valide = (Button) rootView
				.findViewById(R.id.options_button_validerAdresse);
		btn_valide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				enregistrement_adresse();
			}
		});
		edit_adresse = (EditText) rootView
				.findViewById(R.id.options_editText_adresse);

	}

	private void init_variables() {
		bdAdresse = new Bdd_Adresse(getActivity());
		adresseSave = bdAdresse.get_adresse();
		if (adresseSave != null) {
			edit_adresse.setText(adresseSave);
		}else{
			adresseSave = "";
		}
	}

	private void enregistrement_adresse() {
		String adresse = edit_adresse.getText().toString();
		if (!adresseSave.equals(adresse)) {
			bdAdresse.insert_adresse(adresse);
			adresseSave = adresse;
			String message = getActivity().getResources().getString(
					R.string.adress_save);
			Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
		}
	}
}