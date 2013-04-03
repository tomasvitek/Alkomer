package cz.jmx.tomik.alkomer.android.threads;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Alkomer - Server App
 * --------------------
 * Downloads Image and Displays it (ASyncTask)
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

	protected Activity screen;
	protected ImageView placer;
	
	public ImageDownloadAsyncTask(Activity screen, ImageView placer) {
		this.placer = placer;
		this.screen = screen;
	}
	
	protected void onPostExecute(Bitmap image) {
		placer.clearAnimation();		 
		placer.setImageBitmap(image);
		
		if (image == null) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(screen);
		    if (!prefs.getBoolean("chartErrorShown", false)) {	    
		    	Toast.makeText(screen, "Pøi naèítání grafù nastala chyba! Zkontrolujte, zda máte funkèní pøipojení k internetu. \n\nPokud chyba pøetrvává, zkuste to prosím pozdìji.", Toast.LENGTH_SHORT).show();
			    
		    	Editor editor = prefs.edit();
			    editor.putBoolean("chartErrorShown", true);
			    editor.commit();			    	
		    }			
		}
    }

	@Override
	protected Bitmap doInBackground(String... arg0) {
		
		String url = arg0[0];
		
		Log.d("Downloading IMAGE", url);
		
		URL myFileUrl = null; 
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			Log.e("ImageDownload Error", e.getMessage());
		}
		try {
			HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
	
			return BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			Log.e("ImageDownload Error", e.getMessage());
		}
		
		return null;		
		
	}
}