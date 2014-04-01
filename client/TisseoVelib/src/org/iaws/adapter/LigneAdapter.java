package org.iaws.adapter;

import java.util.ArrayList;
import java.util.List;

import org.iaws.R;
import org.iaws.classes.ProchainPassage;
import org.iaws.model.LigneItem;
import org.iaws.parser.ParserJson;
import org.iaws.webservices.WebService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

public class LigneAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<LigneItem> ligneItems;
	private WebService webservice;
	private int positionClique;
	private OnClickListener listener_horaires;
	private OnClickListener listener_like;
	private OnClickListener listener_unlike;

	public LigneAdapter(Context context, ArrayList<LigneItem> ligneItems) {
		this.context = context;
		this.ligneItems = ligneItems;
		webservice = new WebService();

		init_listeners();
	}

	@Override
	public int getCount() {
		return ligneItems.size();
	}

	@Override
	public Object getItem(int position) {
		return ligneItems.get(position);
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

		view_nomLigne.setText(ligneItems.get(position).getNomLigne());
		GradientDrawable drawable = (GradientDrawable) view_nomLigne
				.getBackground();
		drawable.setColor(Color.parseColor(ligneItems.get(position)
				.getBgXmlColor()));
		view_nomLigne.setTextColor(Color.parseColor(ligneItems.get(position)
				.getFgXmlColor()));
		view_nomArret.setText(ligneItems.get(position).getNomArret());
		view_direction.setText("Direction : "
				+ ligneItems.get(position).getDestination());

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

		view_like.setVisibility(View.GONE);

		if (layout.getChildCount() > 1)
			layout.removeViewAt(1);

		layout.addView(view_like);

		btn_like.setOnClickListener(listener_like);
		btn_unlike.setOnClickListener(listener_unlike);
		btn_horraire.setOnClickListener(listener_horaires);

		return convertView;
	}

	private class GetHorrairesTask extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... params) {

			String numArret = params[0];
			String numLigne = params[1];
			
			System.out.println("arret : " + numArret);
			System.out.println("ligne : " + numLigne);

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
			message = context.getResources().getString(R.string.depart_indispo);
		}
		
		new AlertDialog.Builder(context)
				.setTitle(
						ligneItems.get(positionClique).getNomLigne() + " "
								+ ligneItems.get(positionClique).getNomArret())
				.setMessage(message)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).setIcon(R.drawable.clock).show();
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
				String idArret = ligneItems.get(v.getId()).getIdArret();
				String idLigne = ligneItems.get(v.getId()).getIdLigne();
				positionClique = v.getId();
				GetHorrairesTask task = new GetHorrairesTask();
				task.execute(idArret, idLigne);
			}
		};

		listener_like = new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("like");
			}
		};

		listener_unlike = new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("unlike");
			}
		};
	}

}
