package cz.jmx.tomik.alkomer.android.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import cz.jmx.tomik.alkomer.android.R;
import cz.jmx.tomik.alkomer.android.models.User;

/**
 * Alkomer - Server App
 * --------------------
 * Asks a Server for New Password (ASyncTask)
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class ForgottenPasswordAsyncTask extends AsyncTask<String, Void, Integer> {
	
	static final int DIALOG_ERROR = 0;
	
	protected Context context;
	protected Activity activity;
	protected ProgressDialog dialog;
	
	public ForgottenPasswordAsyncTask(Activity activity, ProgressDialog dialog) {		
		this.context = activity.getApplicationContext();
		this.activity = activity;
		this.dialog = dialog;
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		
		User user = new User(arg0[0], null);		
		
		URL url = null;
        
		try {
			url = new URL("https://"+context.getResources().getString(R.integer.apiVersion)+".alko-mer.appspot.com/new-password?email="+user.getEmail()+"&hashcode="+user.hashCode());
		} catch (MalformedURLException e) {
			Log.e("ForgottenPassword Error", e.getMessage());
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
			Log.e("ForgottenPassword Error", e.getMessage());
		}
		
		return 0;
		
	}
	
	protected void onPostExecute(Integer value) {
		if (value == 1) {			
			dialog.dismiss();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    	builder.setTitle("Hotovo!")
			.setMessage("Heslo bylo zmìnìno a bylo vám zasláno na email.")	    			       
			.setCancelable(false)
			.setIcon(R.drawable.simple_icon)
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.dismiss();
		        	   activity.finish();
		           }
		       });
	    	Dialog d = builder.create();
	    	d.show();			
		} else {
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
			dialog.dismiss();
		}
    }
	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog;
	    switch(id) {
	    case DIALOG_ERROR:
	    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    	builder.setTitle("Chyba!")
	    			.setMessage("Pøi komunikaci se serverem došlo k chybì, ujistìte se, že máte funkèní internetové pøipojení.")	    			       
	    			.setCancelable(false)
	    			.setIcon(R.drawable.ic_menu_error)
	    	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   dialog.dismiss();
	    	           }
	    	       });
	    	dialog = builder.create();
	        break;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}	

	
	
}
