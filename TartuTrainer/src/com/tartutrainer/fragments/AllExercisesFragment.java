package com.tartutrainer.fragments;

import java.util.ArrayList;
import java.util.Map;

import com.tartutrainer.R;
import com.tartutrainer.activities.EditExerciseActivity;
import com.tartutrainer.adapters.AllExercisesListAdapter;
import com.tartutrainer.adapters.AllProgramsListAdapter;
import com.tartutrainer.database.DBAdapter;
import com.tartutrainer.dialogs.FilterDialog;
import com.tartutrainer.dialogs.FilterDialog.OnResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class AllExercisesFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	AllExercisesListAdapter adapter;
	private FilterDialog dialog;

	ArrayList<String> idArray;
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
		
		/* Open Filter Dialog */
		Button setFilters = (Button) v
				.findViewById(R.id.filterButton);
		setFilters.setOnClickListener(this);

		/* Programs list listener */
		ListView lv = (ListView) v.findViewById(R.id.listAllExercises);
		lv.setOnItemClickListener(this);

	}

	public void populateList(View v) {
		
		idArray = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		descArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		Cursor myCursor = db
				.getReadableDatabase()
				.rawQuery(
						"SELECT id, name, description FROM exercises WHERE owned LIKE 'true';",
						null);

		myCursor.moveToFirst();
		do {
			idArray.add(myCursor.getString(0));
			nameArray.add(myCursor.getString(1));
			descArray.add(myCursor.getString(2));
			myCursor.moveToNext();
		} while (!myCursor.isAfterLast());

		myCursor.close();
		db.close();

		adapter = new AllExercisesListAdapter(getActivity(), nameArray,
				descArray);

		ListView list = (ListView) v.findViewById(R.id.listAllExercises);
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
			Intent intent = new Intent(getActivity(),
					EditExerciseActivity.class);
			intent.putExtra("action", "New Exercise");
			startActivity(intent);
			break;
		case R.id.filterButton:
			dialog = new FilterDialog(getActivity());
			AlertDialog alertDialog = dialog.create();
			alertDialog.show();
			dialog.setDialogResult(new OnResult() {
				public void finish(String result) {
					Log.d("result", result);
				}
			});
			break;
		}
	}

}