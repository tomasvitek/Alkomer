package cz.jmx.tomik.alkomer.android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;

/**
 * Alkomer - Server App
 * --------------------
 * Adapter for Statistics Activity
 * (because of swiping)
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class StatisticsFlowAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	public StatisticsFlowAdapter(Activity context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.detail_statistics, null);
		}
        
		TableLayout day = (TableLayout) convertView.findViewById(R.id.day);
		TableLayout week = (TableLayout) convertView.findViewById(R.id.week);
		TableLayout month = (TableLayout) convertView.findViewById(R.id.month);
		
		day.setVisibility(View.GONE);
		week.setVisibility(View.GONE);
		month.setVisibility(View.GONE);
		
		 switch (position) {
			case 0:
				day.setVisibility(View.VISIBLE);
				break;
			case 1:
				week.setVisibility(View.VISIBLE);
				break;
			case 2:
				month.setVisibility(View.VISIBLE);
				break;
			}
						
		
        return convertView;
	}

}
