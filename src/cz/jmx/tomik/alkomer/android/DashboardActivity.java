package cz.jmx.tomik.alkomer.android;

import java.util.Formatter;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cz.jmx.tomik.alkomer.android.tools.Calculator;

/**
 * Alkomer - Server App
 * --------------------
 * Dashboard Activity - show the promilles and ETA to sober
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class DashboardActivity extends BaseActivity {
	
	Calculator calc;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
        Button btn = (Button) findViewById(R.id.addDrink);
        btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent(DashboardActivity.this, DrinkListActivity.class);
		    	startActivity(intent);				
			}
		});
        
        Button stats = (Button) findViewById(R.id.statistics);
        stats.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent(DashboardActivity.this, StatisticsActivity.class);
		    	startActivity(intent);				
			}
		});  
        
        Button listDrunk = (Button) findViewById(R.id.listDrunk);
        listDrunk.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent(DashboardActivity.this, GlassListActivity.class);
		    	startActivity(intent);				
			}
		});        
	}
	
    @Override
    public void onResume() {    	
    	super.onResume();
    	updateInfo();
    }
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    calc.closeDatabase();
	}    
    
    public void updateInfo() {
    	calc = new Calculator(getApplicationContext());
    	
		StringBuilder sb = new StringBuilder();
		
        Formatter formatter = new Formatter(sb, Locale.ENGLISH);
        formatter.format("%.2f", calc.countPromilles());

        TextView promille = (TextView) findViewById(R.id.promille);
        TextView eta = (TextView) findViewById(R.id.eta);
        
        promille.setText(formatter.toString()+" ‰");
        
        eta.setVisibility(View.GONE);
        
        String _eta = String.valueOf(calc.countTime());
    	if (!_eta.equals("")) {
    		eta.setText(_eta);
    		eta.setVisibility(View.VISIBLE);
    	}
        
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
