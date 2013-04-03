package cz.jmx.tomik.alkomer.android.drinks;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cz.jmx.tomik.alkomer.android.R;

/**
 * Alkomer - Server App
 * --------------------
 * Adapter for List of All Types of Drinks
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class DrinkAdapter extends BaseExpandableListAdapter {

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    private Context context;

    private ArrayList<String> groups;

    private ArrayList<ArrayList<Drink>> children;

    public DrinkAdapter(Context context, ArrayList<String> groups,
            ArrayList<ArrayList<Drink>> children) {
        this.context = context;
        this.groups = groups;
        this.children = children;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    // Return a child view. You can load your custom layout here.
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
    	
        Drink d = (Drink) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drink_list_row, null);
        }
        
    	TextView text = (TextView) convertView.findViewById(R.id.text);
    	text.setText(d.getName());

    	TextView volume = (TextView) convertView.findViewById(R.id.volume);
    	volume.setText(d.getVolumeInText());        	
    	
    	ImageView icon = (ImageView) convertView.findViewById(R.id.img);
    	
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
    	
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Return a group view. You can load your custom layout here.
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drink_list_group, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(group);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}