package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ClientsListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseExercisesActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	ListView list;
	Button purchase;

	ArrayList<String> collectionArray;
	ArrayList<String> ownedArray;
	PurchaseArrayAdapter adapter;
	

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
		adapter = new PurchaseArrayAdapter(this, R.layout.listitem_purchase,R.id.listitem_purchase_Name, collectionArray);
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
			values.put("owned", "true");
			for (int i = 0; i < args.size(); i++) {
				db.getWritableDatabase().update("exercises", values,"category = " + args.get(i), null);
			}

			db.close();

			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("select_tab", "exercise");
			startActivity(intent);
		}
	}


	private class PurchaseArrayAdapter extends ArrayAdapter<String> {

		private HashMap<Integer, Boolean> Checked = new HashMap<Integer, Boolean>();

		public PurchaseArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
			super(context, resource, textViewResourceId, objects);
			
			for (int i = 0; i < objects.size(); i++) {
				Checked.put(i, false);
			}
		}

		public void toggleChecked(int position) {
			if (Checked.get(position)) {
				Checked.put(position, false);
			} else {
				Checked.put(position, true);
			}

			notifyDataSetChanged();
		}

		public List<Integer> getCheckedItemPositions() {
			List<Integer> checkedItemPositions = new ArrayList<Integer>();

			for (int i = 0; i < Checked.size(); i++) {
				if (Checked.get(i)) {
					(checkedItemPositions).add(i);
				}
			}

			return checkedItemPositions;
		}

		public List<String> getCheckedItems() {
			List<String> checkedItems = new ArrayList<String>();

			for (int i = 0; i < Checked.size(); i++) {
				if (Checked.get(i)) {
					
					(checkedItems).add(collectionArray.get(i));
				}
			}

			return checkedItems;
		}

		@Override
		public View getView(int position, View convertView,  final ViewGroup parent) {
			View row = convertView;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.listitem_purchase, parent,
						false);
			}
			final int pos = position;
			CheckedTextView checkedTextView = (CheckedTextView) row.findViewById(R.id.listitem_purchase_Name);
			checkedTextView.setText("Collection " + collectionArray.get(position));
			if (ownedArray.get(position).equals("True")){
				TextView price = (TextView) row.findViewById(R.id.listitem_purchase_Price);
				price.setText("Owned");
			}
			
			ImageView imageview = (ImageView) row.findViewById(R.id.listitem_purchase_image);
			imageview.setOnClickListener(new OnClickListener(){
			
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent( getContext(), CollectionItemActivity.class);
					intent.putExtra("category", collectionArray.get(pos));
					startActivity(intent);					
				}
				
			});
			
			Boolean checked = Checked.get(position);
			if (checked != null) {
				checkedTextView.setChecked(checked);
			}
			
			

			return row;
		}

	}

}
