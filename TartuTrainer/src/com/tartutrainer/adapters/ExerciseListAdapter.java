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

public class ExerciseListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<String> name;
    private ArrayList<String> desc;
    private static LayoutInflater inflater=null;
    
    public ExerciseListAdapter(Activity a, ArrayList<String> e, ArrayList<String> f) {
        activity = a;
        name=e;
        desc=f;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.exercise_list_item, null);

        TextView text = (TextView)vi.findViewById(R.id.exerciseName);
        TextView textDesc = (TextView)vi.findViewById(R.id.exerciseDesc);
        text.setText(name.get(position));
        textDesc.setText(desc.get(position));
        return vi;
    }
}