package com.tartutrainer.adapters;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddExercisesListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> id;
	private ArrayList<String> name;
	private ArrayList<String> desc;
	private static LayoutInflater inflater = null;
	private final static String MUSCLE_GROUPS = "muscle_groups";

	public AddExercisesListAdapter(Activity a, ArrayList<String> i,
			ArrayList<String> e, ArrayList<String> f) {
		activity = a;
		id = i;
		name = e;
		desc = f;
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

	View vi;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.listitem_addexercise, null);
		}

		try {
			ImageView img = (ImageView) vi.findViewById(R.id.ExerciseIcon);
			String uri = "@drawable/img_" + id.get(position).replaceAll("-", "_")
					+ "1";
			int imgRes = activity.getResources().getIdentifier(uri, null,
					activity.getPackageName());
			Drawable res = activity.getResources().getDrawable(imgRes);
			img.setImageDrawable(res);
		} catch (NotFoundException n) {
			ImageView img = (ImageView) vi.findViewById(R.id.ExerciseIcon);
			String uri = "@drawable/img_notavailable";
			int imgRes = activity.getResources().getIdentifier(uri, null,
					activity.getPackageName());
			Drawable res = activity.getResources().getDrawable(imgRes);
			img.setImageDrawable(res);
		}
			
		TextView text = (TextView) vi.findViewById(R.id.exerciseName);
		text.setText(name.get(position));

		SharedPreferences prefs = activity.getSharedPreferences(MUSCLE_GROUPS,
				Context.MODE_PRIVATE);
		String musclesText = "";

		Log.d("desc", desc.get(position));
		for (int i = 0; i < desc.get(position).length(); i++) {
			boolean addComma = false;
			if (String.valueOf(desc.get(position).charAt(i)).equalsIgnoreCase(
					"1")) {

				musclesText += prefs.getString(String.valueOf(i + 1), "");
				addComma = true;

			}
			if (addComma) {
				musclesText += ", ";
			}
		}

		TextView textDesc = (TextView) vi.findViewById(R.id.exerciseDesc);
		if (musclesText.length() > 3) {
			textDesc.setText(musclesText.substring(0, musclesText.length() - 2));
		} else {
			textDesc.setText("No musclegroups assigned");
		}

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(activity);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT items FROM programs WHERE id LIKE '"
						+ activity.getIntent().getExtras().getString("pgr_id")
						+ "';", null);

		myCursor.moveToFirst();
		String[] oldItems = myCursor.getString(0).split(":");
		ArrayList<String> it = new ArrayList<String>();
		for (String s : oldItems) {
			it.add(s.replaceAll(";", ""));
		}

		/*
		 * if(it.contains(id.get(position))){
		 * vi.setBackgroundColor(Color.parseColor("#00ccff")); } else{
		 * vi.setBackgroundColor(-1); }
		 */

		myCursor.close();
		db.close();

		ImageView imgAdd = (ImageView) vi.findViewById(R.id.exc_add_to_program);
		imgAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DBAdapter db = null;
				db = DBAdapter.getDBAdapterInstance(activity);
				db.openDataBase();

				Cursor myCursor = db.getReadableDatabase().rawQuery(
						"SELECT items FROM programs WHERE id LIKE '"
								+ activity.getIntent().getExtras()
										.getString("pgr_id") + "';", null);

				myCursor.moveToFirst();
				String oldItems = myCursor.getString(0);

				myCursor.close();
				db.close();

				// Write new items

				DBAdapter db_write = null;
				db_write = DBAdapter.getDBAdapterInstance(activity);
				db_write.openDataBase();

				ContentValues args = new ContentValues();
				if (oldItems.length() > 0) {
					args.put("items", oldItems + ":" + id.get(position)
							+ ";;;;;;;;;;;;;;;;;;;;;");
				} else {
					args.put("items", id.get(position)
							+ ";;;;;;;;;;;;;;;;;;;;;");
				}

				db_write.getWritableDatabase().update(
						"programs",
						args,
						"id LIKE '"
								+ activity.getIntent().getExtras()
										.getString("pgr_id") + "'", null);

				db_write.close();

				Toast.makeText(activity, "Exercise added to the program!",
						Toast.LENGTH_SHORT).show();
				// Currently broken
				// vi.setBackgroundColor(Color.parseColor("#00ccff"));
				// vi.refreshDrawableState();
			}

		});

		return vi;
	}
}