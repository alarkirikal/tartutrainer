package com.tartutrainer.adapters;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.database.DBAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.listitem_addexercise, null);
		}
		TextView text = (TextView) vi.findViewById(R.id.exerciseName);
		text.setText(name.get(position));

		TextView textDesc = (TextView) vi.findViewById(R.id.exerciseDesc);
		textDesc.setText(desc.get(position));

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

			}

		});

		return vi;
	}
}