package com.tartutrainer.activities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.tartutrainer.R;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramNotesActivity extends Activity implements OnClickListener {

	/*
	 * Activity that shows the notes of a program before the application shows
	 * the list of exercises within it.
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_programnotes);

		addListeners();
		fillContent();

	}

	private void addListeners() {
		ImageView ad = (ImageView) findViewById(R.id.ad);
		ad.setOnClickListener(this);

		ImageButton toProgram = (ImageButton) findViewById(R.id.programNotesContinue);
		toProgram.setOnClickListener(this);
	}

	private void fillContent() {

		EditText notesText = (EditText) findViewById(R.id.pgr_notes);

		String pgr_id = getIntent().getExtras().getString("pgr_id");
		String pgr_name = getIntent().getExtras().getString("pgr_name");

		TextView title = (TextView) findViewById(R.id.pgr_name);
		title.setText(pgr_name);

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT notes, owned FROM programs WHERE id LIKE '" + pgr_id
						+ "';", null);

		myCursor.moveToFirst();
		notesText.setText(myCursor.getString(0));
		String owned = myCursor.getString(1);
		myCursor.close();
		db.close();

		if (!owned.equalsIgnoreCase("true")) {
			notesText.setEnabled(false);
		}

	}
	
	public void saveNotes() {
		EditText notes = (EditText) findViewById(R.id.pgr_notes);

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		ContentValues args = new ContentValues();
		args.put("notes", notes.getText().toString());
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",
				Locale.US);
		args.put("date", dateFormat.format(date));
		db.getWritableDatabase().update(
				"programs",
				args,
				"id LIKE '" + getIntent().getExtras().getString("pgr_id")
						+ "'", null);

		db.close();

	}

	public void onBackPressed() {
		super.onBackPressed();
		saveNotes();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ad:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.ut.ee/"));
			startActivity(browserIntent);
			break;

		case R.id.programNotesContinue:
			saveNotes();
			Intent intent = new Intent(this, ProgramActivity.class);
			intent.putExtra("pgr_id",
					getIntent().getExtras().getString("pgr_id"));
			intent.putExtra("pgr_name",
					getIntent().getExtras().getString("pgr_name"));
			startActivity(intent);
			break;

		}
	}
}
