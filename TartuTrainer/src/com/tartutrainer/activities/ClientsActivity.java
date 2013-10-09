package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ClientsListAdapter;
import com.tartutrainer.adapters.AllProgramsListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class ClientsActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	/*
	 * List of clients before creating new program
	 */
	
	ArrayList<String> nameArray;
	ArrayList<String> emailArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_clients);

		fillContent();
		setOnClickListeners();

	}

	private void setOnClickListeners() {

		/* Go to ClientsActivity */
		ImageButton toNewProgram = (ImageButton) findViewById(R.id.goNewProgram);
		toNewProgram.setOnClickListener(this);

		/* Clients list listener */
		ListView lv = (ListView) findViewById(R.id.listAllClients);
		lv.setOnItemClickListener(this);

	}

	protected void fillContent() {

		// Get the list of clients
		// nameArray = getClientsSQL();
		nameArray = new ArrayList<String>();
		emailArray = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			nameArray.add("Client #" + i);
			emailArray.add("foo_" + i + "@bar.org");
		}

		// Add the list of clients to the layout
		ListView list = (ListView) findViewById(R.id.listAllClients);
		ClientsListAdapter adapter = new ClientsListAdapter(this, nameArray);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getClientsSQL() {

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

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		Intent intent = new Intent(this, NewProgramActivity.class);
		intent.putExtra("clientSelected", true);
		intent.putExtra("clientName", nameArray.get(pos));
		intent.putExtra("clientEmail", emailArray.get(pos));
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.goNewProgram:
			Intent intent = new Intent(this, NewProgramActivity.class);
			intent.putExtra("clientSelected", false);
			startActivity(intent);
		}
	}

}
