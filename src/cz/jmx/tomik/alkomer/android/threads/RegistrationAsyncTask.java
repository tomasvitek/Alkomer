package cz.jmx.tomik.alkomer.android.threads;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.R;
import cz.jmx.tomik.alkomer.android.SettingsActivity;

/**
 * Alkomer - Server App
 * --------------------
 * Registration ASyncTask
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class RegistrationAsyncTask extends AsyncTask<String[], Void, Integer> {
	
	protected Context context;
	protected String email, password;
	
	public RegistrationAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected Integer doInBackground(String[]... arg0) {
		email = arg0[0][0];
		password = arg0[0][1];
		
		URL url = null;
        
		try {
			url = new URL("https://"+context.getResources().getString(R.integer.apiVersion)+".alko-mer.appspot.com/registration?email="+email+"&password="+password);
		} catch (MalformedURLException e) {
			Log.e("Registration Error", e.getMessage());
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
			
			return Integer.parseInt(bf.toString());
			
		} catch (IOException e) {
			Log.e("Registration Error", e.getMessage());
		}
		
		return 0;
	}
	
	protected void onPostExecute(Integer value) {		
		if (value == 1) {			
			Toast.makeText(context, "Zaregistrov�no, v�tejte!", Toast.LENGTH_LONG).show();
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

			Editor editor = prefs.edit();		
		    	editor.putString("email", email);
		    	editor.putString("password", password);
		    	editor.putBoolean("deleteInfoShown", false);

		    	editor.commit();
		    
			Intent intent = new Intent(context, SettingsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	
	    		context.startActivity(intent);			
		} else {			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    		builder.setTitle("Chyba!")
			.setMessage("U�ivatel s t�mto emailem u� existuje!")	    			       
			.setCancelable(false)
			.setIcon(R.drawable.simple_icon)
	        	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.dismiss();
		           }
		       	});
	    		Dialog d = builder.create();
	    		d.show();			
		}

    }

	
	
}
