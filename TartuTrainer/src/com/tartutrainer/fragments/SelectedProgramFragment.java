package com.tartutrainer.fragments;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.activities.ClientsActivity;
import com.tartutrainer.activities.ExerciseActivity;
import com.tartutrainer.activities.ProgramNotesActivity;
import com.tartutrainer.adapters.AllExercisesListAdapter;
import com.tartutrainer.adapters.AllProgramsListAdapter;
import com.tartutrainer.adapters.SelectedProgramExercisesListAdapter;
import com.tartutrainer.database.DBAdapter;

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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedProgramFragment extends Fragment implements
		OnItemClickListener {

	SelectedProgramExercisesListAdapter adapter;
	ArrayList<String> ids;
	ArrayList<String> excArray;
	ArrayList<String> nameArray;
	ArrayList<String> musclesArray;

	View view;

	public static SelectedProgramFragment newInstance() {

		final SelectedProgramFragment f = new SelectedProgramFragment();
		final Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	public SelectedProgramFragment() {
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

		view = inflater.inflate(R.layout.fragment_programexercises, container,
				false);

		initData();
		if (excArray.size() > 0) {
			populateList();
		}
		setOnClickListeners();
		return view;
	}

	private void initData() {
		TextView infoDate = (TextView) view.findViewById(R.id.programInfoDate);
		TextView infoClient = (TextView) view
				.findViewById(R.id.programInfoClient);

		excArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM programs WHERE id LIKE '"
						+ getActivity().getIntent().getExtras()
								.getString("pgr_id") + "';", null);

		myCursor.moveToFirst();
		// nameArray.add(myCursor.getString(1));
		String[] exercises = myCursor.getString(8).split(":");
		infoDate.setText(myCursor.getString(2));
		infoClient.setText(myCursor.getString(5));
		for (int i = 0; i < exercises.length; i++) {
			excArray.add(exercises[i]);
		}

		myCursor.close();
		db.close();
	}

	private void setOnClickListeners() {

		/* Programs list listener */
		ListView lv = (ListView) view.findViewById(R.id.programExercisesList);
		lv.setOnItemClickListener(this);

	}

	public void populateList() {

		ids = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		musclesArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		for (String excDetails : excArray) {
			String[] exerciseList = excDetails.split(";");
			ids.add(exerciseList[0]);
		}

		String sql = "SELECT name, muscles FROM exercises WHERE";
		int counter = 1;
		for (String id : ids) {
			if (id.length() < 1) {
				break;
			} else {
				if (counter == ids.size()) {
					sql += " id LIKE " + id + ";";
				} else {
					sql += " id LIKE " + id + " OR";
				}
				counter += 1;
			}
		}

		if (counter != 1) {
			Cursor myCursor = db.getReadableDatabase().rawQuery(sql, null);

			myCursor.moveToFirst();
			for (int c = 1; c < counter; c++) {
				nameArray.add(myCursor.getString(0));
				musclesArray.add(myCursor.getString(1));
				myCursor.moveToNext();
			}

			myCursor.close();
			db.close();

			// SQL to get all programs
			/*
			 * for (int i = 0; i < 20; i++) { nameArray.add("Exercise #" + i);
			 * descArray.add("Desc #" + i); }
			 */
			adapter = new SelectedProgramExercisesListAdapter(getActivity(),
					nameArray, musclesArray);

			ListView list = (ListView) view
					.findViewById(R.id.programExercisesList);
			list.setAdapter(adapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {

		Intent intent = new Intent(getActivity(), ExerciseActivity.class);
		intent.putExtra("exc_name", nameArray.get(pos));
		intent.putExtra("pgr_id", getActivity().getIntent().getExtras()
				.getString("pgr_id"));
		intent.putExtra("exc_id", ids.get(pos));
		intent.putExtra("exc_data", excArray.get(pos));
		startActivity(intent);

	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
		if (excArray.size() > 0) {
			populateList();
		}
		setOnClickListeners();
	}
}
