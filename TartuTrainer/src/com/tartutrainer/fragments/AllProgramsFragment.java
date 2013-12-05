package com.tartutrainer.fragments;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.activities.ClientsActivity;
import com.tartutrainer.activities.ProgramNotesActivity;
import com.tartutrainer.adapters.AllProgramsListAdapter;
import com.tartutrainer.database.DBAdapter;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AllProgramsFragment extends Fragment implements
		OnItemClickListener, OnItemLongClickListener {

	AllProgramsListAdapter adapter;
	ArrayList<String> idArray;
	ArrayList<String> nameArray;
	ArrayList<String> clientArray;
	ArrayList<Integer> excCountArray;
	ArrayList<String> dateArray;
	private AlertDialog Dialog;

	View view;

	public static AllProgramsFragment newInstance() {

		final AllProgramsFragment f = new AllProgramsFragment();
		final Bundle args = new Bundle();
		args.putString("identifier", "allprograms");
		f.setArguments(args);
		return f;
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

		view = inflater
				.inflate(R.layout.fragment_allprograms, container, false);

		populateList("date");
		setOnClickListeners(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		populateList("date");
	}
	
	
	

	private void setOnClickListeners(View v) {

		/* Programs list listener */
		ListView lv = (ListView) v.findViewById(R.id.listAllPrograms);
		lv.setOnItemClickListener(this);
		lv.setLongClickable(true);
		lv.setOnItemLongClickListener(this);

	}

	public void populateList(String orderBy) {

		idArray = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		excCountArray = new ArrayList<Integer>();
		clientArray = new ArrayList<String>();
		dateArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		String sql = "SELECT id, name, client, items, date FROM programs ORDER BY "
				+ orderBy;
		if (orderBy.equalsIgnoreCase("date")) {
			sql += " DESC;";
		} else {
			sql += ";";
		}

		Cursor myCursor = db.getReadableDatabase().rawQuery(sql, null);

		if (myCursor.getCount() != 0) {

			try {
				myCursor.moveToFirst();
				do {
					idArray.add(myCursor.getString(0));
					nameArray.add(myCursor.getString(1));
					clientArray.add(myCursor.getString(2));
					if(myCursor.getString(3).equals("")){
						excCountArray.add(0);
					}else{
					excCountArray.add(myCursor.getString(3).split(":", -1).length);
					}
					dateArray.add(myCursor.getString(4));
					myCursor.moveToNext();
				} while (!myCursor.isAfterLast());
			} catch (Exception e) {
				Log.d("EXCEPTION", e.toString());
			}
		}
		myCursor.close();
		db.close();

		Log.d("sql", sql);
		Log.d("nameArray", nameArray.toString());
		// SQL to get all programs
		adapter = new AllProgramsListAdapter(getActivity(), nameArray,
				clientArray, excCountArray, dateArray);

		ListView list = (ListView) view.findViewById(R.id.listAllPrograms);
		list.setAdapter(adapter);

	}

	public void onSort(boolean isChecked) {
		if (isChecked) {
			populateList("client");
		} else {
			populateList("date");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View childView, int pos,
			long id) {
		Intent intent = new Intent(getActivity(), ProgramNotesActivity.class);
		intent.putExtra("pgr_id", idArray.get(pos));
		intent.putExtra("pgr_name", nameArray.get(pos));
		startActivity(intent);

		Toast.makeText(getActivity(), "selected view: " + nameArray.get(pos),
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parentView, View childView,
			final int pos, long id) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(nameArray.get(pos));
		String[] optionList = { "Delete" };
		builder.setItems(optionList, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				DBAdapter db = null;
				db = DBAdapter.getDBAdapterInstance(getActivity());
				db.openDataBase();

				db.getWritableDatabase().delete("programs", "id=?",
						new String[] { idArray.get(pos).toString() });

				db.close();

				populateList("date");

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		Dialog = builder.create();
		Dialog.show();

		return true;
	}

}
