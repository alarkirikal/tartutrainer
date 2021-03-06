package com.tartutrainer.dialogs;

import java.util.ArrayList;

import com.tartutrainer.database.DBAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FilterDialog extends Dialog {

	Context context;
	LinearLayout linlay;

	final AlertDialog.Builder alert;
	OnResult filterResult;

	final TextView title_levels;
	final TextView title_equip;
	final TextView title_muscles;
	final TextView title_modality;

	String cLevel;
	String cEquip;
	String cMuscle;
	String cModality;

	ArrayList<String> levelArray;
	ArrayList<String> equipArray;
	ArrayList<String> musclesArray;
	ArrayList<String> modalityArray;

	private final static String LEVELS = "levels";
	private final static String MODALITIES = "modalities";
	private final static String MUSCLE_GROUPS = "muscle_groups";

	private final static String SELECTIONS = "filter_selections";

	public FilterDialog(final Context context) {
		super(context);
		this.context = context;

		this.alert = new AlertDialog.Builder(context);
		this.alert.setInverseBackgroundForced(true);
		linlay = new LinearLayout(context);
		linlay.setOrientation(LinearLayout.VERTICAL);
		linlay.setPadding(20, 10, 20, 10);

		// Title
		alert.setTitle("Search Filters");

		// Input Content
		title_levels = new TextView(context);
		title_levels.setText("Level");
		title_equip = new TextView(context);
		title_equip.setText("Equipment");
		title_muscles = new TextView(context);
		title_muscles.setText("Muscles");
		title_modality = new TextView(context);
		title_modality.setText("Modality");

		// Add content to the Dialog
		populateLayout();

		// Ok/Cancel etc..
		alert.setView(linlay);
		alert.setCancelable(true);
		alert.setPositiveButton("Ok", new OKListener());
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						FilterDialog.this.dismiss();
					}
				});

	}

	public AlertDialog create() {
		return this.alert.create();
	}

	public void populateLayout() {
		
		final SharedPreferences prefs = context.getSharedPreferences(SELECTIONS, Context.MODE_PRIVATE);

		Spinner levels = new Spinner(context);
		Spinner equips = new Spinner(context);
		Spinner muscles = new Spinner(context);
		Spinner modalities = new Spinner(context);

		levelArray = getLevelArray();
		equipArray = getUsedEquipment();
		musclesArray = getMusclesArray();
		modalityArray = getModalityArray();

		// Level Spinner
		ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, levelArray);
		levelAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		levels.setAdapter(levelAdapter);
		levels.setPadding(0, 0, 0, 10);
		levels.setSelection(prefs.getInt("0", 0));
		levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				String item = (String) parent.getItemAtPosition(pos);
				cLevel = String.valueOf(pos - 1);
				SharedPreferences.Editor e_first = prefs.edit();
				e_first.putInt("0", pos);
				e_first.commit();
			}
		});

		// Equipment Spinner
		ArrayAdapter<String> equipAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, equipArray);
		equipAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		equips.setAdapter(equipAdapter);
		equips.setPadding(0, 0, 0, 10);
		equips.setSelection(prefs.getInt("1", 0));
		equips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				String item = (String) parent.getItemAtPosition(pos);
				cEquip = item;
				SharedPreferences.Editor e_second = prefs.edit();
				e_second.putInt("1", pos);
				e_second.commit();
			}
		});

		// Muscles Spinner
		ArrayAdapter<String> muscleAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, musclesArray);
		muscleAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		muscles.setAdapter(muscleAdapter);
		muscles.setPadding(0, 0, 0, 10);
		muscles.setSelection(prefs.getInt("1", 0));
		muscles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				String item = (String) parent.getItemAtPosition(pos);
				cMuscle = String.valueOf(pos - 1);

				SharedPreferences.Editor e_third = prefs.edit();
				e_third.putInt("2", pos + 1);
				e_third.commit();
			}
		});

		// Modality Spinner
		ArrayAdapter<String> modalityAdapter = new ArrayAdapter<String>(
				context, android.R.layout.simple_spinner_item, modalityArray);
		modalityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		modalities.setAdapter(modalityAdapter);
		modalities.setPadding(0, 0, 0, 10);
		modalities.setSelection(prefs.getInt("3", 0));
		modalities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int pos,
					long id) {
				String item = (String) parent.getItemAtPosition(pos);
				cModality = String.valueOf(pos - 1);
				SharedPreferences.Editor e_fourth = prefs.edit();
				e_fourth.putInt("3", pos);
				e_fourth.commit();
			}
		});
		
		
		LinearLayout.LayoutParams title_params = new LinearLayout.LayoutParams(
				150, LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams spinner_params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout hozlay_levels = new LinearLayout(context);
		hozlay_levels.setOrientation(LinearLayout.HORIZONTAL);
		title_levels.setLayoutParams(title_params);
		levels.setLayoutParams(spinner_params);
		hozlay_levels.addView(title_levels);
		hozlay_levels.addView(levels);
		LinearLayout hozlay_equips = new LinearLayout(context);
		hozlay_equips.setOrientation(LinearLayout.HORIZONTAL);
		title_equip.setLayoutParams(title_params);
		equips.setLayoutParams(spinner_params);
		hozlay_equips.addView(title_equip);
		hozlay_equips.addView(equips);
		LinearLayout hozlay_muscles = new LinearLayout(context);
		hozlay_muscles.setOrientation(LinearLayout.HORIZONTAL);
		title_muscles.setLayoutParams(title_params);
		muscles.setLayoutParams(spinner_params);
		hozlay_muscles.addView(title_muscles);
		hozlay_muscles.addView(muscles);
		LinearLayout hozlay_modalities = new LinearLayout(context);
		hozlay_modalities.setOrientation(LinearLayout.HORIZONTAL);
		title_modality.setLayoutParams(title_params);
		modalities.setLayoutParams(spinner_params);
		hozlay_modalities.addView(title_modality);
		hozlay_modalities.addView(modalities);
		

		linlay.addView(hozlay_levels);
		linlay.addView(hozlay_equips);
		linlay.addView(hozlay_muscles);
		linlay.addView(hozlay_modalities);
	}

	protected ArrayList<String> getLevelArray() {
		ArrayList<String> levels = new ArrayList<String>();

		SharedPreferences levelsPrefs = context.getSharedPreferences(LEVELS,
				Context.MODE_PRIVATE);

		int counter = 0;
		boolean addLevel = true;
		while (addLevel) {
			String currentLevel = levelsPrefs.getString(
					Integer.toString(counter), "");
			if (!currentLevel.equalsIgnoreCase("")) {
				Log.d("currentLevel", currentLevel);
				levels.add(currentLevel);
				counter += 1;
			} else {
				addLevel = false;
			}
		}

		return levels;
	}

	protected ArrayList<String> getUsedEquipment() {
		ArrayList<String> equips = new ArrayList<String>();
		equips.add("All Equipment");

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(context);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT DISTINCT equipment FROM exercises;", null);

		myCursor.moveToFirst();
		do {
			equips.add(myCursor.getString(0));
			myCursor.moveToNext();
		} while (!myCursor.isAfterLast());

		myCursor.close();
		db.close();

		return equips;
	}

	protected ArrayList<String> getMusclesArray() {
		ArrayList<String> muscles = new ArrayList<String>();
		SharedPreferences musclesPrefs = context.getSharedPreferences(
				MUSCLE_GROUPS, Context.MODE_PRIVATE);
		int counter = 0;
		boolean addMuscle = true;
		while (addMuscle) {
			String currentMuscle = musclesPrefs.getString(
					Integer.toString(counter), "");
			if (!currentMuscle.equalsIgnoreCase("")) {
				muscles.add(currentMuscle);
				counter += 1;
			} else {
				addMuscle = false;
			}
		}

		return muscles;
	}

	protected ArrayList<String> getModalityArray() {
		ArrayList<String> modalities = new ArrayList<String>();

		SharedPreferences modalitiesPrefs = context.getSharedPreferences(
				MODALITIES, Context.MODE_PRIVATE);

		int counter = 0;
		boolean addModality = true;
		while (addModality) {
			String currentMod = modalitiesPrefs.getString(
					Integer.toString(counter), "");
			if (!currentMod.equalsIgnoreCase("")) {
				modalities.add(currentMod);
				counter += 1;
			} else {
				addModality = false;
			}
		}

		return modalities;
	}

	// Classes
	private class OKListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			filterResult.finish(cLevel, cEquip, cMuscle, cModality);
		}

	}

	public void setDialogResult(OnResult dialogResult) {
		filterResult = dialogResult;
	}

	// Interface for result handling
	public interface OnResult {
		void finish(String level, String equip, String muscle, String modality);
	}

}
