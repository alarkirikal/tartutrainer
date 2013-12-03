package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.adapters.ClientsListAdapter;
import com.tartutrainer.database.DBAdapter;
import com.tartutrainer.models.Exercise;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectionItemInfoActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collection_item_info);
		
		String id = getIntent().getExtras().getString("item_id");
		
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();
		
		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM exercises WHERE id LIKE '" + id + "';", null);
		
		myCursor.moveToFirst();
		
		final String LEVELS = "levels";
		SharedPreferences levelsPrefs = this.getSharedPreferences(LEVELS,
				Context.MODE_PRIVATE);	
		final String MODALITIES = "modalities";
		SharedPreferences modsPrefs = getSharedPreferences(MODALITIES,
				Context.MODE_PRIVATE);
		final String MUSCLE_GROUPS = "muscle_groups";
		SharedPreferences musclePrefs = getSharedPreferences(MUSCLE_GROUPS,
				Context.MODE_PRIVATE);

		
		// Initialize objects
		TextView itemName = (TextView) findViewById(R.id.collectionItemInfoName);
		TextView itemLevel = (TextView) findViewById(R.id.ItemInfoLevelText);
		TextView itemModality = (TextView) findViewById(R.id.ItemInfoModalityText);
		TextView itemMuscles = (TextView) findViewById(R.id.ItemInfoMusclesText);
		TextView itemEquipment = (TextView) findViewById(R.id.ItemInfoEquipmentText);
		TextView itemInstructions = (TextView) findViewById(R.id.ItemInfoInstructionsText);
		
		ImageView imgOne = (ImageView) findViewById(R.id.InfoimageOne);
		ImageView imgTwo = (ImageView) findViewById(R.id.InfoimageTwo);
		
		
		try {
			String uriFirst = "@drawable/img_"
					+ id.replaceAll("-", "_") + "1";
			int imageResourceFirst = getResources().getIdentifier(uriFirst,
					null, getPackageName());
			Drawable resOne = getResources().getDrawable(imageResourceFirst);
			imgOne.setImageDrawable(resOne);
		} catch (NotFoundException n) {
			String uriFirst = "@drawable/img_notavailable";
			int imgRes = getResources().getIdentifier(uriFirst, null,
					getPackageName());
			Drawable res = getResources().getDrawable(imgRes);
			imgOne.setImageDrawable(res);
		}

		try {
			String uriSecond = "@drawable/img_"
					+ id.replaceAll("-", "_") + "2";
			int imageResourceSecond = getResources().getIdentifier(uriSecond,
					null, getPackageName());
			Drawable resTwo = getResources().getDrawable(imageResourceSecond);
			imgTwo.setImageDrawable(resTwo);
		} catch (NotFoundException n) {
			String uriSecond = "@drawable/img_notavailable";
			int imgRes = getResources().getIdentifier(uriSecond, null,
					getPackageName());
			Drawable res = getResources().getDrawable(imgRes);
			imgTwo.setImageDrawable(res);
		}

		
		itemName.setText(myCursor.getString(1));
		itemLevel.setText(levelsPrefs.getString(
				Integer.toString(myCursor.getInt(3)), ""));
		itemModality.setText(modsPrefs.getString(
				Integer.toString(myCursor.getInt(4)), ""));

		
		String musclesIndexes = myCursor.getString(5);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < musclesIndexes.length(); i++) {
			if (("" + musclesIndexes.charAt(i)).equals("1")) {
				sb.append(musclePrefs.getString(Integer.toString(i + 1), ""));
				sb.append("; ");
			}
		}
		if (sb.toString().length() != 0) {
			String muscles = sb.toString().substring(0,
					sb.toString().length() - 3);
			itemMuscles.setText(muscles);
		} else if (sb.equals("111111111111")) {
			itemMuscles.setText("All muscles");
		} else {

			itemMuscles.setText("None");
		}

		
		
		if (myCursor.getString(6).equals("")){
			itemEquipment.setText("None");
		}
		else{
			itemEquipment.setText(myCursor.getString(6));
		}

		itemInstructions.setText(myCursor.getString(2));
		
		
		
		myCursor.close();
		db.close();
		
	}
}
