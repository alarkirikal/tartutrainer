package com.tartutrainer.activities;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.adapters.TemplateListAdapter;

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
		
		// Get the list of available templates
		// templateArray = getTemplatesSQL();
		templateArray = new ArrayList<String>();
		for (int i = 0; i < 10 ; i++) {
			templateArray.add("Template " + i);
		}
		
		// Add the list of templates to the layout
		list = (ListView) findViewById(R.id.listAllTemplates);
		TemplateListAdapter adapter = new TemplateListAdapter(this, templateArray);
		list.setAdapter(adapter);
	}
	
	public void setCheckBoxValues(View v) {
		CheckBox c = (CheckBox) v;
		if (c.isChecked()) {
			for(int i=0; i < list.getChildCount(); i++){
			    ViewGroup item = (ViewGroup)list.getChildAt(i);
			    CheckBox checkbox = (CheckBox)item.findViewById(R.id.templateCheckbox);
			    checkbox.setChecked(false);
			}
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
		
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos, long id) {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.saveProgram:
			Log.d("click", "save clicked");
		}
	}

}
