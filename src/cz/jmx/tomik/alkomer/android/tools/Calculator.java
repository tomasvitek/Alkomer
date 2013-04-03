package cz.jmx.tomik.alkomer.android.tools;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;
import cz.jmx.tomik.alkomer.android.database.DataHelper;
import cz.jmx.tomik.alkomer.android.drinks.Drink;
import cz.jmx.tomik.alkomer.android.drinks.Drinks;
import cz.jmx.tomik.alkomer.android.drinks.Glass;
import cz.jmx.tomik.alkomer.android.models.User;

/**
 * Alkomer - Server App
 * --------------------
 * Calculates all Data
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class Calculator {

	protected DataHelper db;
	protected User user;
	
	protected double alcoholLevel = -1;
	
    protected String listOfDrinks;
    protected int totalDrinks;
    protected int totalAlcoholAmount;
    protected double maximumAlcoholLevel;
    protected int drinkTypesCount;
    
    protected double maximumAlcoholLevelDayAgo;
    
    protected double alcoholLevelDayAgo;
    protected double alcoholLevelWeekAgo;
    protected double alcoholLevelMonthAgo;
    
    protected String pieChartURL;
    protected String lineChartURL;
    
    public String getListOfDrinks() {
    	return listOfDrinks;
    }	
    
    public int getTotalDrinks() {
		return totalDrinks;
	}

	public int getTotalAlcoholAmount() {
		return totalAlcoholAmount;
	}

	public double getMaximumAlcoholLevel() {
		return maximumAlcoholLevel;
	}

	public int getDrinkTypesCount() {
		return drinkTypesCount;
	}

	public String getPieChartURL() {
		return pieChartURL;
	}

	public String getLineChartURL() {
		return lineChartURL;
	}	
	
	public Calculator(Context context) {
		db = new DataHelper(context);	
		user = new User(context);
		
		alcoholLevel = prepareDataForDashboard();
	}
	
	public ArrayList<Glass> getGlassesFromLastDay() {
		return (ArrayList<Glass>)db.selectAllFromLastDay();
	}
	
	public ArrayList<Glass> getGlassesFromLastDays(int days) {
		return (ArrayList<Glass>)db.selectAllFromLastDays(days);
	}	
	
	public double prepareDataForDashboard() {
		double alcoholGrams = 0.0f;
        
        Drinks drinks = new Drinks();        
        List<Glass> glasses;
        glasses = db.selectAllButLastMonth();
        //glasses = db.selectAllFromLastDays(3);
        
        for (int i = glasses.size()-1; i >= 0; i--) {        	
        	Glass g = glasses.get(i);
        	Drink d = drinks.findDrink(g.getDrinkId());
        	double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);
            
            Date nextTime = null;
            if (i == 0)  nextTime = new Date();
            else nextTime = glasses.get(i-1).getTime();

            // interval between this and next drink
            long hours = nextTime.getTime() - g.getTime().getTime();
            
            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;

            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            if (alcoholGrams < 0) alcoholGrams = 0;    
            
            Log.d("Basic processing", "Drink "+ d.getName() + ", drunk " + g.getTime().toLocaleString());
        }     
        
        alcoholLevelMonthAgo = alcoholGrams;

        glasses = db.selectAllButLastWeek();
        
        for (int i = glasses.size()-1; i >= 0; i--) {        	
        	Glass g = glasses.get(i);
        	Drink d = drinks.findDrink(g.getDrinkId());
        	double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);
            
            Date nextTime = null;
            if (i == 0)  nextTime = new Date();
            else nextTime = glasses.get(i-1).getTime();

            // interval between this and next drink
            long hours = nextTime.getTime() - g.getTime().getTime();
            
            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;

            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            if (alcoholGrams < 0) alcoholGrams = 0;    
            
            Log.d("Basic processing", "Drink "+ d.getName() + ", drunk " + g.getTime().toLocaleString());
        }     
        
        alcoholLevelWeekAgo = alcoholGrams;   
        
        glasses = db.selectAllButLastDay();
        
        for (int i = glasses.size()-1; i >= 0; i--) {        	
        	Glass g = glasses.get(i);
        	Drink d = drinks.findDrink(g.getDrinkId());
        	double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);
            
            Date nextTime = null;
            if (i == 0)  nextTime = new Date();
            else nextTime = glasses.get(i-1).getTime();

            // interval between this and next drink
            long hours = nextTime.getTime() - g.getTime().getTime();
            
            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;

            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            if (alcoholGrams < 0) alcoholGrams = 0;    
            
            Log.d("Basic processing", "Drink "+ d.getName() + ", drunk " + g.getTime().toLocaleString());
        }     
        
        alcoholLevelDayAgo = alcoholGrams;           
        
        glasses = db.selectAllFromLastDays(1);        
        for (int i = glasses.size()-1; i >= 0; i--) {        	
        	Glass g = glasses.get(i);
        	Drink d = drinks.findDrink(g.getDrinkId());
        	double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);
            
            Date nextTime = null;
            if (i == 0)  nextTime = new Date();
            else nextTime = glasses.get(i-1).getTime();

            // interval between this and next drink
            long hours = nextTime.getTime() - g.getTime().getTime();
            
            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;

            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            if (alcoholGrams < 0) alcoholGrams = 0;    
            
            Log.d("Basic processing", "Drink "+ d.getName() + ", drunk " + g.getTime().toLocaleString());
        }           
        return alcoholGrams;
	}
	
    public double processDayAgo() {
    	StringBuilder listBuilder = new StringBuilder();
        
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    	
    	double alcoholGrams = alcoholLevelDayAgo;
        
        Drinks drinks = new Drinks();        
        List<Glass> glasses;
        glasses = db.selectAllFromLastDays(1);
        
        totalDrinks = 0;
        totalAlcoholAmount = 0;
        maximumAlcoholLevel = 0;     
        
        HashMap<String,Integer> drinkTypes = new HashMap<String,Integer>();
        HashMap<Long,Double> levelStats = new HashMap<Long,Double>();
        
        int statsCount;
        if (glasses.size() > 0) {
        	levelStats.put(DataTime.getNextHour(glasses.get(glasses.size()-1).getTime(), 0).getTime(), alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())));
        	statsCount = 1;
        }
        else statsCount = 0; 
        
        Date lastProcessedStat = null;
        
        // PROCESSING EACH DRINK
        for (int i = glasses.size()-1; i >= 0; i--) {
            // glass
        	Glass g = glasses.get(i);
        	// of what drink?
        	Drink d = drinks.findDrink(g.getDrinkId());
        	Log.d("Advanced processing (DAY)", d.getName() + ", drunk " + g.getTime().toLocaleString());
        	// how much alcohol was in a drink?
            double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);

            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;
            
            Date nextTime;
            long hours;
            
            if (i == 0) nextTime = new Date();
            else {
            	double tempAlcoholGrams = alcoholGrams;
            	// see comments under (the same, just for exact hour, not drink)
            	int j = 1;
            	Date old = g.getTime();
            	nextTime = DataTime.getNextHour(g.getTime(), j);
            	while (glasses.get(i-1).getTime().getTime() >= nextTime.getTime()) {
                	hours = nextTime.getTime() - old.getTime();
                	
                	alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
                	if (alcoholGrams < 0) alcoholGrams = 0;
                	
                	if (alcoholGrams > maximumAlcoholLevel) maximumAlcoholLevel = (double)alcoholGrams;
                	
                	levelStats.put(nextTime.getTime(), alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())));
                	statsCount++;                	
                	
                	lastProcessedStat = nextTime;
                	
                	j++;
                	old = nextTime;
                	nextTime = DataTime.getNextHour(g.getTime(), j);
            	}
                
            	alcoholGrams = tempAlcoholGrams;
            	nextTime = glasses.get(i-1).getTime();
            }
            // list
        	listBuilder.append("• "+d.getName()+ " v "+timeFormatter.format(g.getTime()));
        	if (i > 0) listBuilder.append("\n");
        	else {
        		double tempAlcoholGrams = alcoholGrams;
       			nextTime = DataTime.getNextHour(g.getTime(), 1);
       			
            	hours = nextTime.getTime() - g.getTime().getTime();
            	
            	alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            	if (alcoholGrams < 0) alcoholGrams = 0;
            	
            	if (alcoholGrams > maximumAlcoholLevel) maximumAlcoholLevel = (double)alcoholGrams;
            	
            	levelStats.put(nextTime.getTime(), alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())));
        		statsCount++;
        		
        		lastProcessedStat = nextTime;
        		
        		alcoholGrams = tempAlcoholGrams;
        		nextTime = new Date();
        	}        	
            
            // interval between this and next drink
            hours = nextTime.getTime() - g.getTime().getTime();
            
            // user's consumption
            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            // check
            if (alcoholGrams < 0) alcoholGrams = 0;
            
            // max. alcohol level
            if (alcoholGrams > maximumAlcoholLevel) maximumAlcoholLevel = (double)alcoholGrams;
        	// total drinks
            totalDrinks++;
        	// total alcohol amount
        	totalAlcoholAmount += (int)countDrinksAlcoholLevel(d);      
            
        	// drink types statistics
            if (drinkTypes.containsKey(d.getName())) drinkTypes.put(d.getName(), drinkTypes.get(d.getName())+1);
            else drinkTypes.put(d.getName(), 1);
        }
        
        if (statsCount > 0) {
	        double tempAlcoholGrams = alcoholGrams;
	    	// see comments above (the same, just for exact hour, not drink)
	    	int j = 1;
	    	Date old = lastProcessedStat;
	    	Date nextTime = DataTime.getNextHour(lastProcessedStat, j);
	    	while (true) {
	    		if (alcoholGrams == 0.0 && old.getTime() >= (new Date()).getTime()) {
	    			Log.d("PROCESSED HOURS", statsCount+"");
	    			break;	    			
	    		}
	    		
	        	long hours = nextTime.getTime() - old.getTime();	        	
	        	
	        	alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
	        	if (alcoholGrams < 0) alcoholGrams = 0;
	        	
	        	if (alcoholGrams > maximumAlcoholLevel) maximumAlcoholLevel = (double)alcoholGrams;
	        	
	        	levelStats.put(nextTime.getTime(), alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())));
	        	statsCount++;
	        	
	        	j++;
	        	old = nextTime;
	        	nextTime = DataTime.getNextHour(lastProcessedStat, j);
	    	}
	        
	    	alcoholGrams = tempAlcoholGrams;
        }

	    // max alcohol level
        maximumAlcoholLevel = maximumAlcoholLevel/((user.getWeight() * user.getGender().getWaterInBodyConstant()));
        maximumAlcoholLevelDayAgo = maximumAlcoholLevel;
        // list of all drinks
        listOfDrinks = listBuilder.toString();
        // drink types number
        drinkTypesCount = drinkTypes.size();        
        
        // PIE CHART        
	        pieChartURL = "http://chart.googleapis.com/chart?chf=bg,s,D3D3D3&cht=p&chs=400x200&chdlp=l&chts=000000,30&chdls=000000,20&chtt="+URLEncoder.encode("Druhy nápojù")+"&";
	        
	        StringBuilder pieChartValues = new StringBuilder();        
	        StringBuilder pieChartDescs = new StringBuilder();        
	        
	        pieChartValues.append("chd=t:");
	        pieChartDescs.append("chdl=");
	
	        Iterator<Entry<String, Integer>> iteratorPieChart = drinkTypes.entrySet().iterator();        
	        while (iteratorPieChart.hasNext()) {
	        	Entry<String, Integer> pairs = (Entry<String, Integer>)iteratorPieChart.next();
	        	pieChartValues.append(pairs.getValue());
	        	pieChartDescs.append(URLEncoder.encode(pairs.getKey()+" ("+pairs.getValue()+"x)"));
	        	if (iteratorPieChart.hasNext()) {
	        		pieChartValues.append(",");
	        		pieChartDescs.append("|");        		
	        	}
	        }        
	        
	        pieChartURL += pieChartValues.toString() + "&" + pieChartDescs.toString();        
        
	    // LINE CHART
	        double maxValueAtYAxix = 0.5;	        
	        if (maximumAlcoholLevel > maxValueAtYAxix) maxValueAtYAxix = maximumAlcoholLevel;
	        
	        lineChartURL = "http://chart.apis.google.com/chart?chf=bg,s,D3D3D3&chxr=1,0,"+maxValueAtYAxix+"&chxt=x,y&chs=400x200&cht=lc&chco=76A4FB&chdlp=l&chls=2&chxs=0,000000,12.5,0,l,000000|1,000000,12.5,0,l,000000&chma=40,20,20,30&chts=000000,30&chtt="+URLEncoder.encode("Vývoj hladiny alkoholu")+"&";
	        
	        StringBuilder lineChartValues = new StringBuilder();
	        StringBuilder lineChartDescs = new StringBuilder(); 
	        
	        for (int i = 0; i < statsCount; i++) {
	        	Date hour = DataTime.getNextHour(glasses.get(glasses.size()-1).getTime(), i);
	        	
	        	double value = levelStats.get(hour.getTime());
        		lineChartValues.append((value/maxValueAtYAxix)*100);
        		if (i % Math.ceil(statsCount/10.0) == 0) lineChartDescs.append(URLEncoder.encode(DataTime.getDateHour(hour)+"h"));
        		if (i < statsCount-1) {
	        		lineChartValues.append(",");
	        		lineChartDescs.append("|");     		
	        	}
	        }		        
	
	        lineChartValues.insert(0, "chd=t:");
	        lineChartDescs.insert(0, "chxl=0:|");        
	        
	        lineChartURL += lineChartValues.toString() + "&" + lineChartDescs.toString();
        
        return alcoholGrams;
    }
    
    public double processWeekAgo() {
    	StringBuilder listBuilder = new StringBuilder();
        
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    	
    	double alcoholGrams = alcoholLevelWeekAgo;
        
        Drinks drinks = new Drinks();        
        List<Glass> glasses;
        glasses = db.selectAllFromLastDays(7);
        
        totalDrinks = 0;
        totalAlcoholAmount = 0;
        maximumAlcoholLevel = 0;     
        
        HashMap<String,Integer> drinkTypes = new HashMap<String,Integer>();
        HashMap<Long,Double> levelStats = new HashMap<Long,Double>();
        
    	for (int i = -6; i <= 0; i++) {
    		levelStats.put(DataTime.getNextDay(new Date(), i).getTime(), 0.0);
    	}
        
        // PROCESSING EACH DRINK
        for (int i = glasses.size()-1; i >= 0; i--) {
            // glass
        	Glass g = glasses.get(i);
        	// of what drink?
        	Drink d = drinks.findDrink(g.getDrinkId());
        	Log.d("Advanced processing (WEEK)", d.getName() + ", drunk " + g.getTime().toLocaleString());
        	// how much alcohol was in a drink?
            double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);

            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;
            
            Date nextTime;
            long hours;
            
            if (i == 0) nextTime = new Date();
            else nextTime = glasses.get(i-1).getTime();
            // list
        	listBuilder.append("• "+d.getName()+ " v "+timeFormatter.format(g.getTime()));
        	if (i > 0) listBuilder.append("\n");
            
            // interval between this and next drink
            hours = nextTime.getTime() - g.getTime().getTime();
            
            // user's consumption
            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            // check
            if (alcoholGrams < 0) alcoholGrams = 0;
            
            if (levelStats.get(DataTime.getNextDay(g.getTime(),0).getTime()) < (alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())))) {
            	levelStats.put(DataTime.getNextDay(g.getTime(),0).getTime(), alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())));
            }
            
            // max. alcohol level
            if (alcoholGrams > maximumAlcoholLevel) maximumAlcoholLevel = (double)alcoholGrams;
        	// total drinks
            totalDrinks++;
        	// total alcohol amount
        	totalAlcoholAmount += (int)countDrinksAlcoholLevel(d);      
            
        	// drink types statistics
            if (drinkTypes.containsKey(d.getName())) drinkTypes.put(d.getName(), drinkTypes.get(d.getName())+1);
            else drinkTypes.put(d.getName(), 1);
        }
        
        // max alcohol level
        maximumAlcoholLevel = maximumAlcoholLevel/((user.getWeight() * user.getGender().getWaterInBodyConstant()));
        if (maximumAlcoholLevelDayAgo > maximumAlcoholLevel) maximumAlcoholLevel = maximumAlcoholLevelDayAgo;
        // list of all drinks
        listOfDrinks = listBuilder.toString();
        // drink types number
        drinkTypesCount = drinkTypes.size();        
        
        // PIE CHART        
	        pieChartURL = "http://chart.googleapis.com/chart?chf=bg,s,D3D3D3&cht=p&chs=400x200&chdlp=l&chts=000000,30&chdls=000000,20&chtt="+URLEncoder.encode("Druhy nápojù")+"&";
	        
	        StringBuilder pieChartValues = new StringBuilder();        
	        StringBuilder pieChartDescs = new StringBuilder();        
	        
	        pieChartValues.append("chd=t:");
	        pieChartDescs.append("chdl=");
	
	        Iterator<Entry<String, Integer>> iteratorPieChart = drinkTypes.entrySet().iterator();        
	        while (iteratorPieChart.hasNext()) {
	        	Entry<String, Integer> pairs = (Entry<String, Integer>)iteratorPieChart.next();
	        	pieChartValues.append(pairs.getValue());
	        	pieChartDescs.append(URLEncoder.encode(pairs.getKey()+" ("+pairs.getValue()+"x)"));
	        	if (iteratorPieChart.hasNext()) {
	        		pieChartValues.append(",");
	        		pieChartDescs.append("|");        		
	        	}
	        }        
	        
	        pieChartURL += pieChartValues.toString() + "&" + pieChartDescs.toString();        
        
	    // LINE CHART
	        double maxValueAtYAxix = 0.5;	        
	        if (maximumAlcoholLevel > maxValueAtYAxix) maxValueAtYAxix = maximumAlcoholLevel;
	        
	        lineChartURL = "http://chart.apis.google.com/chart?chf=bg,s,D3D3D3&chxr=1,0,"+maxValueAtYAxix+"&chxt=x,y&chs=400x200&cht=lc&chco=76A4FB&chdlp=l&chls=2&chxs=0,000000,12.5,0,l,000000|1,000000,12.5,0,l,000000&chma=40,20,20,30&chts=000000,30&chtt="+URLEncoder.encode("Vývoj hladiny alkoholu")+"&";
	        
	        StringBuilder lineChartValues = new StringBuilder();
	        StringBuilder lineChartDescs = new StringBuilder(); 
	        
	    	for (int i = -6; i <= 0; i++) {
	    		Date day = DataTime.getNextDay(new Date(), i);
	    		
	        	double value = levelStats.get(day.getTime());
        		lineChartValues.append((value/maxValueAtYAxix)*100);
        		lineChartDescs.append(URLEncoder.encode(DataTime.getWeekDay(day)));
        		if (i < 0) {
	        		lineChartValues.append(",");
	        		lineChartDescs.append("|");     		
	        	}	    		
	    	}        
	
	        lineChartValues.insert(0, "chd=t:");
	        lineChartDescs.insert(0, "chxl=0:|");        
	        
	        lineChartURL += lineChartValues.toString() + "&" + lineChartDescs.toString();
        
        return alcoholGrams;
    }    
    
    public double processMonthAgo() {
    	StringBuilder listBuilder = new StringBuilder();
        
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    	
    	double alcoholGrams = alcoholLevelMonthAgo;
        
        Drinks drinks = new Drinks();        
        List<Glass> glasses;
        glasses = db.selectAllFromLastDays(31);
        
        totalDrinks = 0;
        totalAlcoholAmount = 0;
        maximumAlcoholLevel = 0;     
        
        HashMap<String,Integer> drinkTypes = new HashMap<String,Integer>();
        HashMap<Long,Double> levelStats = new HashMap<Long,Double>();
        
    	for (int i = -30; i <= 0; i++) {
    		levelStats.put(DataTime.getNextDay(new Date(), i).getTime(), 0.0);
    	}
        
        // PROCESSING EACH DRINK
        for (int i = glasses.size()-1; i >= 0; i--) {
            // glass
        	Glass g = glasses.get(i);
        	// of what drink?
        	Drink d = drinks.findDrink(g.getDrinkId());
        	Log.d("Advanced processing (MONTH)", d.getName() + ", drunk " + g.getTime().toLocaleString());
        	// how much alcohol was in a drink?
            double drinkAlcoholGrams = ((d.getVolume() * d.getAlcoholPercentage() * 0.8f) / 100.0f);

            if (drinkAlcoholGrams > 0.0f) alcoholGrams += drinkAlcoholGrams;
            
            Date nextTime;
            long hours;
            
            if (i == 0) nextTime = new Date();
            else nextTime = glasses.get(i-1).getTime();
            // list
        	listBuilder.append("• "+d.getName()+ " v "+timeFormatter.format(g.getTime()));
        	if (i > 0) listBuilder.append("\n");
            
            // interval between this and next drink
            hours = nextTime.getTime() - g.getTime().getTime();
            
            // user's consumption
            alcoholGrams -= (user.getConsumptionPerHour() * (hours / (60*60*1000)));
            // check
            if (alcoholGrams < 0) alcoholGrams = 0;
            
            if (levelStats.get(DataTime.getNextDay(g.getTime(),0).getTime()) < alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant()))) {
            	levelStats.put(DataTime.getNextDay(g.getTime(),0).getTime(), alcoholGrams/((user.getWeight() * user.getGender().getWaterInBodyConstant())));
            }
            
            // max. alcohol level
            if (alcoholGrams > maximumAlcoholLevel) maximumAlcoholLevel = (double)alcoholGrams;
        	// total drinks
            totalDrinks++;
        	// total alcohol amount
        	totalAlcoholAmount += (int)countDrinksAlcoholLevel(d);      
            
        	// drink types statistics
            if (drinkTypes.containsKey(d.getName())) drinkTypes.put(d.getName(), drinkTypes.get(d.getName())+1);
            else drinkTypes.put(d.getName(), 1);
        }
        
        // max alcohol level
        maximumAlcoholLevel = maximumAlcoholLevel/((user.getWeight() * user.getGender().getWaterInBodyConstant()));
        if (maximumAlcoholLevelDayAgo > maximumAlcoholLevel) maximumAlcoholLevel = maximumAlcoholLevelDayAgo;
        // list of all drinks
        listOfDrinks = listBuilder.toString();
        // drink types number
        drinkTypesCount = drinkTypes.size();        
        
        // PIE CHART        
	        pieChartURL = "http://chart.googleapis.com/chart?chf=bg,s,D3D3D3&cht=p&chs=400x200&chdlp=l&chts=000000,30&chdls=000000,20&chtt="+URLEncoder.encode("Druhy nápojù")+"&";
	        
	        StringBuilder pieChartValues = new StringBuilder();        
	        StringBuilder pieChartDescs = new StringBuilder();        
	        
	        pieChartValues.append("chd=t:");
	        pieChartDescs.append("chdl=");
	
	        Iterator<Entry<String, Integer>> iteratorPieChart = drinkTypes.entrySet().iterator();        
	        while (iteratorPieChart.hasNext()) {
	        	Entry<String, Integer> pairs = (Entry<String, Integer>)iteratorPieChart.next();
	        	pieChartValues.append(pairs.getValue());
	        	pieChartDescs.append(URLEncoder.encode(pairs.getKey()+" ("+pairs.getValue()+"x)"));
	        	if (iteratorPieChart.hasNext()) {
	        		pieChartValues.append(",");
	        		pieChartDescs.append("|");        		
	        	}
	        }        
	        
	        pieChartURL += pieChartValues.toString() + "&" + pieChartDescs.toString();        
        
	    // LINE CHART
	        double maxValueAtYAxix = 0.5;	        
	        if (maximumAlcoholLevel > maxValueAtYAxix) maxValueAtYAxix = maximumAlcoholLevel;
	        
	        lineChartURL = "http://chart.apis.google.com/chart?chf=bg,s,D3D3D3&chxr=1,0,"+maxValueAtYAxix+"&chxt=x,y&chs=400x200&cht=lc&chco=76A4FB&chdlp=l&chls=2&chxs=0,000000,12.5,0,l,000000|1,000000,12.5,0,l,000000&chma=40,20,20,30&chts=000000,30&chtt="+URLEncoder.encode("Vývoj hladiny alkoholu")+"&";
	        
	        StringBuilder lineChartValues = new StringBuilder();
	        StringBuilder lineChartDescs = new StringBuilder(); 
	        
	    	for (int i = -30; i <= 0; i++) {
	    		Date day = DataTime.getNextDay(new Date(), i);
	    		
	        	double value = levelStats.get(day.getTime());
        		lineChartValues.append((value/maxValueAtYAxix)*100);
        		if (i % 3 == 0) lineChartDescs.append(URLEncoder.encode(DataTime.getFormalDate(day)));
        		if (i < 0) {
	        		lineChartValues.append(",");
	        		lineChartDescs.append("|");     		
	        	}	    		
	    	}        
	
	        lineChartValues.insert(0, "chd=t:");
	        lineChartDescs.insert(0, "chxl=0:|");        
	        
	        lineChartURL += lineChartValues.toString() + "&" + lineChartDescs.toString();
        
        return alcoholGrams;
    }      
    
    public double countDrinksAlcoholLevel(Drink drink) {
    	return drink.getVolume() * drink.getAlcoholPercentage() / 100;
    }
    
    public double countPromilles() {
    	if (alcoholLevel == -1) alcoholLevel = prepareDataForDashboard();
    	return alcoholLevel/((user.getWeight() * user.getGender().getWaterInBodyConstant()));
    }

    public String countTime() {
    	if (alcoholLevel == -1) alcoholLevel = prepareDataForDashboard();
    	if (alcoholLevel == 0.0f) return "";
    	int hours = (int)(alcoholLevel / user.getConsumptionPerHour());
        if (hours < 1) return "vystøízlivíte za\nménì nìž hodinu";
        return "vystøízlivíte za " + String.valueOf(hours) + " hodin";
    }
    
    public void closeDatabase() {
    	db.close();
    }


}

