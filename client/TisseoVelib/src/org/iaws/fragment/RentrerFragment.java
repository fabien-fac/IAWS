package org.iaws.fragment;

import org.iaws.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class RentrerFragment extends Fragment {
     
    public RentrerFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_rentrer, container, false);
          
        return rootView;
    }
}