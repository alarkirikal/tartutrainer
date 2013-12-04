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
import android.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

public class AllExercisesFragment extends Fragment implements
		OnItemClickListener {

	AllExercisesListAdapter adapter;
	private FilterDialog dialog;

	ArrayList<String> idArray;
	ArrayList<String> nameArray;
	ArrayList<String> descArray;

	View view;

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
		view = inflater.inflate(R.layout.fragment_allexercises, container,
				false);

		populateList("SELECT id, name, description FROM exercises WHERE owned LIKE 'true';");
		setOnClickListeners(view);
		return view;
	}

	private void setOnClickListeners(View v) {

		/* Programs list listener */
		ListView lv = (ListView) v.findViewById(R.id.listAllExercises);
		lv.setOnItemClickListener(this);

	}

	public void populateList(String sql) {

		idArray = new ArrayList<String>();
		nameArray = new ArrayList<String>();
		descArray = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(getActivity());
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(sql, null);

		TextView nr = (TextView) view
				.findViewById(R.id.listAllExercisesNoneFound);

		if (myCursor.getCount() != 0) {

			nr.setVisibility(TextView.GONE);
			myCursor.moveToFirst();
			do {
				idArray.add(myCursor.getString(0));
				nameArray.add(myCursor.getString(1));
				descArray.add(myCursor.getString(2));
				myCursor.moveToNext();
			} while (!myCursor.isAfterLast());

			myCursor.close();
			db.close();

		} else {
			nr.setVisibility(TextView.VISIBLE);
		}
		adapter = new AllExercisesListAdapter(getActivity(), idArray, nameArray,
				descArray);

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

	public void filterExerciseDialog() {
		dialog = new FilterDialog(getActivity());
		AlertDialog alertDialog = dialog.create();
		alertDialog.show();
		dialog.setDialogResult(new OnResult() {
			public void finish(String level, String equip, String muscle,
					String modality) {

				// populateList("SELECT id, name, description FROM exercises WHERE owned LIKE 'true';");

				String sql = "SELECT id, name, description FROM exercises WHERE owned LIKE 'true'";

				if (!level.equalsIgnoreCase("-1")) {
					sql += " AND level LIKE " + (Integer.parseInt(level)+1) + " ";
				}
				if (!equip.contains("All ")) {
					sql += " AND equipment LIKE '" + equip + "' ";
				}
				if (!muscle.equalsIgnoreCase("-1")) {
					ArrayList<String> MusclesArray = new ArrayList<String>(); 
					for (int i = 0; i<12;i++){
						MusclesArray.add("_");
					}
					MusclesArray.set(Integer.parseInt(muscle), "1");
					String musclesIndex = "";
					for (String index : MusclesArray){
						musclesIndex += index;
					}
					sql += " AND muscles LIKE '" + musclesIndex + "' ";
					
				}
				if (!modality.equalsIgnoreCase("-1")) {
					sql += " AND modality LIKE " + (Integer.parseInt(modality)+1) + " ";
					
				}
				sql += ";";
				Log.d("sql", sql);
				populateList(sql);

			}
		});
	}

}