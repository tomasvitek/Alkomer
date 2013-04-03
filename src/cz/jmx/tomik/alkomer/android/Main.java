package cz.jmx.tomik.alkomer.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Alkomer - Server App
 * --------------------
 * Main
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class Main extends BaseActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.title.setText("");  
        
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
  
    	Editor editor = prefs.edit();
	    editor.putBoolean("chartErrorShown", false);
	    editor.commit();        
    }
    
    @Override
    public void onResume() {    	
    	super.onResume();
    	
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String email = prefs.getString("email", "");
    	int weight = prefs.getInt("weight", 0);    	
    	String gender = prefs.getString("gender", "");
    	
		if (email.equals("")) {
	        Intent intent = new Intent(Main.this, LoginActivity.class);
	    	startActivity(intent);
			
		} else {
			if (gender.equals("") || weight == 0) {				
				Toast.makeText(getApplicationContext(), "Prosím, nastavte si pohlaví a váhu!", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(Main.this, SettingsActivity.class);
				startActivity(intent);
			}			
			else {
				Intent intent = new Intent(Main.this, DashboardActivity.class);
	    		startActivity(intent);
			}
		}    	
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
	        	startActivity(new Intent(Main.this, SettingsActivity.class));
	            return true;
	        default:
	        	return false;
        }
    }
}