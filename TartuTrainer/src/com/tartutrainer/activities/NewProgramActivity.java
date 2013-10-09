package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.adapters.TemplatesListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class NewProgramActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	/*
	 * Activity that asks the user to fill up the name of
	 * the new program and also the client it'll be connected to
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
		for (int i = 0; i < 20 ; i++) {
			checkedArray.add(false);
			templateArray.add("Template " + i);
		}
		
		// Add the list of templates to the layout
		list = (ListView) findViewById(R.id.listAllTemplates);
		TemplatesListAdapter adapter = new TemplatesListAdapter(this, templateArray);
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
	
	public void setInnerCheckBoxValues(View v) {
		CheckBox cb = (CheckBox) v.findViewById(R.id.templateCheckbox);
		if (!cb.isChecked()) {
			for(int i=0; i < list.getChildCount(); i++){
				if (checkedArray.get(i) == true) {
					ViewGroup item = (ViewGroup)list.getChildAt(i);
			    	CheckBox checkbox = (CheckBox)item.findViewById(R.id.templateCheckbox);
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
			for(int i=0; i < list.getChildCount(); i++){
			    ViewGroup item = (ViewGroup)list.getChildAt(i);
			    CheckBox checkbox = (CheckBox)item.findViewById(R.id.templateCheckbox);
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
	public void onItemClick(AdapterView<?> parentView, View childView, int pos, long id) {
		Log.d("on", "item click listener");
		//ViewGroup item = (ViewGroup)list.getChildAt(pos);
	    //CheckBox checkbox = (CheckBox)item.findViewById(R.id.templateCheckbox);
	    //setCheckBoxValues(checkbox);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveProgram:
			Log.d("click", "save clicked");
		}
	}

}
