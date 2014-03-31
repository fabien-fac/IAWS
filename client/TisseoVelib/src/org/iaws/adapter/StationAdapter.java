package org.iaws.adapter;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.classes.Station;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StationAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Station> stationItems;

	public StationAdapter(Context context, List<Station> stationItems) {
		this.context = context;
		this.stationItems = new ArrayList<Station>(stationItems);
	}

	@Override
	public int getCount() {
		return stationItems.size();
	}

	@Override
	public Object getItem(int position) {
		return stationItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.station, null);
		}

		TextView view_nomStation = (TextView) convertView
				.findViewById(R.id.station_textview_name);
		TextView view_nbVelo = (TextView) convertView
				.findViewById(R.id.station_textview_nbVeloDispo);
		TextView view_nbPlace = (TextView) convertView
				.findViewById(R.id.station_textview_nbPlaceDispo);
		TextView view_adresse = (TextView) convertView
				.findViewById(R.id.station_textview_adresse);

		View etat = (View) convertView.findViewById(R.id.station_etat);

		view_nomStation.setText(stationItems.get(position).getNom());

		String str = context.getResources().getString(
				R.string.station_velo_dispo)
				+ " ";
		str += String.valueOf(stationItems.get(position).getNbVeloDispo());
		view_nbVelo.setText(str);

		str = context.getResources().getString(R.string.station_place_dispo)
				+ " ";
		str += String.valueOf(stationItems.get(position)
				.calculerNbStandDIsponible());
		view_nbPlace.setText(str);

		GradientDrawable drawable = (GradientDrawable) etat.getBackground();
		if (stationItems.get(position).getOuverte()) {
			drawable.setColor(Color.GREEN);
		} else {
			drawable.setColor(Color.RED);
		}

		view_adresse.setText(stationItems.get(position).getAdresse());
		
		
		// Partie like
		
		LinearLayout layout = (LinearLayout) convertView
				.findViewById(R.id.station_linearlayout_body);
		
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view_like = mInflater.inflate(R.layout.like_display, null);
		view_like.setVisibility(View.GONE);
		
		layout.addView(view_like);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				afficher_like(v);
			}
		});

		return convertView;
	}
	
	private void afficher_like(View v){
		
		LinearLayout li = (LinearLayout) v;
		View view = li.getChildAt(1);
		
		if(view.getVisibility() == View.VISIBLE){
			view.setVisibility(View.GONE);
		}
		else{
			view.setVisibility(View.VISIBLE);
		}
	}

}
