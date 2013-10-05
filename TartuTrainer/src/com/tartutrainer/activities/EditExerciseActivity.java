package com.tartutrainer.activities;

import com.tartutrainer.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class EditExerciseActivity extends Activity {

	/*
	 * Activity that edits the details of an exercise in the exercises table (or
	 * creates a new one)
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editexercise);
		
		// Set title
		String actionString = getIntent().getExtras().getString("action");
		TextView excAction = (TextView) findViewById(R.id.editExerciseAction);
		excAction.setText(actionString);
		
		if (actionString.equalsIgnoreCase("Edit Exercise")) {
			fillContentByEdit();
		} else {
			fillContentByNew();
		}
		
	}
	
	protected void fillContentByEdit() {
		
	}
	
	protected void fillContentByNew() {
		
	}

}
