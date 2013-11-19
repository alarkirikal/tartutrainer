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
import android.database.Cursor;
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
		
		Exercise exc = new Exercise();
		exc.setId(myCursor.getString(0));
		exc.setName(myCursor.getString(1));
		exc.setDescription(myCursor.getString(2));
		exc.setLevel(myCursor.getInt(3));
		exc.setModality(myCursor.getInt(4));
		exc.setMuscles(myCursor.getString(5));
		exc.setEquipment(myCursor.getString(6));
		exc.setLabel_1(myCursor.getString(7));
		exc.setLabel_2(myCursor.getString(8));
		exc.setOwned(myCursor.getString(9));
		exc.setCategory(myCursor.getInt(10));	
/*		
		// Initialize objects
		EditText excName = (EditText) findViewById(R.id.editExerciseName);
		EditText excDesc = (EditText) findViewById(R.id.editExerciseDescription);
		EditText excLabelOne = (EditText) findViewById(R.id.editExerciseLabelOne);
		EditText excLabelTwo = (EditText) findViewById(R.id.editExerciseLabelTwo);
		EditText excEquip = (EditText) findViewById(R.id.editExerciseEquipment);
		TextView excLevel = (TextView) findViewById(R.id.editExerciseLevel);
		TextView excModality = (TextView) findViewById(R.id.editExerciseModality);
		TextView excMuscleGroups = (TextView) findViewById(R.id.editExerciseMuscleGroups);

		// Show the values on the layout
		excName.setText(exc.getName());
		excDesc.setText(exc.getDescription());
		excLabelOne.setText(labelsPrefs.getString(exc.getLabel_1(), ""));
		excLabelTwo.setText(labelsPrefs.getString(exc.getLabel_2(), ""));
		excEquip.setText(exc.getEquipment());
		excLevel.setText(levelsPrefs.getString(
				Integer.toString(exc.getLevel()), ""));
		excModality.setText(modsPrefs.getString(
				Integer.toString(exc.getModality()), ""));
		String[] musclesIndexes = exc.getMuscles().split(";");
		StringBuilder sb = new StringBuilder();
		for (String index : musclesIndexes) {
			sb.append(musclePrefs.getString(index, ""));
			sb.append(";");
		}
		String muscles = sb.toString().substring(0, sb.toString().length() - 1);
		excMuscleGroups.setText(muscles.replaceAll(";", "\r\n"));
*/
		
		
		
		myCursor.close();
		db.close();
		
		
		TextView title = (TextView) findViewById(R.id.collectionItemInfoName);
		title.setText("");
	}
}
