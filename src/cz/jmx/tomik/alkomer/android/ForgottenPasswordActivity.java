package cz.jmx.tomik.alkomer.android;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.threads.ForgottenPasswordAsyncTask;
import cz.jmx.tomik.alkomer.android.tools.EmailValidator;

/**
 * Alkomer - Server App
 * --------------------
 * Forgotten Password Form Activity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class ForgottenPasswordActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgottenpassword);
        this.title.setText("Zaslání nového hesla");        
        
        Button reg = (Button) findViewById(R.id.regbtn);
        reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				TextView utext = (TextView) findViewById(R.id.email);
				String email = utext.getText().toString();
				
				if (EmailValidator.validate(email)) {				
					ProgressDialog dialog = ProgressDialog.show(ForgottenPasswordActivity.this, "", "Prosím, èekejte...", true);
				    
					new ForgottenPasswordAsyncTask(ForgottenPasswordActivity.this, dialog).execute(email);
				}
				else {
					Toast.makeText(ForgottenPasswordActivity.this, "Zadejte platný email!", Toast.LENGTH_LONG).show();
				}
			}
		});
    }
    
    @Override
    public void onResume() { 
    	super.onResume();
    	
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String email = prefs.getString("email", "");
		
		if (!email.equals("")) {
	        finish();
		}
    }    
    
}
