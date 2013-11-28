package com.tartutrainer.activities;

import java.util.UUID;

import com.tartutrainer.R;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SaveTemplateActivity extends Activity implements OnClickListener{
		
		String id;
		
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_save_template);
		
		id = getIntent().getExtras().getString("Program");
		
		ImageButton save = (ImageButton) findViewById(R.id.saveTemplate) ;
		save.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View arg0) {
		
		EditText name = (EditText) findViewById(R.id.templateName);
		EditText description = (EditText) findViewById(R.id.templateDescription);
		
		if (name.getText().toString().length() < 4) {
			Toast.makeText(this, "Template name too short", Toast.LENGTH_SHORT).show();				
			return;
		} 

		
		
		String items;
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT items FROM programs WHERE id like ?" , new String[]{id});

		myCursor.moveToFirst();
			items = myCursor.getString(0);
		myCursor.close();

		ContentValues args = new ContentValues();
		String id2 = UUID.randomUUID().toString();
		args.put("id", id2);
		args.put("name", name.getText().toString());
		args.put("description", description.getText().toString());
		args.put("items", items);
		Log.d("ITEMS", items);
		db.getWritableDatabase().insert("templates", null , args);
		
		db.getWritableDatabase().delete("programs", "id like ?", new String[]{id});
		
		db.close();
		
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("select_tab", "program");
		startActivity(intent);
		
		
	}
	
	
}
