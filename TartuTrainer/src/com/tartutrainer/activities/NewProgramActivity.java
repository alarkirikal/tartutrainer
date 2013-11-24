package com.tartutrainer.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import com.tartutrainer.R;
import com.tartutrainer.adapters.TemplatesListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewProgramActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	/*
	 * Activity that asks the user to fill up the name of the new program and
	 * also the client it'll be connected to
	 */

	ArrayList<String> templateArray;
	ArrayList<Boolean> checkedArray;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newprogram);

		// Fill the content with client data + available templates
		fillContent();
		setOnClickListeners();
	}

	protected void fillContent() {

		// Initialize CheckBox arraylist
		checkedArray = new ArrayList<Boolean>();

		// Pre-fill the input data
		if (getIntent().getExtras().getBoolean("clientSelected")) {
			TextView cName = (TextView) findViewById(R.id.newProgramClientName);
			cName.setText(getIntent().getExtras().getString("clientName"));
			TextView cEmail = (TextView) findViewById(R.id.newProgramClientEmail);
			cEmail.setText(getIntent().getExtras().getString("clientEmail"));
		}

		// Get the list of available templates
		// templateArray = getTemplatesSQL();
		templateArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getApplicationContext());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT title, subtitle FROM templates ;", null);
		
		try{
		myCursor.moveToFirst();
		do {
			// Toast.makeText(getActivity(), myCursor.getString(1),
			// Toast.LENGTH_SHORT).show();
				checkedArray.add(false);
				templateArray.add(myCursor.getString(0));
			

			
			myCursor.moveToNext();
		} while (!myCursor.isLast());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		myCursor.close();
		db.close();

		

		// Add the list of templates to the layout
		list = (ListView) findViewById(R.id.listAllTemplates);
		TemplatesListAdapter adapter = new TemplatesListAdapter(this,
				templateArray);
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	public void setInnerCheckBoxValues(View v) {
		CheckBox cb = (CheckBox) v.findViewById(R.id.templateCheckbox);
		if (!cb.isChecked()) {
			for (int i = 0; i < list.getChildCount(); i++) {
				if (checkedArray.get(i) == true) {
					ViewGroup item = (ViewGroup) list.getChildAt(i);
					CheckBox checkbox = (CheckBox) item
							.findViewById(R.id.templateCheckbox);
					checkbox.setChecked(false);
				}
			}
			cb.setChecked(true);
		} else {
			cb.setChecked(false);
		}
	}

	public void setCheckBoxValues(View v) {
		CheckBox c = (CheckBox) v;
		if (c.isChecked()) {
			for (int i = 0; i < list.getChildCount(); i++) {
				ViewGroup item = (ViewGroup) list.getChildAt(i);
				CheckBox checkbox = (CheckBox) item
						.findViewById(R.id.templateCheckbox);
				checkbox.setChecked(false);
			}
			Log.d("checking trueks", "checking trueks");
			c.setChecked(true);
		} else {
			Log.d("action", "do nothing");
		}
	}

	protected void setOnClickListeners() {

		/* Save the program */
		ImageButton saveProgram = (ImageButton) findViewById(R.id.saveProgram);
		saveProgram.setOnClickListener(this);

		/* Template list listener */
		ListView lv = (ListView) findViewById(R.id.listAllTemplates);
		lv.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		Log.d("on", "item click listener");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveProgram:
			EditText edName = (EditText) findViewById(R.id.newProgramName);
			EditText edClient = (EditText) findViewById(R.id.newProgramClientName);
			EditText edClientEmail = (EditText) findViewById(R.id.newProgramClientEmail);

			if (edName.getText().toString().length() < 4) {
				Toast.makeText(this, "Program name too short", Toast.LENGTH_SHORT).show();				
				break;
			} else if (edClient.getText().toString().length() < 4) {
				Toast.makeText(this, "Client name too short", Toast.LENGTH_SHORT).show();				
				break;
			} else if (edClientEmail.getText().toString().length() < 4) {
				Toast.makeText(this, "Client e-mail too short", Toast.LENGTH_SHORT).show();				
				break;
			}

			// User Info
			Pattern emailPattern = Patterns.EMAIL_ADDRESS;
			Account[] accounts = AccountManager.get(getApplicationContext())
					.getAccounts();
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					Log.d("mu email", account.name);
				}
			}

			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(this);
			db.openDataBase();

			ContentValues args = new ContentValues();
			String id = UUID.randomUUID().toString();
			args.put("id", id);
			args.put("name", edName.getText().toString());
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",
					Locale.US);
			args.put("date", dateFormat.format(date));
			args.put("author", "");
			args.put("author_email", "");
			args.put("client", edClient.getText().toString());
			args.put("client_email", edClientEmail.getText().toString());
			args.put("notes", "");
			args.put("items", "");
			args.put("owned", "true");

			long smth = db.getWritableDatabase().insert("programs", null, args);
			Log.d("long", Long.toString(smth));

			db.close();

			finish();
			Intent intent = new Intent(this, ProgramNotesActivity.class);
			intent.putExtra("pgr_name", edName.getText().toString());
			intent.putExtra("pgr_id", id);
			startActivity(intent);
		}
	}

}
