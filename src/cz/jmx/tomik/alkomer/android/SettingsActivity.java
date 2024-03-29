package cz.jmx.tomik.alkomer.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.database.DataHelper;
import cz.jmx.tomik.alkomer.android.threads.DeleteDrinksThread;

/**
 * Alkomer - Server App
 * --------------------
 * Settings Activity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class SettingsActivity extends PreferenceActivity {

	// dialogs
	static final int DIALOG_ABOUT_APP = 0;
	static final int DIALOG_RULES = 1;
	
	protected DataHelper db;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        db = new DataHelper(getApplicationContext());
        
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.layout.settings);
		
		Preference about = (Preference) findPreference("about");
		about.setOnPreferenceClickListener(new OnPreferenceClickListener() {			
			public boolean onPreferenceClick(Preference preference) {
				showDialog(DIALOG_ABOUT_APP);				
				return false;
			}
		});	
		
		Preference rules = (Preference) findPreference("rules");
		rules.setOnPreferenceClickListener(new OnPreferenceClickListener() {			
			public boolean onPreferenceClick(Preference preference) {
				showDialog(DIALOG_RULES);				
				return false;
			}
		});			
		
		Preference logout = (Preference) findPreference("logout");
		logout.setOnPreferenceClickListener(new OnPreferenceClickListener() {			
			public boolean onPreferenceClick(Preference preference) {
				db.deleteAll();
				
				ProgressDialog dialog = ProgressDialog.show(SettingsActivity.this, "", "Odhla�uji, pros�m po�kejte...", true);
				
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			    Editor editor = prefs.edit();
			    editor.putString("email", "");
			    editor.putString("password", "");
			    editor.putString("gender", "");
			    editor.putInt("weight", 0);
			    editor.commit();
		        
			    dialog.dismiss();
			    
			    Toast.makeText(getApplicationContext(), "Odhl�eno!", Toast.LENGTH_LONG).show();
			    
			    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		    	startActivity(intent);			    
			    
				return false;
			}
		});		
		
		Preference delete = (Preference) findPreference("delete");
		delete.setOnPreferenceClickListener(new OnPreferenceClickListener() {			
			public boolean onPreferenceClick(Preference preference) {
				db.deleteAll();
				
				new Thread(new DeleteDrinksThread(getApplicationContext())).start();
				
				Toast.makeText(getApplicationContext(), "Vymaz�no! :)", Toast.LENGTH_LONG).show();
			    
				return false;
			}
		});	        
		
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    db.close();
	} 	
	
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		Dialog dialog;
	    switch(id) {
	    case DIALOG_ABOUT_APP:
	    	builder.setTitle("Alkom�r "+getResources().getString(R.string.appVersion))
	    			.setMessage("Pokud naleznete n�jakou chybu, pros�m nev�hejte mne kontaktovat na e-mail!\n\n� 2011 Tom� V�tek\n\nweb: http://tomik.jmx.cz\nmail: tomik@jmx.cz\ntwitter: @tomikvitek")	    			       
	    			.setCancelable(false)
	    			.setIcon(R.drawable.icon)
	    	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   dialog.dismiss();
	    	           }
	    	       });
	    	dialog = builder.create();
	        break;
	    case DIALOG_RULES:
	    	builder.setTitle("Alkom�r "+getResources().getString(R.string.appVersion))
	    			.setMessage("Aplikace vych�z� z matematick�ch model� simuluj�c�ch spalov�n� alkoholu. Va�e skute�n� spalov�n� alkoholu z�le�� na mnoha faktorech a m��e se od toho p�edv�dan�ho touto aplikac� li�it. Aplikace neposkytuje ��dn� z�ruky, �e pokud hl�s�, �e jste vyst��zliv�li, �e tomu tak opravdu je. Pijte s rozumem.")	    			       
	    			.setCancelable(false)
	    			.setIcon(R.drawable.icon)
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
