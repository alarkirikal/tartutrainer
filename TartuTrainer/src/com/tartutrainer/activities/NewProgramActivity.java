package com.tartutrainer.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

import com.tartutrainer.R;
import com.tartutrainer.adapters.TemplateListAdapter;
import com.tartutrainer.adapters.TemplatesListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewProgramActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	/*
	 * Activity that asks the user to fill up the name of the new program and
	 * also the client it'll be connected to
	 */

	ArrayList<String> templateArray;
	ArrayList<String> descArray;
	ArrayList<String> itemArray;
	ArrayList<String> idArray;
	ArrayList<Boolean> checkedArray;
	ListView list;
	Context context = this;
	TemplateListAdapter adapter;
	private AlertDialog Dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newprogram);

		// Fill the content with client data + available templates
		fillContent();
		setOnClickListeners();
	}
	
	public void onResume() {
		super.onResume();
		fillContent();
	}

	protected void fillContent() {

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
		descArray = new ArrayList<String>();
		itemArray = new ArrayList<String>();
		idArray = new ArrayList<String>();
		
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getApplicationContext());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT id, name, description, items FROM templates ;", null);
		
		try{
		myCursor.moveToFirst();
		do {
			// Toast.makeText(getActivity(), myCursor.getString(1),
			// Toast.LENGTH_SHORT).show();
				idArray.add(myCursor.getString(0));
				templateArray.add(myCursor.getString(1));
				descArray.add(myCursor.getString(2));
				itemArray.add(myCursor.getString(3));

			
			myCursor.moveToNext();
		} while (!myCursor.isLast());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		myCursor.close();
		db.close();

		

		// Add the list of templates to the layout
		list = (ListView) findViewById(R.id.listAllTemplates);
		adapter = new TemplateListAdapter(this,
				R.layout.listitem_template,  templateArray, descArray, itemArray);
		list.setAdapter(adapter);
		
	}

	


	protected void setOnClickListeners() {

		/* Save the program */
		ImageButton saveProgram = (ImageButton) findViewById(R.id.saveProgram);
		saveProgram.setOnClickListener(this);

		/* Template list listener */
		ListView lv = (ListView) findViewById(R.id.listAllTemplates);
		lv.setOnItemClickListener(this);
		lv.setOnItemLongClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
			
		adapter.toggleChecked(pos);
		EditText edName = (EditText) findViewById(R.id.newProgramName);
		edName.setText(templateArray.get(pos));
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
			if (adapter.getCheckedItemPositions().size()==0){
			args.put("items", "");}
			else{args.put("items", itemArray.get(adapter.getCheckedItemPositions().get(0)));}
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
	
	public boolean onItemLongClick(AdapterView<?> parentView, View childView,
			final int pos, long id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(templateArray.get(pos));
		String[] optionList = { "Delete" };
		builder.setItems(optionList, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				DBAdapter db = null;
				db = DBAdapter.getDBAdapterInstance(context);
				db.openDataBase();

				db.getWritableDatabase().delete("templates", "id=?",
						new String[] { idArray.get(pos).toString() });

				db.close();

				fillContent();

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		Dialog = builder.create();
		Dialog.show();

		return true;
	}


}
