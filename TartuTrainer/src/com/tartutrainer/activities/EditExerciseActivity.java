package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.tartutrainer.R;
import com.tartutrainer.database.DBAdapter;
import com.tartutrainer.fragments.AllExercisesFragment;
import com.tartutrainer.models.Exercise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditExerciseActivity extends Activity implements OnClickListener {

	/*
	 * Activity that edits the details of an exercise in the exercises table (or
	 * creates a new one)
	 */

	ArrayList<String> levelArray;
	ArrayList<String> labelArray;
	ArrayList<String> modalityArray;
	ArrayList<String> equipArray;
	ArrayList<String> musclesArray;
	private AlertDialog Dialog;
	boolean[] checkedMuscles;

	Exercise exc;

	private final static String LEVELS = "levels";
	private final static String MODALITIES = "modalities";
	private final static String MUSCLE_GROUPS = "muscle_groups";
	private final static String LABELS = "labels";

	int levelID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editexercise);

		// Set the view
		String actionString = getIntent().getExtras().getString("action");

		// Set content
		TextView excAction = (TextView) findViewById(R.id.editExerciseAction);
		if (actionString.equalsIgnoreCase("Edit Exercise")) {
			excAction.setText("Edit Exercise");
			// Get content from the database
			String id = getIntent().getExtras().getString("exc_id");
			getExerciseFromDatabase(id);
			fillContentByEdit();
		} else {
			excAction.setText("New Exercise");
			exc = new Exercise();
			exc.setId("");
			exc.setName("");
			exc.setDescription("");
			exc.setLevel(0);
			exc.setModality(0);
			exc.setMuscles("");
			exc.setEquipment("");
			exc.setLabel_1("");
			exc.setLabel_2("");
			exc.setOwned("true");
			exc.setCategory(0);
		}

		// Get the value arrays to use in editing
		labelArray = getUsedLabelPairs();
		equipArray = getUsedEquipment();
		levelArray = getLevelArray();
		modalityArray = getModalityArray();
		musclesArray = getMusclesArray();

		// Set listeners
		setOnClickListeners();
	}

	protected ArrayList<String> getMusclesArray() {
		ArrayList<String> muscles = new ArrayList<String>();
		SharedPreferences musclesPrefs = this.getSharedPreferences(
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

		SharedPreferences modalitiesPrefs = this.getSharedPreferences(
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

	protected ArrayList<String> getLevelArray() {
		ArrayList<String> levels = new ArrayList<String>();

		SharedPreferences levelsPrefs = this.getSharedPreferences(LEVELS,
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

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
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

	protected ArrayList<String> getUsedLabelPairs() {
		SharedPreferences labelsPrefs = getSharedPreferences(LABELS,
				Context.MODE_PRIVATE);

		ArrayList<String> labelPairs = new ArrayList<String>();

		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT DISTINCT label_1, label_2 FROM exercises;", null);

		myCursor.moveToFirst();
		do {
			if (myCursor.getString(0).length() == 1
					&& myCursor.getString(1).length() == 1) {
				labelPairs.add(labelsPrefs.getString(myCursor.getString(0), "")
						+ " and "
						+ labelsPrefs.getString(myCursor.getString(1), ""));
			} else {
				labelPairs.add(myCursor.getString(0) + " + "
						+ myCursor.getString(1));
			}
			myCursor.moveToNext();
		} while (!myCursor.isAfterLast());

		myCursor.close();
		db.close();

		return labelPairs;
	}

	protected void getExerciseFromDatabase(String id) {
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		db.openDataBase();

		Cursor myCursor = db.getReadableDatabase().rawQuery(
				"SELECT * FROM exercises WHERE id LIKE '" + id + "';", null);

		myCursor.moveToFirst();
		exc = new Exercise();
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

		myCursor.moveToNext();

		myCursor.close();
		db.close();
	}

	protected void fillContentByEdit() {

		SharedPreferences labelsPrefs = getSharedPreferences(LABELS,
				Context.MODE_PRIVATE);
		SharedPreferences levelsPrefs = getSharedPreferences(LEVELS,
				Context.MODE_PRIVATE);
		SharedPreferences modsPrefs = getSharedPreferences(MODALITIES,
				Context.MODE_PRIVATE);
		SharedPreferences musclePrefs = getSharedPreferences(MUSCLE_GROUPS,
				Context.MODE_PRIVATE);

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
	}

	private void setOnClickListeners() {
		/* Go to ClientsActivity */
		ImageButton saveExercise = (ImageButton) findViewById(R.id.editExerciseSave);
		saveExercise.setOnClickListener(this);

		ImageView label2 = (ImageView) findViewById(R.id.editLabelTwoImg);
		label2.setOnClickListener(this);

		ImageView equipment = (ImageView) findViewById(R.id.editEquipmentImg);
		equipment.setOnClickListener(this);

		ImageView levels = (ImageView) findViewById(R.id.editLevelImg);
		levels.setOnClickListener(this);

		TextView level = (TextView) findViewById(R.id.editExerciseLevel);
		level.setOnClickListener(this);

		TextView modalityText = (TextView) findViewById(R.id.editExerciseModality);
		modalityText.setOnClickListener(this);

		ImageView modality = (ImageView) findViewById(R.id.editModalityImg);
		modality.setOnClickListener(this);

		TextView muscleText = (TextView) findViewById(R.id.editExerciseMuscleGroups);
		muscleText.setOnClickListener(this);

		ImageView muscles = (ImageView) findViewById(R.id.editMuscleGroupsImg);
		muscles.setOnClickListener(this);

	}

	private boolean saveExerciseToDatabase() {

		// Set EditText fields to exc object
		EditText excName = (EditText) findViewById(R.id.editExerciseName);
		exc.setName(excName.getText().toString());
		EditText excDesc = (EditText) findViewById(R.id.editExerciseDescription);
		exc.setDescription(excDesc.getText().toString());

		if (exc.getName().length() > 4 && exc.getDescription().length() > 4) {

			// Set initial values, if exercise is new or not
			if (!(exc.getOwned().length() > 0)) {
				exc.setOwned("true");
			}

			// Add the exercise to the database
			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(this);
			db.openDataBase();

			ContentValues args = new ContentValues();
			args.put("name", exc.getName());
			args.put("description", exc.getDescription());
			args.put("level", exc.getLevel());
			args.put("modality", exc.getModality());
			args.put("muscles", exc.getMuscles());
			args.put("equipment", exc.getEquipment());
			args.put("label_1", exc.getLabel_1());
			args.put("label_2", exc.getLabel_2());
			args.put("owned", "true");
			args.put("category", exc.getCategory());

			if (exc.getId().length() > 0) {
				Log.d("[edit]", "uuendan vana");
				db.getWritableDatabase().update("exercises", args,
						"id == '" + exc.getId() + "'", null);
			} else {
				Log.d("[edit]", "uus sisestus");
				exc.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				args.put("id", exc.getId());
				long smth = db.getWritableDatabase().insert("exercises", null,
						args);
				Log.d("long", Long.toString(smth));
				/*
				 * String sql = "insert into "; sql +=
				 * "exercises(id, name, description, level, modality, muscles, equipment, label_1, label_2, owned, category) "
				 * ; sql += "values ("; sql += "'" + exc.getId() + "', "; sql +=
				 * "'" + exc.getName() + "', "; sql += "'" +
				 * exc.getDescription() + "', "; sql += "'" + exc.getLevel() +
				 * "', "; sql += "'" + exc.getModality() + "', "; sql += "'" +
				 * exc.getMuscles() + "', "; sql += "'" + exc.getEquipment() +
				 * "', "; sql += "'" + exc.getLabel_1() + "', "; sql += "'" +
				 * exc.getLabel_2() + "', "; sql += "'" + exc.getOwned() +
				 * "', "; sql += "'" + exc.getCategory() + "');";
				 * 
				 * Log.d("sql", sql); db.getWritableDatabase().execSQL(sql);
				 */
			}

			db.close();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editExerciseSave:
			boolean succesful = saveExerciseToDatabase();
			if (succesful) {
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("select_tab", "exercise");
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(),
						"Name and description must be longer!",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.editLabelTwoImg:
			final String[] labelList = labelArray.toArray(new String[labelArray
					.size()]);

			final EditText labelOne = (EditText) findViewById(R.id.editExerciseLabelOne);
			final EditText labelTwo = (EditText) findViewById(R.id.editExerciseLabelTwo);

			// dialog builder
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle("Previously used labels");
			builder2.setItems(labelList, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String[] labels = labelArray.get(which).split("and");
					labelOne.setText(labels[0].replaceAll(" ", ""));
					labelTwo.setText(labels[1].replaceAll(" ", ""));

					exc.setLabel_1(labels[0].replaceAll(" ", ""));
					exc.setLabel_2(labels[1].replaceAll(" ", ""));
				}
			});
			Dialog = builder2.create();
			Dialog.show();
			break;

		case R.id.editEquipmentImg:
			final String[] equipment_list = equipArray
					.toArray(new String[equipArray.size()]);

			final EditText equipment = (EditText) findViewById(R.id.editExerciseEquipment);

			// dialog builder
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setTitle("Previously used equipment");
			builder3.setItems(equipment_list,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							equipment.setText(equipment_list[which]);

							exc.setEquipment(equipment_list[which]);
						}
					});
			builder3.setCancelable(false);
			Dialog = builder3.create();
			Dialog.show();
			break;

		case R.id.editLevelImg:
			final String[] level_list = levelArray
					.toArray(new String[levelArray.size()]);

			final TextView level = (TextView) findViewById(R.id.editExerciseLevel);
			final SharedPreferences levelImgPrefs = this.getSharedPreferences(
					LEVELS, Context.MODE_PRIVATE);

			// dialog builder
			AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
			builder4.setTitle("Levels");
			builder4.setItems(level_list,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							level.setText(levelImgPrefs.getString(
									Integer.toString(which), ""));

							exc.setLevel(which);
						}
					});
			builder4.setCancelable(false);
			Dialog = builder4.create();
			Dialog.show();
			break;

		case R.id.editExerciseLevel:
			TextView text = (TextView) findViewById(R.id.editExerciseLevel);
			final SharedPreferences levelClickPrefs = this
					.getSharedPreferences(LEVELS, Context.MODE_PRIVATE);

			int currentLevel = exc.getLevel();
			if (currentLevel == 4) {
				exc.setLevel(0);
			} else {
				exc.setLevel(currentLevel + 1);
			}
			text.setText(levelClickPrefs.getString(
					Integer.toString(exc.getLevel()), ""));
			break;

		case R.id.editExerciseModality:
		case R.id.editModalityImg:
			final String[] modality_list = modalityArray
					.toArray(new String[modalityArray.size()]);

			final TextView modality = (TextView) findViewById(R.id.editExerciseModality);

			// dialog builder
			AlertDialog.Builder builder5 = new AlertDialog.Builder(this);
			builder5.setTitle("Modality");
			builder5.setItems(modality_list,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							modality.setText(modality_list[which]);

							exc.setModality(which);
						}
					});
			builder5.setCancelable(false);
			Dialog = builder5.create();
			Dialog.show();
			break;

		case R.id.editExerciseMuscleGroups:
		case R.id.editMuscleGroupsImg:
			final String[] muscles_list = musclesArray
					.toArray(new String[musclesArray.size()]);
			final TextView muscles = (TextView) findViewById(R.id.editExerciseMuscleGroups);
			final ArrayList<Integer> selectedItems = new ArrayList<Integer>();
			checkedMuscles = new boolean[musclesArray.size()];
			String[] selectedMuscles = exc.getMuscles().split(";");
			for (String index : selectedMuscles) {
				if (index != "") {
					checkedMuscles[Integer.parseInt(index)] = true;
				}
			}

			AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
			builder6.setTitle("Muscles");
			builder6.setMultiChoiceItems(muscles_list, checkedMuscles,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							if (isChecked) {
								selectedItems.add(which);
								checkedMuscles[which] = true;
							} else if (selectedItems.contains(which)) {
								selectedItems.remove(Integer.valueOf(which));
								checkedMuscles[which] = false;
							}
						}
					})
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									if (selectedItems.size() == 0) {
										dialog.dismiss();
									} else {
										StringBuilder sb = new StringBuilder();
										int counter = 0;
										String musclesToSet = "";
										for (boolean checked : checkedMuscles) {
											if (checked) {
												sb.append(muscles_list[counter]
														+ ";");
												musclesToSet += Integer
														.toString(counter)
														+ ";";
											}
											counter += 1;
										}

										musclesToSet = musclesToSet.substring(
												0, musclesToSet.length() - 1);
										exc.setMuscles(musclesToSet);

										String musclesString = sb
												.toString()
												.substring(
														0,
														sb.toString().length() - 1);
										muscles.setText(musclesString
												.replaceAll(";", "\r\n"));
										dialog.dismiss();
									}
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});

			Dialog = builder6.create();
			Dialog.show();
			break;

		}

	}

}