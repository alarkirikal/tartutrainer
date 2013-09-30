package com.tartutrainer.fragments;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ProgramListAdapter;

import android.app.Activity;
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
