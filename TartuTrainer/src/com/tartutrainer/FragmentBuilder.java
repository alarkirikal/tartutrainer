package com.tartutrainer;

import com.test.fragmenttest.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentBuilder extends Fragment {

	public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	public static int selected_exercise_id = 0;

	public static final FragmentBuilder newInstance(String message) {
		FragmentBuilder f = new FragmentBuilder();
		Bundle bdl = new Bundle(1);
		bdl.putString(EXTRA_MESSAGE, message);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		String message = getArguments().getString(EXTRA_MESSAGE);

		// Different fragments
		if (message.equalsIgnoreCase("Program")) {
			View v = inflater.inflate(R.layout.fragment_programslist,
					container, false);

			// Outer layout
			LinearLayout linlay = (LinearLayout) v
					.findViewById(R.id.sortByLayout);
			linlay.setGravity(Gravity.RIGHT);

			// sortBy Text
			TextView sortBy = new TextView(getActivity());
			sortBy.setPadding(25, 0, 0, 0);
			sortBy.setText("Sort by: ");
			sortBy.setTextAppearance(getActivity(),
					android.R.style.TextAppearance_Medium);

			// sorter Spinner

			Spinner sortSpinner = new Spinner(getActivity());
			sortSpinner.setPadding(25, 0, 0, 0);
			String[] sortOptions = { "Date", "Client" };
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					getActivity(),
					android.R.layout.simple_spinner_dropdown_item, sortOptions);
			arrayAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sortSpinner.setAdapter(arrayAdapter);
			sortSpinner.setSelection(0);
			sortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if (arg2 == 1) {
						Log.d("sort", "Sort by client");
					} else if (arg2 == 0) {
						Log.d("sort", "Sort by date");
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}

			});

			// Filling the outer layout
			linlay.addView(sortBy);
			linlay.addView(sortSpinner);

			return v;
		} else if (message.equalsIgnoreCase("Exercise")) {
			selected_exercise_id = 1;
			if (selected_exercise_id != 0) {
				
			} else {
				
			}
		}
		View v = inflater.inflate(R.layout.fragment_exercise, container, false);
		TextView messageTextView = (TextView) v.findViewById(R.id.textView);
		messageTextView
				.setText("You shouldn't be here. This was not supposed to happen.");
		return v;
	}

}
