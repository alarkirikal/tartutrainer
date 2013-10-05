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
		
		String exc_name = getIntent().getExtras().getString("exc_name");
		
		TextView hai_edit = (TextView) findViewById(R.id.hai_edit);
		hai_edit.setText(exc_name);

	}

}
