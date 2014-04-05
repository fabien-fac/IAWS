package org.iaws.adapter;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.classes.LikeUnlike;
import org.iaws.classes.Station;
import org.iaws.parser.ParserJson;
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
	private int positionClique;

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

		Station station = stationItems.get(position);

		view_nomStation.setText(station.getNom());

		view_nbVeloText.setText(station.getTextVeloDispo());
		view_nbVeloValeur.setText(String.valueOf(station.getNbVeloDispo()));
		view_nbVeloValeur.setTextColor(station.getColorVeloDispo());

		view_nbPlaceText.setText(station.getTextPlaceDispo());
		view_nbPlaceValeur.setText(String.valueOf(station
				.calculerNbStandDIsponible()));
		view_nbPlaceValeur.setTextColor(station.getColorPlaceDispo());

		GradientDrawable drawable = (GradientDrawable) etat.getBackground();
		if (station.getOuverte()) {
			drawable.setColor(Color.GREEN);
		} else {
			drawable.setColor(Color.RED);
		}

		view_adresse.setText(station.getAdresse());

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

		text_like.setText(String.valueOf(station.get_nb_like()));
		text_unlike.setText(String.valueOf(station.get_nb_unlike()));

		if (layout.getChildCount() > 1) {
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

	private void afficher_like(View v) {

		LinearLayout li = (LinearLayout) v;
		View view = li.getChildAt(1);

		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	private void init_listeners() {

		listener_like = new OnClickListener() {

			@Override
			public void onClick(View v) {
				positionClique = v.getId();
				String idLigne = stationItems.get(v.getId()).getIdStation();
				stationItems.get(v.getId()).ajout_like(1);
				SendLikeUnlikeTask taskLike = new SendLikeUnlikeTask();

				String nb_like = String.valueOf(stationItems.get(v.getId())
						.get_nb_like());
				taskLike.execute(idLigne, nb_like, String.valueOf(stationItems
						.get(v.getId()).get_nb_unlike()),
						stationItems.get(v.getId()).getRev());

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
				positionClique = v.getId();
				String idLigne = stationItems.get(v.getId()).getIdStation();
				stationItems.get(v.getId()).ajout_unlike(1);
				SendLikeUnlikeTask taskLike = new SendLikeUnlikeTask();

				String nb_unlike = String.valueOf(stationItems.get(v.getId())
						.get_nb_unlike());
				taskLike.execute(idLigne, String.valueOf(stationItems.get(
						v.getId()).get_nb_like()), nb_unlike,
						stationItems.get(v.getId()).getRev());

				View view_grandparent = (View) v.getParent().getParent();
				RelativeLayout parent = (RelativeLayout) view_grandparent
						.findViewById(R.id.likedisplay_relativelayout_pouces);
				TextView text_unlike = (TextView) parent
						.findViewById(R.id.likedisplay_textview_unlike);
				text_unlike.setText(nb_unlike);
			}
		};
	}

	private class SendLikeUnlikeTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String idLigne = "station" + params[0];
			String nbLike = params[1];
			String nbUnLike = params[2];
			String rev = params[3];

			return webservice.send_like_unlike(idLigne, nbLike, nbUnLike, rev);

		}

		protected void onPostExecute(String json) {
			if (json == null) {
				return;
			}
			ParserJson parser = new ParserJson();
			LikeUnlike like = parser.jsonToLikeUnlike(json);
			Station station = stationItems.get(positionClique);

			if (like.getId().equals("station" + station.getIdStation())) {
				station.setRev(like.getRev());
			}
		}
	}

}
