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

public class AllProgramsFragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener, OnItemClickListener {

	AllProgramsListAdapter adapter;
	ArrayList<String> idArray;
	ArrayList<String> nameArray;
	ArrayList<String> clientArray;

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

		/* Go to ClientsActivity */
		ImageButton toClients = (ImageButton) v
				.findViewById(R.id.toClientsList);
		toClients.setOnClickListener(this);

		/* Sort Switch listener */
		Switch s = (Switch) v.findViewById(R.id.sortBySwitch);
		s.setOnCheckedChangeListener(this);

		/* Programs list listener */
		ListView lv = (ListView) v.findViewById(R.id.listAllPrograms);
		lv.setOnItemClickListener(this);

	}

	public void populateList(String orderBy) {

		idArray = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		clientArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		String sql = "SELECT id, name, client FROM programs ORDER BY "
				+ orderBy;
		if (orderBy.equalsIgnoreCase("date")) {
			sql += " DESC;";
		} else {
			sql += ";";
		}

		Cursor myCursor = db.getReadableDatabase().rawQuery(sql, null);

		myCursor.moveToFirst();
		do {
			idArray.add(myCursor.getString(0));
			nameArray.add(myCursor.getString(1));
			clientArray.add(myCursor.getString(2));
			myCursor.moveToNext();
		} while (!myCursor.isAfterLast());

		myCursor.close();
		db.close();

		// SQL to get all programs
		adapter = new AllProgramsListAdapter(getActivity(), nameArray,
				clientArray);

		ListView list = (ListView) view.findViewById(R.id.listAllPrograms);
		list.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.toClientsList:
			Intent intent = new Intent(getActivity(), ClientsActivity.class);
			startActivity(intent);

		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.sortBySwitch:
			if (isChecked) {
				populateList("client");
			} else {
				populateList("date");
			}
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
}
