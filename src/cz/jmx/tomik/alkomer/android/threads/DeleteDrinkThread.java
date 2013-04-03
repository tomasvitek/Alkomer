package cz.jmx.tomik.alkomer.android.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import cz.jmx.tomik.alkomer.android.R;
import cz.jmx.tomik.alkomer.android.models.User;
import cz.jmx.tomik.alkomer.android.tools.DataTime;

import android.content.Context;
import android.util.Log;

/**
 * Alkomer - Server App
 * --------------------
 * Send a Drink to a Server (Thread)
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class DeleteDrinkThread implements Runnable {

	protected int drinkId;
	protected User user;
	protected Context context;
	protected Date time;
	
	public DeleteDrinkThread(Context context, int drinkId, User user, Date time) {
		this.context = context;
		this.drinkId = drinkId;
		this.user = user;
		this.time = time;
	}
	
	@Override
	public void run() {
		URL url = null;
        
		try {
			url = new URL("https://"+context.getResources().getString(R.integer.apiVersion)+".alko-mer.appspot.com/delete-drink?drinkId="+drinkId+"&time="+URLEncoder.encode(DataTime.convertFromDateToString(time))+"&email="+user.getEmail()+"&hashcode="+user.hashCode());
		} catch (MalformedURLException e) {
			Log.e("Delete Drink Error", e.getMessage());
		}		
		
        URLConnection conn = null;
        
        StringBuffer bf = new StringBuffer();
        
		try {
			conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				bf.append(line);			}

			
		} catch (IOException e) {
			Log.e("Delete Drink Error", e.getMessage());
		}
		
	}

}
