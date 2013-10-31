package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ExerciseActivity extends Activity {

	ArrayList<ArrayList<String>> rows;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exercise);
		
		fillContent();
	}
	
	private void fillContent() {
		
		// Title
		TextView title = (TextView) findViewById(R.id.exerciseHeader);
		title.setText(getIntent().getExtras().getString("exc_name"));
		
		// Table elements
	}

}
