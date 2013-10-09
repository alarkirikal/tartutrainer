package com.tartutrainer.activities;

import com.tartutrainer.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramNotesActivity extends Activity implements OnClickListener {
	
	/*
	 * Activity that shows the notes of a program before the
	 * application shows the list of exercises within it.
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
		String pgr_name = getIntent().getExtras().getString("pgr_name");
		TextView test = (TextView) findViewById(R.id.pgr_name);
		test.setText(pgr_name);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.ad:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ut.ee/"));
			startActivity(browserIntent);
		case R.id.programNotesContinue:
			Intent intent = new Intent(this, ProgramActivity.class);
			intent.putExtra("pgr_name", getIntent().getExtras().getString("pgr_name"));
			startActivity(intent);
		}
	}
}
