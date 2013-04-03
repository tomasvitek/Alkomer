package cz.jmx.tomik.alkomer.android.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import cz.jmx.tomik.alkomer.android.drinks.Glass;
import cz.jmx.tomik.alkomer.android.tools.DataTime;
 
/**
 * Alkomer - Server App
 * --------------------
 * Database Handler
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class DataHelper {

   private static final String DATABASE_NAME = "drinks.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "drinks";

   private Context context;
   private SQLiteDatabase db;
   private OpenHelper helper = null;

   private SQLiteStatement insertStmt;
   private static final String INSERT = "insert into " + TABLE_NAME + "(drinkId, time) values (?, ?)";

   public DataHelper(Context context) {
      this.context = context;
      this.helper = new OpenHelper(this.context);      
      this.db = helper.getWritableDatabase();
      this.insertStmt = this.db.compileStatement(INSERT);
   }
   
   protected void finalize() throws Throwable {
	   super.finalize();
	   this.db.close();
	   this.helper.close();
   }    
   
   public void close() {
	   this.db.close();
	   this.helper.close();
   }  
   
   public long insert(Date time, int drinkId) {
	   	  this.insertStmt.bindLong(1, drinkId);
	      this.insertStmt.bindString(2, DataTime.convertFromDateToString(time));
	      return this.insertStmt.executeInsert();
	   }   

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }
   
   public void delete(int id) {
      this.db.delete(TABLE_NAME, "id = " +  id, null);
      Log.d("DELETING DRINK", "id = " + id);
   }   

   public List<Glass> selectAll() {
      List<Glass> list = new ArrayList<Glass>();
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "drinkId", "time" }, 
        null, null, null, null, "time desc");
      
      if (cursor.moveToFirst()) {
         do {        	 
        	int id = cursor.getInt(0);
        	
        	String dbtime = cursor.getString(2);
        	Date time = null;
        	time = DataTime.convertFromStringToDate(dbtime, context);;
			
        	Glass g = new Glass(id, time, cursor.getInt(1));
        	
        	list.add(g); 
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }
   
   public List<Glass> selectAllButLastMonth() {
      List<Glass> list = new ArrayList<Glass>();
      Date lastDay = new Date();
      long secs = 31;
      secs *= 24;
      secs *= 60;
      secs *= 60;
      secs *= 1000;
      lastDay.setTime(lastDay.getTime()-secs);
            
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "drinkId", "time" }, 
    	        "time < '"+DataTime.convertFromDateToString(lastDay)+"'", null, null, null, "time desc");
      
      if (cursor.moveToFirst()) {
         do {        	 
         	int id = cursor.getInt(0);
        	String dbtime = cursor.getString(2);
        	Date time = null;
        	time = DataTime.convertFromStringToDate(dbtime, context);;
			
			list.add(new Glass(id, time, cursor.getInt(1)));
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }  
   
   public List<Glass> selectAllButLastWeek() {
      List<Glass> list = new ArrayList<Glass>();
      Date firstDay = new Date();
      long secs = 31;
      secs *= 24;
      secs *= 60;
      secs *= 60;
      secs *= 1000;      
      firstDay.setTime(firstDay.getTime()-secs);
      
      secs = 7;
      secs *= 24;
      secs *= 60;
      secs *= 60;
      secs *= 1000;         
      Date lastDay = new Date();
      lastDay.setTime(lastDay.getTime()-secs);
      
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "drinkId", "time" }, 
    	        "time >= '"+DataTime.convertFromDateToString(firstDay)+"' AND time < '"+DataTime.convertFromDateToString(lastDay)+"'", null, null, null, "time desc");
      
      if (cursor.moveToFirst()) {
         do {        	 
         	int id = cursor.getInt(0);
        	String dbtime = cursor.getString(2);
        	Date time = null;
        	time = DataTime.convertFromStringToDate(dbtime, context);;
			
			list.add(new Glass(id, time, cursor.getInt(1)));
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }      
   
   public List<Glass> selectAllButLastDay() {
	      List<Glass> list = new ArrayList<Glass>();
	      Date firstDay = new Date();
	      long secs = 7;
	      secs *= 24;
	      secs *= 60;
	      secs *= 60;
	      secs *= 1000;	      
	      firstDay.setTime(firstDay.getTime()-secs);
	      
	      Date lastDay = new Date();
	      secs = 24;
	      secs *= 60;
	      secs *= 60;
	      secs *= 1000;		      
	      lastDay.setTime(lastDay.getTime()-secs);
	      
	      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "drinkId", "time" }, 
	    	        "time >= '"+DataTime.convertFromDateToString(firstDay)+"' AND time < '"+DataTime.convertFromDateToString(lastDay)+"'", null, null, null, "time desc");
	      
	      if (cursor.moveToFirst()) {
	         do {
	         	int id = cursor.getInt(0);
	        	String dbtime = cursor.getString(2);
	        	Date time = null;
	        	time = DataTime.convertFromStringToDate(dbtime, context);;
				
				list.add(new Glass(id, time, cursor.getInt(1)));
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return list;
	   }     
   
   public List<Glass> selectAllFromLastDay() {
	   return selectAllFromLastDays(1);
   }
   
   public List<Glass> selectAllFromLastDays(int days) {
      List<Glass> list = new ArrayList<Glass>();
      Date lastDay = new Date();
      
      long secs = days;
      secs *= 24;
      secs *= 60;
      secs *= 60;
      secs *= 1000;
      
      lastDay.setTime(lastDay.getTime()-secs);
      
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id", "drinkId", "time" }, 
    	        "time >= '"+DataTime.convertFromDateToString(lastDay)+"'", null, null, null, "time desc");
      
      if (cursor.moveToFirst()) {
         do {        	 
         	int id = cursor.getInt(0);
        	String dbtime = cursor.getString(2);
        	Date time = null;
        	time = DataTime.convertFromStringToDate(dbtime, context);;
			
			list.add(new Glass(id, time, cursor.getInt(1)));
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }

   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE "+TABLE_NAME+" (" +
         		"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
         		"drinkId INTEGER NOT NULL," +
         		"time DATETIME NOT NULL" +
         		")");
        	
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}
