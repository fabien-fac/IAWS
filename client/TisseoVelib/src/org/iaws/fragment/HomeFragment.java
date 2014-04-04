package org.iaws.fragment;
 
import org.iaws.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
 
public class HomeFragment extends Fragment {
	
	private View rootView;
	private WebView plan_view;
     
    public HomeFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
          
        init_composants();
        
        return rootView;
    }
    
    private void init_composants(){
    	
    	plan_view = (WebView) rootView.findViewById(R.id.home_webview_plan);
    	plan_view.getSettings().setBuiltInZoomControls(true);
    	plan_view.getSettings().setLoadWithOverviewMode(true);
    	plan_view.getSettings().setUseWideViewPort(true);
    	plan_view.loadUrl("file:///android_res/drawable/plan.png");
    	plan_view.setInitialScale(80);
    	
    }
}