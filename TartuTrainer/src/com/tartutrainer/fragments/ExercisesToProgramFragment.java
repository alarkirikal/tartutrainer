package com.tartutrainer.fragments;

import java.util.ArrayList;
import java.util.Map;

import com.tartutrainer.R;
import com.tartutrainer.activities.EditExerciseActivity;
import com.tartutrainer.adapters.AllExercisesListAdapter;
import com.tartutrainer.adapters.AllProgramsListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class ExercisesToProgramFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	AllExercisesListAdapter adapter;
	
	ArrayList<String> nameArray;
	ArrayList<String> descArray;

	ArrayList<String> levelArray;
	ArrayList<String> equipArray;
	ArrayList<String> musclesArray;
	ArrayList<String> modalityArray;
	
	private final static String LEVELS = "levels";
	private final static String MODALITIES = "modalities";
	private final static String MUSCLE_GROUPS = "muscle_groups";

	
	public static ExercisesToProgramFragment newInstance() {

		final ExercisesToProgramFragment f = new ExercisesToProgramFragment();
		final Bundle args = new Bundle();
		args.putString("identifier", "allexercises");
		f.setArguments(args);
		return f;
	}

	public ExercisesToProgramFragment() {
	}

	/**
	 * Run when fragment itself is created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * Run when the fragment view is created (i.e. populated)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_allexercises,
				container, false);

		populateList(view);
		populateSpinners(view);
		setOnClickListeners(view);
		return view;
	}
	
	private void populateSpinners(View v) {
		
		/*
		Spinner levels = (Spinner) v.findViewById(R.id.exercisesSortLevel);
		Spinner equips = (Spinner) v.findViewById(R.id.exercisesSortEquip);
		Spinner muscles = (Spinner) v.findViewById(R.id.exercisesSortMuscles);
		Spinner modalities = (Spinner) v.findViewById(R.id.exercisesSortModality);
		*/
		
		levelArray = new ArrayList<String>();
		equipArray = new ArrayList<String>();
		musclesArray = new ArrayList<String>();
		modalityArray = new ArrayList<String>();
		
		int indexCounter = 0;
		
		// Levels spinner values
		boolean levelsToAdd = true;
		SharedPreferences levelsPrefs = getActivity().getSharedPreferences(
				LEVELS, Context.MODE_PRIVATE);
		while(levelsToAdd) {
			String val = levelsPrefs.getString(Integer.toString(indexCounter), "");
			if (!val.equalsIgnoreCase("")){
				levelArray.add(val);
				indexCounter += 1;
			} else {
				indexCounter = 0;
				levelsToAdd = false;
			}
		}
		
		ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, levelArray);
	    levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //levels.setAdapter(levelAdapter);
		
	    // Equipment spinner values
		equipArray.add("Bench 1");
		equipArray.add("Bench 2");
		ArrayAdapter<String> equipAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, equipArray);
		equipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //equips.setAdapter(equipAdapter);
		
	    // Muscles spinner values
	    boolean musclesToAdd = true;
	    SharedPreferences musclesPrefs = getActivity().getSharedPreferences(
				MUSCLE_GROUPS, Context.MODE_PRIVATE);
	    while(musclesToAdd) {
			String val = musclesPrefs.getString(Integer.toString(indexCounter), "");
			if (!val.equalsIgnoreCase("")){
				musclesArray.add(val);
				indexCounter += 1;
			} else {
				indexCounter = 0;
				musclesToAdd = false;
			}
		}
		
	    ArrayAdapter<String> muscleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, musclesArray);
		muscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //muscles.setAdapter(muscleAdapter);
		
	    // Modality spinner values
	    boolean modalitiesToAdd = true;
	    SharedPreferences modalitiesPrefs = getActivity().getSharedPreferences(
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
		
		ArrayAdapter<String> modalityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, modalityArray);
		modalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //modalities.setAdapter(modalityAdapter);
		
	}

	private void setOnClickListeners(View v) {

		/* Go to ClientsActivity */
		ImageButton toClients = (ImageButton) v
				.findViewById(R.id.toNewExercise);
		toClients.setOnClickListener(this);

		/* Programs list listener */
		ListView lv = (ListView) v.findViewById(R.id.listAllExercises);
		lv.setOnItemClickListener(this);

	}

	public void populateList(View v) {

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM sqlite_master WHERE type='table';", null);

		myCursor.moveToFirst();
		do {
			// Toast.makeText(getActivity(), myCursor.getString(1),
			// Toast.LENGTH_SHORT).show();
			myCursor.moveToNext();
		} while (!myCursor.isLast());

		myCursor.close();
		db.close();

		// SQL to get all programs
		nameArray = new ArrayList<String>();
		descArray = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			nameArray.add("Exercise #" + i);
			descArray.add("Desc #" + i);
		}
		adapter = new AllExercisesListAdapter(getActivity(), nameArray, descArray);

		ListView list = (ListView) v.findViewById(R.id.listAllExercises);
		list.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		Intent intent = new Intent(getActivity(), EditExerciseActivity.class);
		intent.putExtra("exc_name", nameArray.get(pos));
		intent.putExtra("action", "Edit Exercise");
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toNewExercise:
			Intent intent = new Intent(getActivity(), EditExerciseActivity.class);
			intent.putExtra("action", "New Exercise");
			startActivity(intent);
		}
	}

}