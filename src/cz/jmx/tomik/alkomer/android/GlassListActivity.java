package cz.jmx.tomik.alkomer.android;

import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.database.DataHelper;
import cz.jmx.tomik.alkomer.android.drinks.Glass;
import cz.jmx.tomik.alkomer.android.drinks.GlassAdapter;
import cz.jmx.tomik.alkomer.android.models.User;
import cz.jmx.tomik.alkomer.android.threads.DeleteDrinkThread;

/**
 * Alkomer - Server App
 * --------------------
 * List of All Drunk Drinks ("Glasses")
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class GlassListActivity extends ListActivity {
	
	protected User user;
	protected DataHelper db;
	protected Context context;
	
	Glass g;
	Date time;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		super.onCreate(savedInstanceState);    
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    if (!prefs.getBoolean("deleteInfoShown", false)) {	    
	    	Toast.makeText(getApplicationContext(), "Tapnutím na nápoj jej mùžete smazat!", Toast.LENGTH_SHORT).show();
		    
	    	Editor editor = prefs.edit();
		    editor.putBoolean("deleteInfoShown", true);
		    editor.commit();			    	
	    }
		
		context = getApplicationContext();
         
        setContentView(R.layout.glasslist); 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
 
        ((TextView)findViewById(R.id.title)).setText("Seznam vypitých nápojù");
        
        db = new DataHelper(getApplicationContext());
        
        ArrayList<Glass> glasses;
        glasses = (ArrayList)db.selectAll();
        
        user = new User(getApplicationContext());
        
        setListAdapter(new GlassAdapter(this, R.layout.glass_list_row, glasses));
        
        getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				g = (Glass) arg0.getItemAtPosition(position);
				
				time = g.getTime();				
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(GlassListActivity.this);
				builder.setMessage("Opravdu chcete tento nápoj smazat?")
				       .setCancelable(false)
				       .setPositiveButton("Ano", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
								db.delete(g.getGlassId());
								//db.close();
								
								new Thread(new DeleteDrinkThread(getApplicationContext(), g.getDrinkId(), user, time)).start();
								
								Toast.makeText(getApplicationContext(), "Nápoj byl smazán!", Toast.LENGTH_LONG).show();
								
								GlassListActivity.this.finish();
								
								db.close();
				           }
				       })
				       .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
    }
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    db.close();
	}	

}
