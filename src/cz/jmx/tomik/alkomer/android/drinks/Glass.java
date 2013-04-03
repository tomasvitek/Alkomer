package cz.jmx.tomik.alkomer.android.drinks;


import java.util.Date;

/**
 * Alkomer - Server App
 * --------------------
 * Glass of Alcohol Object
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class Glass {

    protected Date time;
    protected int drinkId;    
    protected int glassId = 0;

    public Glass(Date time, int drinkId) {
        this.time = time;
        this.drinkId = drinkId;
    }
    
    public Glass(int glassId, Date time, int drinkId) {
        this.glassId = glassId;
    	this.time = time;
        this.drinkId = drinkId;
    }    

    public int getGlassId() {
        return glassId;
    }    
    
    public int getDrinkId() {
        return drinkId;
    }

    public Date getTime() {
        return time;
    }    

}
