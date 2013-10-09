package com.tartutrainer.fragments;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.activities.ClientsActivity;
import com.tartutrainer.activities.ProgramNotesActivity;
import com.tartutrainer.adapters.AllProgramsListAdapter;
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

	AllProgramsListAdapter adapter;
	ArrayList<String> nameArray;
	ArrayList<String> descArray;

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

		final View view = inflater.inflate(R.layout.fragment_programexercises,
				container, false);

		populateList(view);
		setOnClickListeners(view);
		return view;
	}

	private void setOnClickListeners(View v) {

		/* Programs list listener */
		ListView lv = (ListView) v.findViewById(R.id.programExercisesList);
		lv.setOnItemClickListener(this);

	}

	public void populateList(View v) {

		// SQL Test blalbalbalbalblabla

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
		adapter = new AllProgramsListAdapter(getActivity(), nameArray,
				descArray);

		ListView list = (ListView) v.findViewById(R.id.programExercisesList);
		list.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {

		/*
		 * Intent intent = new Intent(getActivity(),
		 * ProgramNotesActivity.class); intent.putExtra("pgr_name",
		 * nameArray.get(pos)); startActivity(intent);
		 */

		Toast.makeText(getActivity(), "selected view: " + nameArray.get(pos),
				Toast.LENGTH_SHORT).show();

	}
}
