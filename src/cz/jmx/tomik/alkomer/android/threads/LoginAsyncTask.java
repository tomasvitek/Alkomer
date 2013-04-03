package cz.jmx.tomik.alkomer.android.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.DashboardActivity;
import cz.jmx.tomik.alkomer.android.R;
import cz.jmx.tomik.alkomer.android.database.DataHelper;
import cz.jmx.tomik.alkomer.android.drinks.Glass;
import cz.jmx.tomik.alkomer.android.models.User;
import cz.jmx.tomik.alkomer.android.tools.DataTime;

/**
 * Alkomer - Server App
 * --------------------
 * Login ASyncTask
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class LoginAsyncTask extends AsyncTask<String[], Void, StringBuffer> {
	
	protected Context context;
	protected Activity activity;
	protected String email, password;
	protected ProgressDialog dialog;
	
	public LoginAsyncTask(Activity activity, ProgressDialog dialog) {		
		this.context = activity.getApplicationContext();
		this.activity = activity;
		this.dialog = dialog;
	}

	@Override
	protected StringBuffer doInBackground(String[]... arg0) {
		
		email = arg0[0][0];
		password = arg0[0][1];
		
		User user = new User(email, password);
		
		URL url = null;
        
		try {
			url = new URL("https://"+context.getResources().getString(R.integer.apiVersion)+".alko-mer.appspot.com/load?email="+user.getEmail()+"&password="+user.getPassword()+"&hashcode="+user.hashCode());
		} catch (MalformedURLException e) {
			Log.e("Login Error", e.getMessage());
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
			Log.e("Login Error", e.getMessage());
		}
		
		return bf;
		
	}
	
	protected void onPostExecute(StringBuffer sb) {
		
		if (sb.toString().equals("0")) {			
			dialog.dismiss();
			Toast.makeText(context, "Uživatel neexistuje nebo bylo zadáno špatné heslo!", Toast.LENGTH_LONG).show();			
		} else {
			
			ArrayList<Glass> list = new ArrayList<Glass>();
			
			final DataHelper db = new DataHelper(context);
			
			db.deleteAll();
			
			try {
				JSONObject json = new JSONObject(sb.toString());
				int weight = json.getInt("weight");
				String gender = json.getString("gender");
				
				json = (JSONObject) json.getJSONObject("drinks");				
				JSONArray ids = json.names();
				
				if (ids != null) {				
					for (int i=0; i < ids.length(); i++) {					
						JSONObject obj = (JSONObject) json.getJSONObject(ids.optString(i));
						int id = obj.getInt("id");
						String _time = obj.getString("time");
						Date time = DataTime.convertFromStringToDate(_time, context);
						
						Glass g = new Glass(time, id);
						list.add(g);
						
						db.insert(time, id);					
					}
				}
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
			    Editor editor = prefs.edit();
			    editor.putString("email", email);
			    editor.putString("password", password);
			    editor.putInt("weight", weight);
			    editor.putString("gender", gender);
			    editor.putBoolean("deleteInfoShown", false);

			    editor.commit();	
			    
				dialog.dismiss();
				Toast.makeText(context, "Vítejte!", Toast.LENGTH_LONG).show();
				//activity.finish();
				Intent intent = new Intent(activity, DashboardActivity.class);
				activity.startActivity(intent);				
				
			} catch (JSONException e) {
				Log.e("Login Error", e.getMessage());
				
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		    	builder.setTitle("Chyba!")
				.setMessage("Pøi komunikaci se serverem došlo k chybì, ujistìte se, že máte funkèní internetové pøipojení.")	    			       
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
			
			db.close();
		}
		

    }

	
	
}
