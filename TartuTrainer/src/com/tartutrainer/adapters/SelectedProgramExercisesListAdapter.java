package com.tartutrainer.adapters;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.models.Exercise;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectedProgramExercisesListAdapter extends ArrayAdapter<Exercise> {

	private Activity activity;
	private ArrayList<Exercise> exe;
	private static LayoutInflater inflater;

	private final static String MUSCLE_GROUPS = "muscle_groups";

	public SelectedProgramExercisesListAdapter(Activity a, ArrayList<Exercise> f) {
		super(a, R.layout.listitem_exercise, f);
		exe = f;
		activity = a;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return exe.size();
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
		name_text.setText(exe.get(position).getName());
		SharedPreferences prefs = activity.getSharedPreferences(MUSCLE_GROUPS,
				Context.MODE_PRIVATE);
		String musclesText = "";
		int counter = 1;
		String[] muscles = exe.get(position).getMuscles().split(";");
		if (muscles.length > 0) {
			// Exercise
			for (String e : muscles) {
				if(e!=""){
				Integer index = Integer.parseInt(e) + 1;
				
				musclesText += prefs.getString(String.valueOf(index), "");
				}
				if (counter < muscles.length) {
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