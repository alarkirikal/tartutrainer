package com.tartutrainer.fragments;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ProgramListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class AllProgramsFragment {

	LayoutInflater inflater;
	ViewGroup container;
	ProgramListAdapter adapter;
	View view;

	public AllProgramsFragment(LayoutInflater inflater, ViewGroup container) {
		this.inflater = inflater;
		this.container = container;
	}

	public View initView() {
		view = inflater
				.inflate(R.layout.fragment_allprograms, container, false);
		return view;
	}

	public View getView() {
		return view;
	}

	public void populateList(Activity activity) {
		
		// SQL Test blalbalbalbalblabla
		
		DBAdapter db = null; db = DBAdapter.getDBAdapterInstance(activity);
		db.openDataBase();
		
		Cursor myCursor = db.getReadableDatabase().rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
		
		myCursor.moveToFirst();
		do {
			Toast.makeText(activity, myCursor.getString(1), Toast.LENGTH_SHORT).show();
			myCursor.moveToNext();
		} while (!myCursor.isLast());
		
		myCursor.close(); db.close();

		// SQL to get all programs
		String[] nameArray = { "Program #1", "Program #2", "Program #3",
				"Program #4", "Program #5", "Program #6", };
		String[] descArray = { "Description #1", "Description #2",
				"Description #3", "Description #4", "Description #5",
				"Description #6", };
		adapter = new ProgramListAdapter(activity, nameArray, descArray);

		ListView list = (ListView) view.findViewById(R.id.listAllPrograms);
		list.setAdapter(adapter);
	}
}