package cz.jmx.tomik.alkomer.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.threads.LoginAsyncTask;
import cz.jmx.tomik.alkomer.android.tools.EmailValidator;

/**
 * Alkomer - Server App
 * --------------------
 * Login Form Activity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class LoginActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.login);
        this.title.setText("Pøihlášení");
        this.logo.setVisibility(View.GONE);
        
        TextView reg = (TextView) findViewById(R.id.registration);
        reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
		    	startActivity(intent);
			}
        });

        TextView pass = (TextView) findViewById(R.id.forgottenPassword);
        pass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		        Intent intent = new Intent(getBaseContext(), ForgottenPasswordActivity.class);
		    	startActivity(intent);
			}
        });        
        
        
        Button log = (Button) findViewById(R.id.login);
        log.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				TextView utext = (TextView) findViewById(R.id.email);
				TextView ptext = (TextView) findViewById(R.id.password);
				
				String email = utext.getText().toString();
				String password = ptext.getText().toString();
				
				if (EmailValidator.validate(email)) {				
					if (!password.equals("")) {
						
						ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "", "Pøihlašuji...", true);
						
						String[] data = {email, password};
						new LoginAsyncTask(LoginActivity.this, dialog).execute(data);						
					}
					else {
						Toast.makeText(LoginActivity.this, "Heslo nesmí být prázdné!", Toast.LENGTH_LONG).show();
					}							
				}
				else {
					Toast.makeText(LoginActivity.this, "Zadejte platný email!", Toast.LENGTH_LONG).show();
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
