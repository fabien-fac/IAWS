package org.iaws.adapter;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.classes.Station;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StationRenterAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Station> stationItems;

	public StationRenterAdapter(Context context, List<Station> stationItems) {
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

		Station station = stationItems.get(position);

		TextView view_nomStation = (TextView) convertView
				.findViewById(R.id.station_textview_name);
		TextView view_nbVeloText = (TextView) convertView
				.findViewById(R.id.station_textview_nbVeloDispoText);
		TextView view_nbVeloValeur = (TextView) convertView
				.findViewById(R.id.station_textview_nbVeloDispoValeur);

		TextView view_nbPlaceText = (TextView) convertView
				.findViewById(R.id.station_textview_nbPlaceDispoText);
		TextView view_nbPlaceValeur = (TextView) convertView
				.findViewById(R.id.station_textview_nbPlaceDispoValeur);
		TextView view_adresse = (TextView) convertView
				.findViewById(R.id.station_textview_adresse);

		View etat = (View) convertView.findViewById(R.id.station_etat);

		view_nomStation.setText(station.getNom());

		view_nbVeloText.setText(station.getTextVeloDispo());
		view_nbVeloValeur.setText(String.valueOf(station.getNbVeloDispo()));
		view_nbVeloValeur.setTextColor(station.getColorVeloDispo());

		view_nbPlaceText.setText(station.getTextPlaceDispo());
		view_nbPlaceValeur.setText(String.valueOf(station
				.calculerNbStandDIsponible()));
		view_nbPlaceValeur.setTextColor(station.getColorPlaceDispo());

		GradientDrawable drawable = (GradientDrawable) etat.getBackground();
		if (stationItems.get(position).getOuverte()) {
			drawable.setColor(Color.GREEN);
		} else {
			drawable.setColor(Color.RED);
		}

		view_adresse.setText(station.getAdresse());

		// Partie Like

		LinearLayout layout = (LinearLayout) convertView
				.findViewById(R.id.station_linearlayout_body);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view_like = mInflater.inflate(R.layout.like_display, null);
		view_like.setVisibility(View.VISIBLE);

		Button btn_like = (Button) view_like
				.findViewById(R.id.likedisplay_button_like);
		btn_like.setVisibility(View.GONE);
		Button btn_unlike = (Button) view_like
				.findViewById(R.id.likedisplay_button_unlike);
		btn_unlike.setVisibility(View.GONE);

		TextView text_like = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_like);
		TextView text_unlike = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_unlike);
		TextView text_temps = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_temps);

		text_like.setText(String.valueOf(station.get_nb_like()));
		text_unlike.setText(String.valueOf(station.get_nb_unlike()));
		text_temps.setText(station.getTemps());
		text_temps.setVisibility(View.VISIBLE);

		if (layout.getChildCount() > 1) {
			layout.removeViewAt(1);
		}

		layout.addView(view_like);

		return convertView;
	}

}
