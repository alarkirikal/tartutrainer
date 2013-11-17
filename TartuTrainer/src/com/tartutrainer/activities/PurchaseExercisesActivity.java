package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ClientsListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class PurchaseExercisesActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	ArrayList<String> collectionArray;
	ListView list;
	Button purchase;
	String selected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.purchase_exercises);

		fillContent();
		setOnClickListeners();

	}

	private void setOnClickListeners() {

		/* Go to ClientsActivity */
		purchase = (Button) findViewById(R.id.purchase);
		purchase.setEnabled(false);
		purchase.setOnClickListener(this);

		/* Clients list listener */
		ListView lv = (ListView) findViewById(R.id.listAllCollections);
		lv.setOnItemClickListener(this);

	}

	protected void fillContent() {
		collectionArray = new ArrayList<String>();
		
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM exercises WHERE owned like 'false' GROUP BY category;",
				null);

		myCursor.moveToFirst();
		do {
			collectionArray.add(myCursor.getString(0));
			myCursor.moveToNext();
		} while (!myCursor.isAfterLast());

		myCursor.close();
		db.close();

		// Add the list of collections to the layout
				
		list = (ListView) findViewById(R.id.listAllCollections);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item_multiple_choice, collectionArray);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		
		
		selected = "";
		int cntChoice = list.getCount();

        SparseBooleanArray sparseBooleanArray = list.getCheckedItemPositions();
        for(int i = 0; i < cntChoice; i++){
            if(sparseBooleanArray.get(i)) {
                selected += list.getItemAtPosition(i).toString() + "\n";
            }
        }
        Toast.makeText(PurchaseExercisesActivity.this, selected, Toast.LENGTH_LONG).show();
        
        if (selected.length()>0){
    		purchase.setEnabled(true);
        }
        else{
        	purchase.setEnabled(false);
        }
      	/*
		Intent intent = new Intent(this, NewProgramActivity.class);
		intent.putExtra("clientSelected", true);
		startActivity(intent);
		*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.purchase:

			Toast.makeText(PurchaseExercisesActivity.this, "Clicked", Toast.LENGTH_LONG).show();
			
			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(this);
			db.openDataBase();
			
			String[] args = selected.split("\n");
			ContentValues values = new ContentValues();
			values.put("owned", "true");
			for (int i = 0; i<args.length;i++){
				db.getWritableDatabase().update("exercises", values , "category = " + args[i], null);
			}
			
			db.close();
			
			
            Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("select_tab", "exercise");
			startActivity(intent);
		}
	}



}
