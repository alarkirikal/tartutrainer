package com.tartutrainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.fragmenttest.R;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListPrograms extends Activity implements
		ActionBar.OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listprograms);

		// ActionBar
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Sort by: ");
		actionBar.setDisplayShowHomeEnabled(false);

		// ActionBar dropdown sort
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		final String[] dropdownValues = getResources().getStringArray(
				R.array.programslist_sort);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				actionBar.getThemedContext(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				dropdownValues);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		actionBar.setListNavigationCallbacks(adapter, this);

		// ActionBar sort listener

		fillListView("date");
	}

	protected void fillListView(String sortBy) {

		// List to fill
		ListView lv = (ListView) findViewById(R.id.list_programs);

		// Remove all child elements, if they exist
		lv.setAdapter(null);

		// TODO: SQL - list all available programs
		List<Map<String, String>> programsList = populateProgramsList(sortBy);

		// TODO: Populate ListView with all available programs
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, programsList,
				android.R.layout.simple_list_item_1,
				new String[] { "program" }, new int[] { android.R.id.text1 });
		lv.setAdapter(simpleAdpt);

		// TODO: Generate onClick methods on all ListView elements

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view,
					int position, long id) {
				TextView clickedView = (TextView) view;
				Toast.makeText(
						ListPrograms.this,
						"Item with id [" + id + "] - " + "Position ["
								+ position + "] - " + "Program ["
								+ clickedView.getText() + "]",
						Toast.LENGTH_SHORT).show();
			}
		});

		// END TODO
	}

	private ArrayList<Map<String, String>> populateProgramsList(String element) {
		// sort accordingly to the element
		List<Map<String, String>> programsList = new ArrayList<Map<String, String>>();
		programsList.add(createProgram("program", "LihasedLihased_" + element));
		programsList.add(createProgram("program", "LihasedVihased_" + element));
		programsList.add(createProgram("program", "PekisedLihased_" + element));
		programsList.add(createProgram("program", "KuumadLihased_" + element));
		programsList.add(createProgram("program", "KoledadPekivoldid_"
				+ element));
		return (ArrayList<Map<String, String>>) programsList;
	}

	private HashMap<String, String> createProgram(String key, String name) {
		HashMap<String, String> p = new HashMap<String, String>();
		p.put(key, name);
		return p;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar_programslist, menu);
		return true;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		Log.d("position", "pos: " + Integer.toString(position));
		if (position == 0) {
			fillListView("date");
			return true;
		} else if (position == 1) {
			fillListView("client");
			return true;
		} else {
			return false;
		}
	}

}
