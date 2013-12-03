package com.tartutrainer.adapters;

import java.util.ArrayList;

import com.tartutrainer.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AllExercisesListAdapter extends BaseAdapter {

	private Activity mActivity;
	private ArrayList<String> tId;
	private ArrayList<String> tName;
	private ArrayList<String> tDesc;
	private static LayoutInflater inflater = null;

	public AllExercisesListAdapter(Activity mActivity, ArrayList<String> tId,
			ArrayList<String> tName, ArrayList<String> tDesc) {
		this.mActivity = mActivity;
		this.tId = tId;
		this.tName = tName;
		this.tDesc = tDesc;
		inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return tName.size();
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
		
		try {
			ImageView img = (ImageView) vi.findViewById(R.id.ExerciseIcon);
			String uri = "@drawable/img_" + tId.get(position).replaceAll("-", "_") + "1";
			int imgRes = mActivity.getResources().getIdentifier(uri, null, mActivity.getPackageName());
			Drawable res = mActivity.getResources().getDrawable(imgRes);
			img.setImageDrawable(res);
		} catch (NotFoundException n) {
			
			ImageView img = (ImageView) vi.findViewById(R.id.ExerciseIcon);
			if (tName.get(position).equals("Tri-Set") || tName.get(position).equals("Super-Set")){
				img.setVisibility(vi.GONE);
			}
			else{
			String uri = "@drawable/img_notavailable";
			int imgRes = mActivity.getResources().getIdentifier(uri, null, mActivity.getPackageName());
			Drawable res = mActivity.getResources().getDrawable(imgRes);
			img.setImageDrawable(res);
			}
		}

		
		
		TextView text = (TextView) vi.findViewById(R.id.exerciseName);
		text.setText(tName.get(position));

		TextView textDesc = (TextView) vi.findViewById(R.id.exerciseDesc);
		textDesc.setText(tDesc.get(position));

		return vi;
	}
}