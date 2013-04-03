package cz.jmx.tomik.alkomer.android;

import cz.jmx.tomik.alkomer.android.models.User;
import cz.jmx.tomik.alkomer.android.threads.UpdateSettingsThread;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Alkomer - Server App
 * --------------------
 * BaseActivity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class BaseActivity extends Activity implements OnSharedPreferenceChangeListener {
    protected TextView title;
    protected ImageView logo;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {        
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    	
    	super.onCreate(savedInstanceState); 
         
        setContentView(R.layout.main); 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
 
        title = (TextView) findViewById(R.id.title);
        logo = (ImageView) findViewById(R.id.logo);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		new Thread(new UpdateSettingsThread(getApplicationContext(), new User(getApplicationContext()))).start();		
	}
}
