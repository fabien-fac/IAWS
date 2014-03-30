package org.iaws.fragment;
 
import org.iaws.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
 
public class OptionsFragment extends Fragment {
	
	private Button btn_valide;
	private EditText edit_adresse;
	private View rootView;
     
    public OptionsFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        rootView = inflater.inflate(R.layout.fragment_options, container, false);
          
        init_composants();
        
        return rootView;
    }
    
    private void init_composants() {

    	btn_valide = (Button) rootView
				.findViewById(R.id.options_button_validerAdresse);
    	edit_adresse = (EditText) rootView
				.findViewById(R.id.options_editText_adresse);

	}
}