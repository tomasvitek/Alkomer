package cz.jmx.tomik.alkomer.android.drinks;

/**
 * Alkomer - Server App
 * --------------------
 * Drink Object
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class Drink {

	protected int id;
	protected String name;
    protected double alcoholPercentage;
    protected double volume;
    protected String volumeInText;	
    protected String iconType;
	
    public Drink(int id, String name, double alcoholPercentage, double volume, String volumeInText) {
		super();
		this.id = id;
		this.name = name;
		this.alcoholPercentage = alcoholPercentage;
		this.volume = volume;
		this.volumeInText = volumeInText;
	}
    
    public Drink(int id, String name, double alcoholPercentage, double volume, String volumeInText, String iconType) {
		super();
		this.id = id;
		this.name = name;
		this.alcoholPercentage = alcoholPercentage;
		this.volume = volume;
		this.iconType = iconType;
		this.volumeInText = volumeInText;
	}    

    public int getId() {
        return id;
    }
    
    public String getIconType() {
        return iconType;
    }    
    
    public String getName() {
        return name;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public double getVolume() {
        return volume;
    }
    
    public String getVolumeInText() {
        return volumeInText;
    }    
}

