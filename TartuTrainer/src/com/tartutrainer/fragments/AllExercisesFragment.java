package com.tartutrainer.fragments;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ExerciseListAdapter;
import com.tartutrainer.adapters.ProgramListAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AllExercisesFragment {

	LayoutInflater inflater;
	ViewGroup container;
	ExerciseListAdapter adapter;
	View view;

	public AllExercisesFragment(LayoutInflater inflater, ViewGroup container) {
		this.inflater = inflater;
		this.container = container;
	}

	public View initView() {
		view = inflater
				.inflate(R.layout.fragment_allexercises, container, false);
		return view;
	}

	public View getView() {
		return view;
	}

	public void populateList(Activity activity) {

		// SQL to get all programs
		String[] nameArray = { "Exercise #1", "Exercise #2", "Exercise #3",
				"Exercise #4", "Exercise #5", "Exercise #6", };
		String[] descArray = { "Description #1", "Description #2",
				"Description #3", "Description #4", "Description #5",
				"Description #6", };
		adapter = new ExerciseListAdapter(activity, nameArray, descArray);

		ListView list = (ListView) view.findViewById(R.id.listAllExercises);
		list.setAdapter(adapter);
	}

}
