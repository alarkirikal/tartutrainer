package com.tartutrainer.fragments;

import java.util.ArrayList;
import java.util.Map;

import com.tartutrainer.R;
import com.tartutrainer.activities.EditExerciseActivity;
import com.tartutrainer.adapters.AddExercisesListAdapter;
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

public class ProgramAddExercisesFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	AddExercisesListAdapter adapter;
	
	ArrayList<String> idArray;
	ArrayList<String> nameArray;
	ArrayList<String> descArray;

	ArrayList<String> levelArray;
	ArrayList<String> equipArray;
	ArrayList<String> musclesArray;
	ArrayList<String> modalityArray;
	
	View view;
	
	
	public static ProgramAddExercisesFragment newInstance() {

		final ProgramAddExercisesFragment f = new ProgramAddExercisesFragment();
		final Bundle args = new Bundle();
		args.putString("identifier", "allexercises");
		f.setArguments(args);
		return f;
	}

	public ProgramAddExercisesFragment() {
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
		view = inflater.inflate(R.layout.fragment_allexercises,
				container, false);

		populateList();
		setOnClickListeners();
		return view;
	}
	
	private void setOnClickListeners() {

		/* Go to ClientsActivity */
		ImageButton toClients = (ImageButton) view
				.findViewById(R.id.toNewExercise);
		toClients.setOnClickListener(this);

		/* Programs list listener */
		ListView lv = (ListView) view.findViewById(R.id.listAllExercises);
		lv.setOnItemClickListener(this);

	}

	public void populateList() {

		idArray = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		descArray = new ArrayList<String>();
		
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT id, name, description FROM exercises WHERE owned LIKE 'true';", null);

		myCursor.moveToFirst();
		do {
			idArray.add(myCursor.getString(0));
			nameArray.add(myCursor.getString(1));
			descArray.add(myCursor.getString(2));
			myCursor.moveToNext();
		} while (!myCursor.isAfterLast());

		myCursor.close();
		db.close();

		adapter = new AddExercisesListAdapter(getActivity(), idArray, nameArray, descArray);
		ListView list = (ListView) view.findViewById(R.id.listAllExercises);
		list.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		Intent intent = new Intent(getActivity(), EditExerciseActivity.class);
		intent.putExtra("exc_id", idArray.get(pos));
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