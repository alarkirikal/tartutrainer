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



public class PurchaseListAdapter extends ArrayAdapter<String> {

	private HashMap<Integer, Boolean> Checked = new HashMap<Integer, Boolean>();

	ArrayList<String> collectionArray;
	ArrayList<String> ownedArray;
	Context c;
	
	
	public PurchaseListAdapter(Context context, int resource,  ArrayList<String> objects, ArrayList<String> owned) {
		super(context, resource,  objects);
		collectionArray = objects;
		ownedArray= owned;
		c=context;
		for (int i = 0; i < objects.size(); i++) {
			Checked.put(i, false);
		}
	}

	public void toggleChecked(int position) {
		if (Checked.get(position)) {
			Checked.put(position, false);
		} else {
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
				
				(checkedItems).add(collectionArray.get(i));
			}
		}

		return checkedItems;
	}

	@Override
	public View getView(int position, View convertView,  final ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_purchase, parent,
					false);
		}
		final int pos = position;
		CheckedTextView checkedTextView = (CheckedTextView) row.findViewById(R.id.listitem_purchase_Name);
		checkedTextView.setText("Collection " + collectionArray.get(position));
		Log.d("OWNED", ownedArray.toString());
		if (ownedArray.get(position).equals("True")){
			TextView price = (TextView) row.findViewById(R.id.listitem_purchase_Price);
			price.setText("Owned");
		}
		
		ImageView imageview = (ImageView) row.findViewById(R.id.listitem_purchase_image);
		imageview.setOnClickListener(new OnClickListener(){
		
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent( getContext(), CollectionItemActivity.class);
				intent.putExtra("category", collectionArray.get(pos));
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