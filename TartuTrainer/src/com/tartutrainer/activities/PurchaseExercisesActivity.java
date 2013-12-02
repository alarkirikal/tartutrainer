package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.adapters.PurchaseListAdapter;

import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class PurchaseExercisesActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	ListView list;
	Button purchase;

	ArrayList<String> collectionArray;
	ArrayList<String> ownedArray;
	PurchaseListAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_purchase_exercises);

		fillContent();
		setOnClickListeners();

	}

	@Override
	public void onResume() {
		super.onResume();
		fillContent();
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
		ownedArray = new ArrayList<String>();
		
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT category, owned FROM exercises WHERE category>0 GROUP BY category;",
				null);

		try {
			myCursor.moveToFirst();
			do {
				collectionArray.add(myCursor.getString(0));
				ownedArray.add(myCursor.getString(1));
				myCursor.moveToNext();
			} while (!myCursor.isAfterLast());

			myCursor.close();
			db.close();
		} catch (Exception e) {
			Log.d("EXCEPTION", e.toString());
		}
		
		// Add the list of collections to the layout
		list = (ListView) findViewById(R.id.listAllCollections);
		adapter = new PurchaseListAdapter(this, R.layout.listitem_purchase, collectionArray, ownedArray);
		list.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos, long id) {

		if (ownedArray.get(pos).equals("False")){
			adapter.toggleChecked(pos);
		}
		List l = adapter.getCheckedItems();
		if (l.size() > 0) {
			purchase.setEnabled(true);
		} else {
			purchase.setEnabled(false);
		}
				
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.purchase:

			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(this);
			db.openDataBase();

			List<String> args = adapter.getCheckedItems();
			ContentValues values = new ContentValues();
			values.put("owned", "True");
			for (int i = 0; i < args.size(); i++) {
				db.getWritableDatabase().update("exercises", values,"category = " + args.get(i), null);
			}

			db.close();

			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("select_tab", "exercise");
			startActivity(intent);
		}
	}




}





