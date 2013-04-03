package cz.jmx.tomik.alkomer.android;

import java.util.Formatter;
import java.util.Locale;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import cz.jmx.tomik.alkomer.android.threads.ImageDownloadAsyncTask;
import cz.jmx.tomik.alkomer.android.tools.Calculator;

/**
 * Alkomer - Server App
 * --------------------
 * Statistics Activity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class StatisticsActivity extends BaseActivity {
	
	private ViewFlow viewFlow;
	
	private Calculator calc;
	
	private boolean dayStatsLoaded = false;
	private boolean weekStatsLoaded = false;
	private boolean monthStatsLoaded = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		viewFlow.setAdapter(new StatisticsFlowAdapter(this));
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		indic.setFillColor(0xff888888);
		indic.setStrokeColor(0xff888888);
		indic.setBackgroundColor(0xffffffff);
		viewFlow.setFlowIndicator(indic);
		viewFlow.setOnViewSwitchListener(new ViewSwitchListener() {

			@Override
			public void onSwitched(View view, int position) {
				loadStatistics(view, position);				
			}
		});
        
		loadStatistics(viewFlow, 0);
		
        this.title.setText("Statistiky");
	}
	
	public void loadStatistics(View view, int position) {
		calc = new Calculator(StatisticsActivity.this);
        
		ProgressDialog dialog = ProgressDialog.show(StatisticsActivity.this, "", "Generuji statistiky, prosím poèkejte...", true);
		dialog.show();
    	
		TableLayout day = (TableLayout) view.findViewById(R.id.day);
		TableLayout week = (TableLayout) view.findViewById(R.id.week);
		TableLayout month = (TableLayout) view.findViewById(R.id.month);
		
        TextView list = (TextView) view.findViewById(R.id.list);
        TextView listLabel = (TextView) view.findViewById(R.id.label1);
        
        TextView day_count = (TextView) view.findViewById(R.id.day_count);
        TextView day_max = (TextView) view.findViewById(R.id.day_max);
        TextView day_types = (TextView) view.findViewById(R.id.day_types);
        TextView day_alc = (TextView) view.findViewById(R.id.day_alc);
        
        TextView week_count = (TextView) view.findViewById(R.id.week_count);
        TextView week_max = (TextView) view.findViewById(R.id.week_max);
        TextView week_types = (TextView) view.findViewById(R.id.week_types);
        TextView week_alc = (TextView) view.findViewById(R.id.week_alc);
        
        TextView month_count = (TextView) view.findViewById(R.id.month_count);
        TextView month_max = (TextView) view.findViewById(R.id.month_max);
        TextView month_types = (TextView) view.findViewById(R.id.month_types);
        TextView month_alc = (TextView) view.findViewById(R.id.month_alc);        
        
        TextView chartLabel = (TextView) view.findViewById(R.id.label3);
        ImageView chart1 = (ImageView) view.findViewById(R.id.day_graf_1);
        ImageView chart2 = (ImageView) view.findViewById(R.id.day_graf_2);
        
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.ENGLISH);
        
        ImageView placerPie, placerLine;
        String urlPie, urlLine;
        
        switch (position) {
		case 0:
			// Last 1 day
			if (!dayStatsLoaded) {
				dayStatsLoaded = true;
				
				day.setVisibility(View.VISIBLE);
				week.setVisibility(View.GONE);
				month.setVisibility(View.GONE);
				
		        calc.processDayAgo();
		        
		        chartLabel.setVisibility(View.GONE);
		        chart1.setVisibility(View.GONE);
		        chart2.setVisibility(View.GONE);
		        
		        list.setVisibility(View.GONE);
		        listLabel.setVisibility(View.GONE);
		        
		        if (calc.getTotalDrinks() > 0) {
		        	list.setText(String.valueOf(calc.getListOfDrinks()));
		        	list.setVisibility(View.VISIBLE);
		        	listLabel.setVisibility(View.VISIBLE);
		        	
		            chartLabel.setVisibility(View.VISIBLE);
		            chart1.setVisibility(View.VISIBLE);        	
		            chart2.setVisibility(View.VISIBLE);
		        }        
		        
		        sb = new StringBuilder();
		        
		        formatter.format("%.2f", calc.getMaximumAlcoholLevel());
		        
		        day_count.setText("Poèet vypitých nápojù: " + calc.getTotalDrinks());
		        day_max.setText("Max. obj. alk. v krvi: " + formatter.toString()+" ‰");
		        day_types.setText("Poèet druhù nápojù: " + calc.getDrinkTypesCount());
		        day_alc.setText("Vypito èistého alkoholu: " + calc.getTotalAlcoholAmount()+" ml");
		        
		        placerPie = (ImageView) view.findViewById(R.id.day_graf_1);        
		        placerPie.startAnimation(AnimationUtils.loadAnimation(StatisticsActivity.this, R.anim.rotate_wheel));
		        urlPie = String.valueOf(calc.getPieChartURL());        
		        new ImageDownloadAsyncTask(StatisticsActivity.this, placerPie).execute(urlPie);
		        
		        placerLine = (ImageView) view.findViewById(R.id.day_graf_2);
		        placerLine.startAnimation(AnimationUtils.loadAnimation(StatisticsActivity.this, R.anim.rotate_wheel));
		        urlLine = String.valueOf(calc.getLineChartURL());        
		        new ImageDownloadAsyncTask(StatisticsActivity.this, placerLine).execute(urlLine);
		        
		        placerPie.setImageResource(R.drawable.spinner_black);
		        placerLine.setImageResource(R.drawable.spinner_black);			
			}
			
			break;
			
		case 1:
			// Last week
			
			if (!weekStatsLoaded) {			
				weekStatsLoaded = true;
			
				day.setVisibility(View.GONE);
				week.setVisibility(View.VISIBLE);
				month.setVisibility(View.GONE);					
				
		        calc.processWeekAgo();     
		        
		        sb = new StringBuilder();
		        formatter = new Formatter(sb, Locale.ENGLISH);
		        formatter.format("%.2f", calc.getMaximumAlcoholLevel());
		        
		        week_count.setText("Poèet vypitých nápojù: " + calc.getTotalDrinks());
		        week_max.setText("Max. obj. alk. v krvi: " + formatter.toString()+" ‰");
		        week_types.setText("Poèet druhù nápojù: " + calc.getDrinkTypesCount());
		        week_alc.setText("Vypito èistého alkoholu: " + calc.getTotalAlcoholAmount()+" ml");
		        
		        placerPie = (ImageView) view.findViewById(R.id.day_graf_3);
		        placerPie.startAnimation(AnimationUtils.loadAnimation(StatisticsActivity.this, R.anim.rotate_wheel));
		        urlPie = String.valueOf(calc.getPieChartURL());        
		        new ImageDownloadAsyncTask(StatisticsActivity.this, placerPie).execute(urlPie);
		        
		        placerPie.setVisibility(View.VISIBLE);
		        if (calc.getTotalDrinks() == 0) placerPie.setVisibility(View.GONE);
		        
		        placerLine = (ImageView) view.findViewById(R.id.day_graf_4);
		        placerLine.startAnimation(AnimationUtils.loadAnimation(StatisticsActivity.this, R.anim.rotate_wheel));
		        urlLine = String.valueOf(calc.getLineChartURL());        
		        new ImageDownloadAsyncTask(StatisticsActivity.this, placerLine).execute(urlLine);  
		        
		        placerPie.setImageResource(R.drawable.spinner_black);
		        placerLine.setImageResource(R.drawable.spinner_black);  	
			}
			
			break;
			
		case 2:
	        // Last month
			
			if (!monthStatsLoaded) {
				
				monthStatsLoaded = true;
				
				day.setVisibility(View.GONE);
				week.setVisibility(View.GONE);
				month.setVisibility(View.VISIBLE);					
				
		        calc.processMonthAgo();     
		        
		        sb = new StringBuilder();
		        formatter = new Formatter(sb, Locale.ENGLISH);
		        formatter.format("%.2f", calc.getMaximumAlcoholLevel());
		        
		        month_count.setText("Poèet vypitých nápojù: " + calc.getTotalDrinks());
		        month_max.setText("Max. obj. alk. v krvi: " + formatter.toString()+" ‰");
		        month_types.setText("Poèet druhù nápojù: " + calc.getDrinkTypesCount());
		        month_alc.setText("Vypito èistého alkoholu: " + calc.getTotalAlcoholAmount()+" ml");
		        
		        placerPie = (ImageView) view.findViewById(R.id.day_graf_5);
		        placerPie.startAnimation(AnimationUtils.loadAnimation(StatisticsActivity.this, R.anim.rotate_wheel));
		        urlPie = String.valueOf(calc.getPieChartURL());        
		        new ImageDownloadAsyncTask(StatisticsActivity.this, placerPie).execute(urlPie);
		        
		        placerPie.setVisibility(View.VISIBLE);
		        if (calc.getTotalDrinks() == 0) placerPie.setVisibility(View.GONE);		        
		        
		        placerLine = (ImageView) view.findViewById(R.id.day_graf_6);
		        placerLine.startAnimation(AnimationUtils.loadAnimation(StatisticsActivity.this, R.anim.rotate_wheel));
		        urlLine = String.valueOf(calc.getLineChartURL());        
		        new ImageDownloadAsyncTask(StatisticsActivity.this, placerLine).execute(urlLine);              
		        
		        placerPie.setImageResource(R.drawable.spinner_black);
		        placerLine.setImageResource(R.drawable.spinner_black);  
			}
			
			break;

		default:
			break;
		}
        
        dialog.dismiss();
        
        //Toast.makeText(StatisticsActivity.this, "Chvilinku strpení bìhem naèítání grafù.", Toast.LENGTH_LONG).show();
        
        calc.closeDatabase();		
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.menu_settings:
	        	startActivity(new Intent(this, SettingsActivity.class));
	            return true;
	        default:
	        	return false;
        }
    }    
}
