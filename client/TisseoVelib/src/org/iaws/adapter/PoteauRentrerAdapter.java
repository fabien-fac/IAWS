package org.iaws.adapter;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.classes.GestionLignes;
import org.iaws.classes.Ligne;
import org.iaws.classes.LikeUnlike;
import org.iaws.classes.Poteau;
import org.iaws.classes.ProchainPassage;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.Activity;
import android.app.AlertDialog;
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

public class PoteauRentrerAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Poteau> poteauItems;

	public PoteauRentrerAdapter(Context context, ArrayList<Poteau> ligneItems) {
		this.context = context;
		this.poteauItems = ligneItems;
	}

	@Override
	public int getCount() {
		return poteauItems.size();
	}

	@Override
	public Object getItem(int position) {
		return poteauItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.ligne, null);
		}

		TextView view_nomLigne = (TextView) convertView
				.findViewById(R.id.ligne_textview_nomLigne);
		TextView view_nomArret = (TextView) convertView
				.findViewById(R.id.ligne_textview_nomArret);
		TextView view_direction = (TextView) convertView
				.findViewById(R.id.ligne_textview_direction);

		view_nomLigne.setText(poteauItems.get(position).getNumLigne());
		GradientDrawable drawable = (GradientDrawable) view_nomLigne
				.getBackground();
		drawable.setColor(Color.parseColor(poteauItems.get(position).getLigne()
				.getBgXmlColor()));
		view_nomLigne.setTextColor(Color.parseColor(poteauItems.get(position)
				.getLigne().getFgXmlColor()));
		view_nomArret.setText(poteauItems.get(position).getDestination()
				.getArret().getName());
		view_direction.setText("Direction : "
				+ poteauItems.get(position).getDestination().getName());

		// Partie like

		LinearLayout layout = (LinearLayout) convertView
				.findViewById(R.id.ligne_linearlayout_body);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		View view_like = mInflater.inflate(R.layout.like_display, null);

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

		text_like.setText(String.valueOf(poteauItems.get(position)
				.get_nb_like()));
		text_unlike.setText(String.valueOf(poteauItems.get(position)
				.get_nb_unlike()));
		text_temps.setText(poteauItems.get(position)
				.getTemps());
		text_temps.setVisibility(View.VISIBLE);

		view_like.setVisibility(View.VISIBLE);

		if (layout.getChildCount() > 1) {
			layout.removeViewAt(1);
		}

		layout.addView(view_like);

		return convertView;
	}

}
