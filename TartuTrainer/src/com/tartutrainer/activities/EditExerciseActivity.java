package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.fragments.AllExercisesFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditExerciseActivity extends Activity implements OnClickListener {

	/*
	 * Activity that edits the details of an exercise in the exercises table (or
	 * creates a new one)
	 */

	ArrayList<String> levelArray;
	ArrayList<String> labelArray;
	ArrayList<String> modalityArray;
	ArrayList<String> equipArray;
	private AlertDialog Dialog;
	
	private final static String MODALITIES = "modalities";
	
	int indexCounter = 0;
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editexercise);
		
		// Set title
		String actionString = getIntent().getExtras().getString("action");
		TextView excAction = (TextView) findViewById(R.id.editExerciseAction);
		excAction.setText(actionString);
		
		
		//creating the labels
		labelArray = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			labelArray.add("Label #" + i);
		}
		
		equipArray = new ArrayList<String>();
		equipArray.add("Bench 1");
		equipArray.add("Bench 2");
		
		
		modalityArray = new ArrayList<String>();
		boolean modalitiesToAdd = true;
	    SharedPreferences modalitiesPrefs = this.getSharedPreferences(
				MODALITIES, Context.MODE_PRIVATE);
	    while(modalitiesToAdd) {
			String val = modalitiesPrefs.getString(Integer.toString(indexCounter), "");
			if (!val.equalsIgnoreCase("")){
				modalityArray.add(val);
				indexCounter += 1;
			} else {
				indexCounter = 0;
				modalitiesToAdd = false;
			}
		}
	    
	    
		
		
		if (actionString.equalsIgnoreCase("Edit Exercise")) {
			fillContentByEdit();
		} else {
			fillContentByNew();
		}
		
		setOnClickListeners();
	}
	
	protected void fillContentByEdit() {
		
	}
	
	protected void fillContentByNew() {
		
	}

	private void setOnClickListeners() {
		/* Go to ClientsActivity */
		ImageButton saveExercise = (ImageButton) findViewById(R.id.editExerciseSave);
		saveExercise.setOnClickListener(this);

		ImageView label1 = (ImageView) findViewById(R.id.imageView1);
		label1.setOnClickListener(this);
		
		ImageView label2 = (ImageView) findViewById(R.id.imageView2);
		label2.setOnClickListener(this);
		
		ImageView equipment = (ImageView) findViewById(R.id.imageView3);
		equipment.setOnClickListener(this);
		
		ImageView modality = (ImageView) findViewById(R.id.imageView5);
		modality.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.editExerciseSave:
			Intent intent = new Intent(this, AllExercisesFragment.class);
			startActivity(intent);
		
		case R.id.imageView1:
			final String[] label1_list = labelArray.toArray(new String[labelArray.size()]);
			final EditText label = (EditText) findViewById(R.id.editExerciseLabelOne);
			//dialog builder
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Label 1");
			builder.setItems(label1_list, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					label.setText(label1_list[which]);
				}
			});	
			builder.setCancelable(false);
			Dialog = builder.create();
			Dialog.show();
			break;
						
		case R.id.imageView2:
			final String[] label2_list = labelArray.toArray(new String[labelArray.size()]);
			final EditText label2 = (EditText) findViewById(R.id.editExerciseLabelTwo);
			//dialog builder
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle("Label 2");
			builder2.setItems(label2_list, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					label2.setText(label2_list[which]);
				}
			});	
			builder2.setCancelable(false);
			Dialog = builder2.create();
			Dialog.show();
			break;
			
		case R.id.imageView3:
			final String[] equipment_list = equipArray.toArray(new String[equipArray.size()]);
			final EditText equipment = (EditText) findViewById(R.id.editExerciseEquipment);
			//dialog builder
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setTitle("Equipment");
			builder3.setItems(equipment_list, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					equipment.setText(equipment_list[which]);
				}
			});	
			builder3.setCancelable(false);
			Dialog = builder3.create();
			Dialog.show();
			break;

		case R.id.imageView5:
			final String[] modality_list = modalityArray.toArray(new String[modalityArray.size()]);
			final TextView modality = (TextView) findViewById(R.id.editExerciseModality);
			//dialog builder
			AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
			builder4.setTitle("Modality");
			builder4.setItems(modality_list, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					modality.setText(modality_list[which]);
				}
			});	
			builder4.setCancelable(false);
			Dialog = builder4.create();
			Dialog.show();
			break;

		}
		
	}
	
}