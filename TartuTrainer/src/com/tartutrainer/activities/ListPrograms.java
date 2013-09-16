package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tartutrainer.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

		// Initial load
		fillListView("date");
	}

	protected void fillListView(String sortBy) {

		// ListView to fill
		ListView lv = (ListView) findViewById(R.id.list_programs);

		// Remove all child elements, if they exist
		lv.setAdapter(null);

		// Get the list
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
		// TODO: SQL statement goes here and to be sorted accordingly to the
		// element
		List<Map<String, String>> programsList = new ArrayList<Map<String, String>>();
		programsList.add(createProgram("program", "Lihased Programm - by "
				+ element));
		programsList.add(createProgram("program", "Vihased Programm - by "
				+ element));
		programsList.add(createProgram("program", "Pekised Programm - by "
				+ element));
		programsList.add(createProgram("program", "Kuumad Programm - by "
				+ element));
		programsList.add(createProgram("program", "Koledad Programm - by "
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_add:
			Intent intent = new Intent(this, AddProgram.class);
			startActivity(intent);
			return true;
		default:
			return false;

		}

	}

}
