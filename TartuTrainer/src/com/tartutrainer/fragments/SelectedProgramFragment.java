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
	ArrayList<String> excArray;
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
		
		nameArray = new ArrayList<String>();
		excArray = new ArrayList<String>();

		final View view = inflater.inflate(R.layout.fragment_programexercises,
				container, false);

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM programs WHERE id LIKE "
						+ getActivity().getIntent().getExtras()
								.getString("pgr_id") + ";", null);

		myCursor.moveToFirst();
		//nameArray.add(myCursor.getString(1));
		String[] exercises = myCursor.getString(8).split(":");
		for (int i = 0; i < exercises.length; i++) {
			excArray.add(exercises[i]);
		}

		myCursor.close();
		db.close();

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

		for (String excDetails : excArray) {
			String[] exerciseList = excDetails.split(";");
			Log.d("Exercise ID in program", exerciseList[0]);
		}
		
		// SQL to get all programs
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
