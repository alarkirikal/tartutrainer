package com.tartutrainer.adapters;

import java.util.ArrayList;

import com.tartutrainer.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AllProgramsListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> name;
	private ArrayList<String> desc;
	private ArrayList<Integer> count;
	private ArrayList<String> date;
	private static LayoutInflater inflater = null;

	public AllProgramsListAdapter(Activity a, ArrayList<String> e,
			ArrayList<String> f, ArrayList<Integer> c, ArrayList<String> d) {
		activity = a;
		name = e;
		desc = f;
		count = c;
		date = d;
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
			vi = inflater.inflate(R.layout.listitem_program, null);
		}
		TextView text = (TextView) vi.findViewById(R.id.programName);
		TextView textDesc = (TextView) vi.findViewById(R.id.programDesc);
		TextView textCount = (TextView) vi.findViewById(R.id.programCount);
		TextView textDate = (TextView) vi.findViewById(R.id.programDate);
		text.setText(name.get(position));
		textDesc.setText(desc.get(position));
		textCount.setText(String.valueOf(count.get(position)) + " exercises");
		textDate.setText(date.get(position));
		return vi;
	}
}