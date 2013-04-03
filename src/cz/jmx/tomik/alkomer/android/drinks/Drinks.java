package cz.jmx.tomik.alkomer.android.drinks;

import java.util.ArrayList;

/**
 * Alkomer - Server App
 * --------------------
 * List of All Drinks in App
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class Drinks {
	
	protected ArrayList<ArrayList<Drink>> drinks;
	protected ArrayList<String> groups;
		
	public Drinks() {
		drinks = new ArrayList<ArrayList<Drink>>();
		groups = new ArrayList<String>();
		
		ArrayList<Drink> temp = new ArrayList<Drink>();		
		
		temp.add(new Drink(10, "Pivo 12°", 4.5f, 500, "0,5l", "drink_1"));
		temp.add(new Drink(11, "Pivo 11°", 4.0f, 500, "0,5l","drink_6"));
		temp.add(new Drink(1, "Pivo 10°", 3.5f, 500, "0,5l","drink_7"));		
		temp.add(new Drink(2, "Pivo 12°", 4.5f, 300, "0,3l","drink_9"));
		temp.add(new Drink(12, "Pivo 11°", 4.0f, 300, "0,3l","drink_15"));
		temp.add(new Drink(3, "Pivo 10°", 3.5f, 300, "0,3l","drink_4"));
		
		drinks.add(temp);
		groups.add("Piva");
		
		temp = new ArrayList<Drink>();
		temp.add(new Drink(4, "Víno", 12f, 200, "2dl","drink_2"));
		temp.add(new Drink(5, "Víno", 12f, 300, "3dl","drink_10"));

		drinks.add(temp);
		groups.add("Vína");		
		
		temp = new ArrayList<Drink>();
		temp.add(new Drink(6, "Malý panák 40%", 40f, 20, "20ml", "drink_12"));
		temp.add(new Drink(7, "Velký panák 40%", 40f, 40, "40ml", "drink_13"));
		temp.add(new Drink(13, "Velký panák 40%", 40f, 50, "50ml","drink_8"));
		temp.add(new Drink(8, "Malý panák 20%", 20f, 20, "20ml","drink_14"));
		temp.add(new Drink(9, "Velký panák 20%", 20f, 40, "40ml","drink_5"));
		temp.add(new Drink(14, "Velký panák 20%", 20f, 50, "50ml","drink_11"));
		
		drinks.add(temp);
		groups.add("Panáky");		
		
		temp = new ArrayList<Drink>();
		temp.add(new Drink(15, "Mojito", 10f, 400, "0,4l", "drink_4"));
		temp.add(new Drink(17, "Cuba Libre", 15f, 330, "0,33l","drink_16"));
		temp.add(new Drink(16, "Martini", 50f, 70, "7cl", "drink_3"));
		temp.add(new Drink(18, "Piña Colada", 28f, 150, "15cl","drink_2"));		
		
		drinks.add(temp);
		groups.add("Drinky");				
	}
	
	public ArrayList<ArrayList<Drink>> getDrinksList() {
        return drinks;		
	}
	
	public ArrayList<String> getGroupsList() {
        return groups;		
	}	
	
	public Drink findDrink(int id) {
		for (ArrayList<Drink> gr : drinks) {
			for (Drink d : gr) {
				if (d.getId() == id) return d;
			}
		}
		return null;
	}
}