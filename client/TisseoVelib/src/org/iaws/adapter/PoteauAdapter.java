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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PoteauAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Poteau> poteauItems;
	private WebService webservice;
	private int positionClique;
	private OnClickListener listener_horaires;
	private OnClickListener listener_like;
	private OnClickListener listener_unlike;
	private AlertDialog dialog;

	public PoteauAdapter(Context context, ArrayList<Poteau> ligneItems) {
		this.context = context;
		this.poteauItems = ligneItems;
		webservice = new WebService();

		init_listeners();
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

		Poteau poteau = poteauItems.get(position);

		TextView view_nomLigne = (TextView) convertView
				.findViewById(R.id.ligne_textview_nomLigne);
		TextView view_nomArret = (TextView) convertView
				.findViewById(R.id.ligne_textview_nomArret);
		TextView view_direction = (TextView) convertView
				.findViewById(R.id.ligne_textview_direction);

		view_nomLigne.setText(poteau.getNumLigne());
		GradientDrawable drawable = (GradientDrawable) view_nomLigne
				.getBackground();
		drawable.setColor(Color.parseColor(poteau.getLigne().getBgXmlColor()));
		view_nomLigne.setTextColor(Color.parseColor(poteau.getLigne()
				.getFgXmlColor()));
		view_nomArret.setText(poteau.getDestination().getArret().getName());
		view_direction.setText("Direction : "
				+ poteau.getDestination().getName());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				afficher_like(v);
			}
		});

		// Partie like

		LinearLayout layout = (LinearLayout) convertView
				.findViewById(R.id.ligne_linearlayout_body);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		View view_like = mInflater.inflate(R.layout.like_display, null);

		Button btn_horraire = (Button) view_like
				.findViewById(R.id.likedisplay_button_horaires);
		Button btn_like = (Button) view_like
				.findViewById(R.id.likedisplay_button_like);
		Button btn_unlike = (Button) view_like
				.findViewById(R.id.likedisplay_button_unlike);
		btn_horraire.setId(position);
		btn_horraire.setVisibility(View.VISIBLE);

		TextView text_like = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_like);
		TextView text_unlike = (TextView) view_like
				.findViewById(R.id.likedisplay_textview_unlike);

		text_like.setText(String.valueOf(poteau.get_nb_like()));
		text_unlike.setText(String.valueOf(poteau.get_nb_unlike()));

		view_like.setVisibility(View.GONE);

		if (layout.getChildCount() > 1) {
			layout.removeViewAt(1);
		}

		btn_like.setOnClickListener(listener_like);
		btn_like.setId(position);
		btn_unlike.setOnClickListener(listener_unlike);
		btn_unlike.setId(position);
		btn_horraire.setOnClickListener(listener_horaires);

		layout.addView(view_like);

		return convertView;
	}

	private class GetHorrairesTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String numArret = params[0];
			String numLigne = params[1];

			String liste_horaires = webservice.get_horaires(numLigne, numArret);

			return liste_horaires;
		}

		protected void onPostExecute(String result) {
			ParserJson parser = new ParserJson();
			List<ProchainPassage> prochainPassages = parser
					.jsonToListProchainPassage(result);
			display_horaires(prochainPassages);
		}
	}

	private void display_horaires(List<ProchainPassage> prochainPassages) {

		String message = "";
		String str;
		int count = 0;

		for (ProchainPassage prochainPassage : prochainPassages) {
			str = prochainPassage.calculerProchainPassage();
			if (str != null) {
				message += str + "\n";
				count++;
			}
		}

		if (count == 0) {
			message = traitement_no_departures();
		}

		TextView messageDialog = (TextView) dialog
				.findViewById(android.R.id.message);
		messageDialog.setText(message);

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
		listener_horaires = new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(poteauItems.get(positionClique).getNumLigne()
						+ " - "
						+ poteauItems.get(positionClique).getDestination()
								.getArret().getName());
				builder.setMessage(context.getResources().getString(
						R.string.loading)
						+ "...");
				builder.setPositiveButton("OK", null);
				dialog = builder.show();

				String idArret = poteauItems.get(v.getId()).getDestination()
						.getArret().getId();
				String idLigne = poteauItems.get(v.getId()).getLigne().getId();
				positionClique = v.getId();
				GetHorrairesTask task = new GetHorrairesTask();
				task.execute(idArret, idLigne);
			}
		};

		listener_like = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Poteau poteau = poteauItems.get(v.getId());
				String idLigne = poteau.getNumLigne();
				poteau.like();
				SendLikeUnlikeTask taskLike = new SendLikeUnlikeTask();

				String nb_like = String.valueOf(poteau.get_nb_like());
				taskLike.execute(idLigne, nb_like,
						String.valueOf(poteau.get_nb_unlike()),
						poteau.get_rev());

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
				Poteau poteau = poteauItems.get(v.getId());

				String idLigne = poteau.getNumLigne();
				poteau.unlike();
				SendLikeUnlikeTask taskLike = new SendLikeUnlikeTask();

				String nb_unlike = String.valueOf(poteau.get_nb_unlike());
				taskLike.execute(idLigne, String.valueOf(poteau.get_nb_like()),
						nb_unlike, poteau.get_rev());

				View view_grandparent = (View) v.getParent().getParent();
				RelativeLayout parent = (RelativeLayout) view_grandparent
						.findViewById(R.id.likedisplay_relativelayout_pouces);
				TextView text_unlike = (TextView) parent
						.findViewById(R.id.likedisplay_textview_unlike);
				text_unlike.setText(nb_unlike);
			}
		};
	}

	private String traitement_no_departures() {
		String nomLigne = poteauItems.get(positionClique).getLigne().getNumLigne();
		if (nomLigne.equals("A") || nomLigne.equals("B")) {
			ProchainPassage p = new ProchainPassage(nomLigne, "", "", null);
			return p.calculerProchainPassage();
		} else {
			return context.getResources().getString(R.string.depart_indispo);
		}
	}

	private class SendLikeUnlikeTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String idLigne = params[0];
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

			Ligne ligne = GestionLignes.get_instance().get_ligne(like.getId());
			ligne.set_rev(like.getRev());
		}
	}

}
