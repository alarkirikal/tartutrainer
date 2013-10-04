package com.tartutrainer.fragments;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ExerciseListAdapter;
import com.tartutrainer.adapters.ProgramListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class AllExercisesFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	ExerciseListAdapter adapter;
	ArrayList<String> nameArray;
	ArrayList<String> descArray;

	public static AllExercisesFragment newInstance() {

		final AllExercisesFragment f = new AllExercisesFragment();
		final Bundle args = new Bundle();
		args.putString("identifier", "allexercises");
		f.setArguments(args);
		return f;
	}

	public AllExercisesFragment() {
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
		setOnClickListeners(view);
		return view;
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
		for (int i = 0; i < 10; i++) {
			nameArray.add("Exercise #" + i);
			descArray.add("Desc #" + i);
		}
		adapter = new ExerciseListAdapter(getActivity(), nameArray, descArray);

		ListView list = (ListView) v.findViewById(R.id.listAllExercises);
		list.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		Toast.makeText(getActivity(), "selected view: " + nameArray.get(pos),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toNewExercise:
			Toast.makeText(getActivity(), "Add a new exercise",
					Toast.LENGTH_SHORT).show();
		}
	}

}