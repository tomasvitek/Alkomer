package cz.jmx.tomik.alkomer.android.drinks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cz.jmx.tomik.alkomer.android.R;

/**
 * Alkomer - Server App
 * --------------------
 * Adapter for List of All Drunk Drinks
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class GlassAdapter extends ArrayAdapter<Glass> {

    private Context context;
    private ArrayList<Glass> items;

    public GlassAdapter(Context context, int textViewResourceId, ArrayList<Glass> items) {
    		
            super(context, textViewResourceId, items);
            
            this.items = items;
            this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	Drinks drinks = new Drinks();
    	
		View v = convertView;
        
        Glass g = items.get(position);
        Drink d = drinks.findDrink(g.getDrinkId());
        
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.glass_list_row, null);
        }
        
    	TextView text = (TextView) v.findViewById(R.id.text);
    	text.setText(d.getName());
    	
    	TextView volume = (TextView) v.findViewById(R.id.volume);
    	volume.setText("("+d.getVolumeInText()+")");    	

    	SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm (d.M.)");
    	
    	TextView time = (TextView) v.findViewById(R.id.time);
    	time.setText(timeFormatter.format(g.getTime()));    	
    	
    	ImageView icon = (ImageView) v.findViewById(R.id.img);
    	
        Drawable dr = null;
    	if (d.getIconType().equals("drink_1")) dr = context.getResources().getDrawable(R.drawable.drink_1);
    	if (d.getIconType().equals("drink_2")) dr = context.getResources().getDrawable(R.drawable.drink_2);
    	if (d.getIconType().equals("drink_3")) dr = context.getResources().getDrawable(R.drawable.drink_3);
    	if (d.getIconType().equals("drink_4")) dr = context.getResources().getDrawable(R.drawable.drink_4);
    	if (d.getIconType().equals("drink_5")) dr = context.getResources().getDrawable(R.drawable.drink_5);
    	if (d.getIconType().equals("drink_6")) dr = context.getResources().getDrawable(R.drawable.drink_6);
    	if (d.getIconType().equals("drink_7")) dr = context.getResources().getDrawable(R.drawable.drink_7);
    	if (d.getIconType().equals("drink_8")) dr = context.getResources().getDrawable(R.drawable.drink_8);
    	if (d.getIconType().equals("drink_9")) dr = context.getResources().getDrawable(R.drawable.drink_9);
    	if (d.getIconType().equals("drink_10")) dr = context.getResources().getDrawable(R.drawable.drink_10);
    	if (d.getIconType().equals("drink_11")) dr = context.getResources().getDrawable(R.drawable.drink_11);
    	if (d.getIconType().equals("drink_12")) dr = context.getResources().getDrawable(R.drawable.drink_12);
    	if (d.getIconType().equals("drink_13")) dr = context.getResources().getDrawable(R.drawable.drink_13);
    	if (d.getIconType().equals("drink_14")) dr = context.getResources().getDrawable(R.drawable.drink_14);
    	if (d.getIconType().equals("drink_15")) dr = context.getResources().getDrawable(R.drawable.drink_15);
    	if (d.getIconType().equals("drink_16")) dr = context.getResources().getDrawable(R.drawable.drink_16);
    	icon.setImageDrawable(dr);

        return v;
    }
}