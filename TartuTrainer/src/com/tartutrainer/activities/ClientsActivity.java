package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ClientListAdapter;
import com.tartutrainer.adapters.ProgramListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ClientsActivity extends Activity {

	ArrayList<String> nameArray;

	/*
	 * List of clients before creating new program
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_clients);

		TextView test = (TextView) findViewById(R.id.clients_name);
		test.setText("Clients");

		nameArray = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			nameArray.add("Client #" + i);
		}

		ListView list = (ListView) findViewById(R.id.listAllClients);
		ClientListAdapter adapter = new ClientListAdapter(this, nameArray);

		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void populateList() {

		// SQL Test blalbalbalbalblabla

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getApplicationContext());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM sqlite_master WHERE type='table';", null);

		myCursor.moveToFirst();
		do {
			// Toast.makeText(getActivity(), myCursor.getString(1),
			// Toast.LENGTH_SHORT).show();
			myCursor.moveToNext();
		} while (!myCursor.isLast());

		myCursor.close();
		db.close();

	}

}
