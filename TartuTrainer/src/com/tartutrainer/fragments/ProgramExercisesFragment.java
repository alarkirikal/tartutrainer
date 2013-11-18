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

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

public class ProgramExercisesFragment extends Fragment implements
		OnItemClickListener, OnClickListener {

	SelectedProgramExercisesListAdapter adapter;
	ArrayList<String> ids;
	ArrayList<String> notes;
	ArrayList<String> excArray;
	ArrayList<String> nameArray;
	ArrayList<String> musclesArray;

	String currentPgrItems;

	View view;

	public static ProgramExercisesFragment newInstance() {

		final ProgramExercisesFragment f = new ProgramExercisesFragment();
		final Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	public ProgramExercisesFragment() {
	}

	/**
	 * Run when fragment itself is created
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences tagPrefs = getActivity().getSharedPreferences(
				"tagPrefs", Context.MODE_PRIVATE);
		Editor e = tagPrefs.edit();
		e.putInt("id", getId());
		Log.d("id", String.valueOf(getId()));
		e.commit();
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
		currentPgrItems = myCursor.getString(8);
		Log.d("currentPgrItems", currentPgrItems);
		String[] exercises = myCursor.getString(8).split(":", -1);
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

		/* Header listener */
		TextView tv = (TextView) view.findViewById(R.id.addHeaderBtn);
		tv.setOnClickListener(this);

	}

	public void populateList() {

		ids = new ArrayList<String>();
		notes = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		musclesArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		// Temporary fix
		if (excArray.get(0).length() != 0) {
			
			for (String excDetails : excArray) {
				String[] exerciseList = excDetails.split(";", -1);
				ids.add(exerciseList[0]);
				notes.add(exerciseList[1]);
			}
			
			int counter = 0;

			Log.d("ids", ids.toString());
			Log.d("ids len", String.valueOf(ids.size()));
			
			for (String id : ids) {
				if (id.length() > 0) {
					String sql = "SELECT name, muscles FROM exercises WHERE id LIKE '"
							+ id.trim() + "';";

					Log.d("sql", "" + sql);
					Cursor myCursor = db.getReadableDatabase().rawQuery(sql,
							null);
					myCursor.moveToFirst();
					nameArray.add(myCursor.getString(0));
					musclesArray.add(myCursor.getString(1));
					myCursor.close();
				} else { // It's a header
					nameArray.add(notes.get(counter));
					musclesArray.add("");
				}
				counter++;
			}
		}

		db.close();

		adapter = new SelectedProgramExercisesListAdapter(getActivity(),
				ids, nameArray, musclesArray);

		ListView list = (ListView) view.findViewById(R.id.programExercisesList);
		list.setAdapter(adapter);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addHeaderBtn:
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Which kind of header should be added?")
			   .setCancelable(false)
			   .setPositiveButton("Tri-Set", new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
String header;
			    	   
			    	   if (currentPgrItems.length() > 5) {
							header = ":;Tri-Set;;;;;;;;;;;;;;;;;;;";
						} else {
							header = ";Tri-Set;;;;;;;;;;;;;;;;;;;";
						}
						
						DBAdapter db_write = null;
						db_write = DBAdapter.getDBAdapterInstance(getActivity());
						db_write.openDataBase();

						ContentValues args = new ContentValues();
						args.put("items", currentPgrItems + header);
						db_write.getWritableDatabase().update(
								"programs",
								args,
								"id LIKE '"
										+ getActivity().getIntent().getExtras()
												.getString("pgr_id") + "'", null);

						db_write.close();
						onResume();
			       }
			   })
			   .setNegativeButton("Super-Set", new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			    	   
			    	   String header;
			    	   
			    	   if (currentPgrItems.length() > 5) {
							header = ":;Super-Set;;;;;;;;;;;;;;;;;;;";
						} else {
							header = ";Super-Set;;;;;;;;;;;;;;;;;;;";
						}
						
						DBAdapter db_write = null;
						db_write = DBAdapter.getDBAdapterInstance(getActivity());
						db_write.openDataBase();

						ContentValues args = new ContentValues();
						args.put("items", currentPgrItems + header);
						db_write.getWritableDatabase().update(
								"programs",
								args,
								"id LIKE '"
										+ getActivity().getIntent().getExtras()
												.getString("pgr_id") + "'", null);

						db_write.close();
						onResume();
			       }
			   });
			AlertDialog alert = builder.create();
			alert.show();
			
			break;

		default:
			break;
		}
	}
}