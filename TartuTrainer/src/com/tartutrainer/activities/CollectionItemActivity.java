package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.tartutrainer.R;
import com.tartutrainer.adapters.AllExercisesListAdapter;
import com.tartutrainer.database.DBAdapter;
import com.tartutrainer.dialogs.FilterDialog;
import com.tartutrainer.fragments.AllExercisesFragment;
import com.tartutrainer.models.Exercise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionItemActivity extends Activity implements OnItemClickListener {

	AllExercisesListAdapter adapter;
	ArrayList<String> idArray;
	ArrayList<String> nameArray;
	ArrayList<String> descArray;
	
	
	/*
	 * Activity that edits the details of an exercise in the exercises table (or
	 * creates a new one)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collection_item);

		String category = getIntent().getExtras().getString("category");
		TextView title = (TextView) findViewById(R.id.collection);
		title.setText("Collection " + category);
		populateList("SELECT id, name, description FROM exercises WHERE category LIKE '" + category + "' ;");
	}

	public void populateList(String sql) {

		idArray = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		descArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(sql, null);

		
			myCursor.moveToFirst();
			do {
				idArray.add(myCursor.getString(0));
				nameArray.add(myCursor.getString(1));
				descArray.add(myCursor.getString(2));
				myCursor.moveToNext();
			} while (!myCursor.isAfterLast());

			myCursor.close();
			db.close();

		adapter = new AllExercisesListAdapter(this, nameArray,
				descArray);

		ListView list = (ListView) findViewById(R.id.listAllCollections);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		
	}

	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		
		Intent intent = new Intent(this, CollectionItemInfoActivity.class);
		intent.putExtra("item_id", idArray.get(pos));
		startActivity(intent);
	}


}