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

public class ClientsListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<String> name;
    private ArrayList<String> email;
    private static LayoutInflater inflater=null;
    
    public ClientsListAdapter(Activity a, ArrayList<String> e, ArrayList<String> em) {
        activity = a;
        name=e;
        email=em;
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
            vi = inflater.inflate(R.layout.listitem_client, null);

        TextView text = (TextView) vi.findViewById(R.id.clientName);
        text.setText(name.get(position));
        
        TextView mail = (TextView) vi.findViewById(R.id.clientEmail);
        mail.setText(email.get(position));
        return vi;
    }
}