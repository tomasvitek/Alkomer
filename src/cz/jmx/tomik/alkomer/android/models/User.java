package cz.jmx.tomik.alkomer.android.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Alkomer - Server App
 * --------------------
 * User Object
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class User {

    protected String email, password;
    protected int weight;
    protected Gender gender;
    
    protected double consumptionPerHour;

    public User(String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.gender = Gender.male;
    	this.weight = 0;
    	this.consumptionPerHour = weight * gender.getBetaConstant();
    }    
    
    public User(Context context) {
    	
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	this.email = prefs.getString("email", "");
    	this.password = prefs.getString("password", "");
    	this.weight = prefs.getInt("weight", 0);    	
    	String _gender = prefs.getString("gender", "");
		
		if (_gender.equals("male"))  {
			this.gender = Gender.male;
		}
		else {
			this.gender = Gender.female;
		}
				
		this.consumptionPerHour = weight * gender.getBetaConstant();
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public double getConsumptionPerHour() {
        return consumptionPerHour;
    }    
    
    public Gender getGender() {
        return gender;
    }

    public int getWeight() {
        return weight;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }


}
