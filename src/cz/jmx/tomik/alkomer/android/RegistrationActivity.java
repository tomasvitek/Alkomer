package cz.jmx.tomik.alkomer.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cz.jmx.tomik.alkomer.android.threads.RegistrationAsyncTask;
import cz.jmx.tomik.alkomer.android.tools.EmailValidator;

/**
 * Alkomer - Server App
 * --------------------
 * Registration Form Activity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class RegistrationActivity extends BaseActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        this.title.setText("Registrace");        
        
        Button reg = (Button) findViewById(R.id.regbtn);
        reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				TextView utext = (TextView) findViewById(R.id.email);
				TextView ptext = (TextView) findViewById(R.id.password);
				TextView p2text = (TextView) findViewById(R.id.password2);
				
				String email = utext.getText().toString();
				String password = ptext.getText().toString();
				String password2 = p2text.getText().toString();
				
				if (EmailValidator.validate(email)) {				
					if (password != password2) {
						if (!password.equals("")) {
							String[] data = {email, password};					
							new RegistrationAsyncTask(RegistrationActivity.this).execute(data);
						}
						else {
							Toast.makeText(RegistrationActivity.this, "Heslo nesmí být prázdné!", Toast.LENGTH_LONG).show();
						}							
					}
					else {
						Toast.makeText(RegistrationActivity.this, "Obì hesla se musí rovnat!", Toast.LENGTH_LONG).show();
					}
				}
				else {
					Toast.makeText(RegistrationActivity.this, "Zadejte platný email!", Toast.LENGTH_LONG).show();
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
