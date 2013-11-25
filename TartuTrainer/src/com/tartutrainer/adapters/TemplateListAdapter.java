package com.tartutrainer.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tartutrainer.R;
import com.tartutrainer.activities.CollectionItemActivity;
import com.tartutrainer.activities.TemplateItemActivity;



public class TemplateListAdapter extends ArrayAdapter<String> {

	private HashMap<Integer, Boolean> Checked = new HashMap<Integer, Boolean>();

	ArrayList<String> templateArray;
	ArrayList<String> descArray;
	ArrayList<String> itemArray;
	Context c;
	
	
	public TemplateListAdapter(Context context, int resource,  ArrayList<String> objects, ArrayList<String> description, ArrayList<String> items) {
		super(context, resource,  objects);
		templateArray = objects;
		descArray = description;
		itemArray = items;
		c=context;
		for (int i = 0; i < objects.size(); i++) {
			Checked.put(i, false);
		}
	}

	public void toggleChecked(int position) {
		if (Checked.get(position)) {
			Checked.put(position, false);
		} else {
			for (int i = 0; i < templateArray.size(); i++) {
				Checked.put(i, false);
			}
			Checked.put(position, true);
		}

		notifyDataSetChanged();
	}

	public List<Integer> getCheckedItemPositions() {
		List<Integer> checkedItemPositions = new ArrayList<Integer>();

		for (int i = 0; i < Checked.size(); i++) {
			if (Checked.get(i)) {
				(checkedItemPositions).add(i);
			}
		}

		return checkedItemPositions;
	}

	public List<String> getCheckedItems() {
		List<String> checkedItems = new ArrayList<String>();

		for (int i = 0; i < Checked.size(); i++) {
			if (Checked.get(i)) {
				
				(checkedItems).add(templateArray.get(i));
			}
		}

		return checkedItems;
	}

	@Override
	public View getView(int position, View convertView,  final ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_template, parent,
					false);
		}
		//Log.d("Template Array", templateArray.toString());
		final int pos = position;
		CheckedTextView checkedTextView = (CheckedTextView) row.findViewById(R.id.listitem_template_check);
		TextView textview = (TextView) row.findViewById(R.id.listitem_template_Name);
		textview.setText(templateArray.get(position));
		
		TextView description = (TextView) row.findViewById(R.id.templateDesc);
		description.setText(descArray.get(position));
		
		
		
		ImageView imageview = (ImageView) row.findViewById(R.id.listitem_template_image);
		imageview.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent( getContext(), TemplateItemActivity.class);
				intent.putExtra("name", templateArray.get(pos));
				intent.putExtra("items", itemArray.get(pos));
				c.startActivity(intent);					
			}
			
		});
		
		Boolean checked = Checked.get(position);
		if (checked != null) {
			checkedTextView.setChecked(checked);
		}
		
		

		return row;
	}

}