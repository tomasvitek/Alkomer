package cz.jmx.tomik.alkomer.android;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import cz.jmx.tomik.alkomer.android.database.DataHelper;
import cz.jmx.tomik.alkomer.android.drinks.Drink;
import cz.jmx.tomik.alkomer.android.drinks.DrinkAdapter;
import cz.jmx.tomik.alkomer.android.drinks.Drinks;
import cz.jmx.tomik.alkomer.android.models.User;
import cz.jmx.tomik.alkomer.android.threads.DrinkThread;

/**
 * Alkomer - Server App
 * --------------------
 * List of Drinks Activity
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public class DrinkListActivity extends BaseActivity {
	
	protected User user;
	protected DataHelper db;	
	
	private DrinkAdapter adapter;
	
    ArrayList<String> groups;
    ArrayList<ArrayList<Drink>> items;	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinklist);
        
        this.title.setText("Vyberte nápoj");   
        
        Drinks drinks = new Drinks();
        
        user = new User(getApplicationContext());
        db = new DataHelper(getApplicationContext());
        
        groups = drinks.getGroupsList();
        items = drinks.getDrinksList();        

        ExpandableListView list = (ExpandableListView) findViewById(R.id.list);
        
        list.setOnChildClickListener(new OnChildClickListener()
        {
            
            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition, int itemPosition, long arg4)
            {
				Drink d = (Drink) items.get(groupPosition).get(itemPosition);
				
				Date time = new Date();
				
				db.insert(time, d.getId());
				//db.close();
				
				new Thread(new DrinkThread(getApplicationContext(), d.getId(), user.hashCode(), time)).start();
				
				finish();
				return false;
            }
        });
        

        
        adapter = new DrinkAdapter(this, groups, items);

        list.setAdapter(adapter);
    }
    
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            adapter.notifyDataSetChanged();
            super.handleMessage(msg);
        }

    };    
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    db.close();
	}	

}
