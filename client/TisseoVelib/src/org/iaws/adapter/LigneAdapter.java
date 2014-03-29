package org.iaws.adapter;

import java.util.ArrayList;

import org.iaws.R;
import org.iaws.model.LigneItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LigneAdapter extends BaseAdapter{
	
	private Context context;
    private ArrayList<LigneItem> ligneItems;
    
    public LigneAdapter(Context context, ArrayList<LigneItem> ligneItems){
        this.context = context;
        this.ligneItems = ligneItems;
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
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ligne, null);
        }
          
        TextView view_nomLigne = (TextView) convertView.findViewById(R.id.ligne_textview_nomLigne);
        TextView view_nomArret = (TextView) convertView.findViewById(R.id.ligne_textview_nomArret);
        TextView view_direction = (TextView) convertView.findViewById(R.id.ligne_textview_direction);
          
        view_nomLigne.setText(ligneItems.get(position).getNomLigne());
        GradientDrawable drawable = (GradientDrawable) view_nomLigne.getBackground();
        drawable.setColor(Color.parseColor(ligneItems.get(position).getBgXmlColor()));
        view_nomLigne.setTextColor(Color.parseColor(ligneItems.get(position).getFgXmlColor()));
        view_nomArret.setText(ligneItems.get(position).getNomArret());
        view_direction.setText("Direction : " + ligneItems.get(position).getDestination());
         
        /*
        if(navDrawerItems.get(position).getCounterVisibility()){
            txtCount.setText(ligneItems.get(position).getCount());
        }else{
            txtCount.setVisibility(View.GONE);
        }
         */
        
        return convertView;
	}

}
