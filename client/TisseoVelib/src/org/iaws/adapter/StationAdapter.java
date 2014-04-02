package org.iaws.adapter;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.classes.Station;
import org.iaws.webservices.WebService;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StationAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Station> stationItems;
	private OnClickListener listener_like;
	private OnClickListener listener_unlike;
	private WebService webservice;

	public StationAdapter(Context context, List<Station> stationItems) {
		this.context = context;
		this.stationItems = new ArrayList<Station>(stationItems);
		webservice = new WebService();
		init_listeners();
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
		
		
		Button btn_like = (Button) view_like
				.findViewById(R.id.likedisplay_button_like);
		Button btn_unlike = (Button) view_like
				.findViewById(R.id.likedisplay_button_unlike);

		TextView text_like = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_like);
		TextView text_unlike = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_unlike);

		text_like.setText(String
				.valueOf(stationItems.get(position).get_nb_like()));
		text_unlike.setText(String.valueOf(stationItems.get(position)
				.get_nb_unlike()));
		
		if(layout.getChildCount()>1){
			layout.removeViewAt(1);
		}
		
		btn_like.setOnClickListener(listener_like);
		btn_like.setId(position);
		btn_unlike.setOnClickListener(listener_unlike);
		btn_unlike.setId(position);
		
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
	
	private void init_listeners() {
		
		listener_like = new OnClickListener() {

			@Override
			public void onClick(View v) {
				String idLigne = stationItems.get(v.getId()).getIdStation();
				stationItems.get(v.getId()).ajout_like(1);
				SendLikeUnlikeTask taskLike = new SendLikeUnlikeTask();
				
				String nb_like = String.valueOf(stationItems.get(v.getId())
						.get_nb_like());
				taskLike.execute(idLigne, nb_like, String.valueOf(stationItems.get(v.getId())
						.get_nb_unlike()));

				View view_grandparent = (View) v.getParent().getParent();
				RelativeLayout parent = (RelativeLayout) view_grandparent
						.findViewById(R.id.likedisplay_relativelayout_pouces);
				TextView text_like = (TextView) parent
						.findViewById(R.id.likedisplay_textview_like);
				text_like.setText(nb_like);

			}
		};

		listener_unlike = new OnClickListener() {

			@Override
			public void onClick(View v) {
				String idLigne = stationItems.get(v.getId()).getIdStation();
				stationItems.get(v.getId()).ajout_unlike(1);
				SendLikeUnlikeTask taskLike = new SendLikeUnlikeTask();
				
				String nb_unlike = String.valueOf(stationItems.get(v.getId())
						.get_nb_unlike());
				taskLike.execute(idLigne, String.valueOf(stationItems.get(v.getId())
						.get_nb_like()), nb_unlike);

				View view_grandparent = (View) v.getParent().getParent();
				RelativeLayout parent = (RelativeLayout) view_grandparent
						.findViewById(R.id.likedisplay_relativelayout_pouces);
				TextView text_unlike = (TextView) parent
						.findViewById(R.id.likedisplay_textview_unlike);
				text_unlike.setText(nb_unlike);
			}
		};
	}
	
	private class SendLikeUnlikeTask extends AsyncTask<String, Void, Void> {

		protected Void doInBackground(String... params) {

			String idLigne = params[0];
			String nbLike = params[1];
			String nbUnLike = params[2];
			
			webservice.send_like_unlike(idLigne, nbLike, nbUnLike);
			
			return null;

		}

		protected void onPostExecute(Void param) {
			System.out.println("envoyé");
		}
	}

}
