package com.tartutrainer.adapters;

import java.util.ArrayList;

import com.tartutrainer.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectedProgramExercisesListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> ids;
	private ArrayList<String> name;
	private ArrayList<String> muscles;
	private static LayoutInflater inflater;

	private final static String MUSCLE_GROUPS = "muscle_groups";

	public SelectedProgramExercisesListAdapter(Activity a, ArrayList<String> i,
			ArrayList<String> e, ArrayList<String> f) {
		activity = a;
		ids = i;
		name = e;
		muscles = f;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.listitem_exercise, null);
		}
		TextView name_text = (TextView) vi.findViewById(R.id.exerciseName);
		TextView muscles_text = (TextView) vi.findViewById(R.id.exerciseDesc);
		ImageView exc_img = (ImageView) vi.findViewById(R.id.ExerciseIcon);
		name_text.setText(name.get(position));
		SharedPreferences prefs = activity.getSharedPreferences(MUSCLE_GROUPS,
				Context.MODE_PRIVATE);
		String musclesText = "";
		int counter = 1;
		if (ids.get(position).length() > 0) {
			// Exercise
			for (String e : muscles.get(position).split(";")) {
				Integer index = Integer.parseInt(e) + 1;
				musclesText += prefs.getString(String.valueOf(index), "");
				if (counter < muscles.get(position).split(";").length) {
					musclesText += ", ";
				}
				counter++;
			}
			name_text.setPadding(0, 0, 0, 2);
			exc_img.setVisibility(ImageView.VISIBLE);
			muscles_text.setVisibility(TextView.VISIBLE);
		} else {
			// Header
			exc_img.setVisibility(ImageView.GONE);
			muscles_text.setVisibility(TextView.GONE);
			name_text.setPadding(10, 10, 10, 10);
		}

		muscles_text.setText(musclesText);
		return vi;
	}
}