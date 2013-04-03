package cz.jmx.tomik.alkomer.android.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.util.Log;

import cz.jmx.tomik.alkomer.android.R;
import cz.jmx.tomik.alkomer.android.models.User;

/**
 * Alkomer - Server App
 * --------------------
 * Updates User Settings on a Server (ASyncTask)
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class UpdateSettingsThread implements Runnable {

	protected User user;
	protected Context context;
	
	public UpdateSettingsThread(Context context, User user) {
		this.user = user;
		this.context = context;
	}	
	
	@Override
	public void run() {
		URL url = null;
        
		try {
			url = new URL("https://"+context.getResources().getString(R.integer.apiVersion)+".alko-mer.appspot.com/update-settings?email="+user.getEmail()+"&password="+user.getPassword()+"&gender="+user.getGender().toString()+"&weight="+user.getWeight()+"&hashcode="+user.hashCode());
		} catch (MalformedURLException e) {
			Log.e("UpdateSettings Error", e.getMessage());
		}
		
		
        URLConnection conn = null;
        
        StringBuffer bf = new StringBuffer();
        
		try {
			conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				bf.append(line);
			}
			
		} catch (IOException e) {
			Log.e("UpdateSettings Error", e.getMessage());
		}
		
	}

}
