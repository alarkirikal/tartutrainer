package com.tartutrainer.activities;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.tartutrainer.R;
import com.tartutrainer.database.DBAdapter;

import android.R.color;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerciseActivity extends Activity implements OnLongClickListener,
		OnClickListener {

	ArrayList<String> excElements;
	TextView currentSelectedView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_exercise);

		fillContent();
		setNumpadListeners();
		setSelectionListeners();

		// Save Listener
		ImageButton imgBtn = (ImageButton) findViewById(R.id.goSaveChanges);
		imgBtn.setOnClickListener(this);

	}

	private String putNewItemTogether() {

		String item = "";

		// ID
		item += getIntent().getExtras().getString("exc_id") + ";";

		// Notes
		item += ((TextView) findViewById(R.id.exerciseNotes)).getText()
				.toString() + ";";

		// First Row
		item += ((TextView) findViewById(R.id.target_reps_first)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.target_lbs_first)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_reps_first)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_lbs_first)).getText()
				.toString() + ";";

		// Second Row
		item += ((TextView) findViewById(R.id.target_reps_second)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.target_lbs_second)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_reps_second)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_lbs_second)).getText()
				.toString() + ";";

		// Third Row
		item += ((TextView) findViewById(R.id.target_reps_third)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.target_lbs_third)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_reps_third)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_lbs_third)).getText()
				.toString() + ";";

		// Fourth Row
		item += ((TextView) findViewById(R.id.target_reps_fourth)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.target_lbs_fourth)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_reps_fourth)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_lbs_fourth)).getText()
				.toString() + ";";

		// Fifth Row
		item += ((TextView) findViewById(R.id.target_reps_fifth)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.target_lbs_fifth)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_reps_fifth)).getText()
				.toString() + ";";
		item += ((TextView) findViewById(R.id.actual_lbs_fifth)).getText()
				.toString();

		return item;
	}

	private void setNumpadListeners() {
		TextView n_one = (TextView) findViewById(R.id.exc_numpad_1);
		n_one.setOnClickListener(this);
		TextView n_two = (TextView) findViewById(R.id.exc_numpad_2);
		n_two.setOnClickListener(this);
		TextView n_thr = (TextView) findViewById(R.id.exc_numpad_3);
		n_thr.setOnClickListener(this);
		TextView n_fou = (TextView) findViewById(R.id.exc_numpad_4);
		n_fou.setOnClickListener(this);
		TextView n_fiv = (TextView) findViewById(R.id.exc_numpad_5);
		n_fiv.setOnClickListener(this);
		TextView n_six = (TextView) findViewById(R.id.exc_numpad_6);
		n_six.setOnClickListener(this);
		TextView n_sev = (TextView) findViewById(R.id.exc_numpad_7);
		n_sev.setOnClickListener(this);
		TextView n_eig = (TextView) findViewById(R.id.exc_numpad_8);
		n_eig.setOnClickListener(this);
		TextView n_nin = (TextView) findViewById(R.id.exc_numpad_9);
		n_nin.setOnClickListener(this);
		TextView n_del = (TextView) findViewById(R.id.exc_numpad_del);
		n_del.setOnClickListener(this);
		TextView n_zer = (TextView) findViewById(R.id.exc_numpad_0);
		n_zer.setOnClickListener(this);
		TextView n_dot = (TextView) findViewById(R.id.exc_numpad_dot);
		n_dot.setOnClickListener(this);
	}

	private void setSelectionListeners() {
		TextView aRepFirst = (TextView) findViewById(R.id.actual_reps_first);
		aRepFirst.setOnClickListener(this);
		TextView aLbsFirst = (TextView) findViewById(R.id.actual_lbs_first);
		aLbsFirst.setOnClickListener(this);
		TextView tRepFirst = (TextView) findViewById(R.id.target_reps_first);
		tRepFirst.setOnClickListener(this);
		TextView tLbsFirst = (TextView) findViewById(R.id.target_lbs_first);
		tLbsFirst.setOnClickListener(this);
		TextView aRepSecond = (TextView) findViewById(R.id.actual_reps_second);
		aRepSecond.setOnClickListener(this);
		TextView aLbsSecond = (TextView) findViewById(R.id.actual_lbs_second);
		aLbsSecond.setOnClickListener(this);
		TextView tRepSecond = (TextView) findViewById(R.id.target_reps_second);
		tRepSecond.setOnClickListener(this);
		TextView tLbsSecond = (TextView) findViewById(R.id.target_lbs_second);
		tLbsSecond.setOnClickListener(this);
		TextView aRepThird = (TextView) findViewById(R.id.actual_reps_third);
		aRepThird.setOnClickListener(this);
		TextView aLbsThird = (TextView) findViewById(R.id.actual_lbs_third);
		aLbsThird.setOnClickListener(this);
		TextView tRepThird = (TextView) findViewById(R.id.target_reps_third);
		tRepThird.setOnClickListener(this);
		TextView tLbsThird = (TextView) findViewById(R.id.target_lbs_third);
		tLbsThird.setOnClickListener(this);
		TextView aRepFourth = (TextView) findViewById(R.id.actual_reps_fourth);
		aRepFourth.setOnClickListener(this);
		TextView aLbsFourth = (TextView) findViewById(R.id.actual_lbs_fourth);
		aLbsFourth.setOnClickListener(this);
		TextView tRepFourth = (TextView) findViewById(R.id.target_reps_fourth);
		tRepFourth.setOnClickListener(this);
		TextView tLbsFourth = (TextView) findViewById(R.id.target_lbs_fourth);
		tLbsFourth.setOnClickListener(this);
		TextView aRepFifth = (TextView) findViewById(R.id.actual_reps_fifth);
		aRepFifth.setOnClickListener(this);
		TextView aLbsFifth = (TextView) findViewById(R.id.actual_lbs_fifth);
		aLbsFifth.setOnClickListener(this);
		TextView tRepFifth = (TextView) findViewById(R.id.target_reps_fifth);
		tRepFifth.setOnClickListener(this);
		TextView tLbsFifth = (TextView) findViewById(R.id.target_lbs_fifth);
		tLbsFifth.setOnClickListener(this);
	}

	private void fillContent() {

		excElements = new ArrayList<String>();

		String[] exc_data = getIntent().getExtras().getString("exc_data")
				.split(";", -1);
		for (String e : exc_data) {
			excElements.add(e);
		}

		// Title
		TextView title = (TextView) findViewById(R.id.exerciseHeader);
		title.setText(getIntent().getExtras().getString("exc_name"));

		// Notes
		TextView notes = (TextView) findViewById(R.id.exerciseNotes);
		notes.setText(excElements.get(1));

		// First Image
		ImageView imgOne = (ImageView) findViewById(R.id.exerciseFirstImg);
		String imgPathOne = Environment.getExternalStorageDirectory()
				+ "/Android/data/" + getApplicationContext().getPackageName()
				+ "/Files/" + getIntent().getExtras().getString("exc_id") + "1.png";
		File imgFileOne = new File(imgPathOne);
		if (imgFileOne.exists()) {

			Bitmap myBitmapOne = BitmapFactory.decodeFile(imgFileOne
					.getAbsolutePath());
			imgOne.setImageBitmap(myBitmapOne);
		} else {
			try {
				String uriFirst = "@drawable/img_"
						+ getIntent().getExtras().getString("exc_id")
								.replaceAll("-", "_") + "1";
				int imageResourceFirst = getResources().getIdentifier(uriFirst,
						null, getPackageName());
				Drawable resOne = getResources()
						.getDrawable(imageResourceFirst);
				imgOne.setImageDrawable(resOne);
			} catch (NotFoundException n) {
				String uriFirst = "@drawable/img_notavailable";
				int imgRes = getResources().getIdentifier(uriFirst, null,
						getPackageName());
				Drawable res = getResources().getDrawable(imgRes);
				imgOne.setImageDrawable(res);
			}
		}

		// Second Image
		ImageView imgTwo = (ImageView) findViewById(R.id.exerciseSecondImg);
		String imgPathTwo = Environment.getExternalStorageDirectory()
				+ "/Android/data/" + getApplicationContext().getPackageName()
				+ "/Files/" + getIntent().getExtras().getString("exc_id") + "2.png";
		File imgFileTwo = new File(imgPathTwo);
		if (imgFileTwo.exists()) {

			Bitmap myBitmapTwo = BitmapFactory.decodeFile(imgFileTwo
					.getAbsolutePath());
			imgTwo.setImageBitmap(myBitmapTwo);
		} else {
			try {
				String uriSecond = "@drawable/img_"
						+ getIntent().getExtras().getString("exc_id")
								.replaceAll("-", "_") + "2";
				int imageResourceSecond = getResources().getIdentifier(
						uriSecond, null, getPackageName());
				Drawable resTwo = getResources().getDrawable(
						imageResourceSecond);
				imgTwo.setImageDrawable(resTwo);
			} catch (NotFoundException n) {
				String uriSecond = "@drawable/img_notavailable";
				int imgRes = getResources().getIdentifier(uriSecond, null,
						getPackageName());
				Drawable res = getResources().getDrawable(imgRes);
				imgTwo.setImageDrawable(res);
			}
		}

		// Table elements
		TextView tRepsFirst = (TextView) findViewById(R.id.target_reps_first);
		tRepsFirst.setText(excElements.get(2));
		tRepsFirst.setOnLongClickListener(this);
		TextView tLbsFirst = (TextView) findViewById(R.id.target_lbs_first);
		tLbsFirst.setText(excElements.get(3));
		tLbsFirst.setOnLongClickListener(this);
		TextView aRepsFirst = (TextView) findViewById(R.id.actual_reps_first);
		aRepsFirst.setText(excElements.get(4));
		TextView aLbsFirst = (TextView) findViewById(R.id.actual_lbs_first);
		aLbsFirst.setText(excElements.get(5));
		TextView tRepsSecond = (TextView) findViewById(R.id.target_reps_second);
		tRepsSecond.setText(excElements.get(6));
		tRepsSecond.setOnLongClickListener(this);
		TextView tLbsSecond = (TextView) findViewById(R.id.target_lbs_second);
		tLbsSecond.setText(excElements.get(7));
		tLbsSecond.setOnLongClickListener(this);
		TextView aRepsSecond = (TextView) findViewById(R.id.actual_reps_second);
		aRepsSecond.setText(excElements.get(8));
		TextView aLbsSecond = (TextView) findViewById(R.id.actual_lbs_second);
		aLbsSecond.setText(excElements.get(9));
		TextView tRepsThird = (TextView) findViewById(R.id.target_reps_third);
		tRepsThird.setText(excElements.get(10));
		tRepsThird.setOnLongClickListener(this);
		TextView tLbsThird = (TextView) findViewById(R.id.target_lbs_third);
		tLbsThird.setText(excElements.get(11));
		tLbsThird.setOnLongClickListener(this);
		TextView aRepsThird = (TextView) findViewById(R.id.actual_reps_third);
		aRepsThird.setText(excElements.get(12));
		TextView aLbsThird = (TextView) findViewById(R.id.actual_lbs_third);
		aLbsThird.setText(excElements.get(13));
		TextView tRepsFourth = (TextView) findViewById(R.id.target_reps_fourth);
		tRepsFourth.setText(excElements.get(14));
		tRepsFourth.setOnLongClickListener(this);
		TextView tLbsFourth = (TextView) findViewById(R.id.target_lbs_fourth);
		tLbsFourth.setText(excElements.get(15));
		tLbsFourth.setOnLongClickListener(this);
		TextView aRepsFourth = (TextView) findViewById(R.id.actual_reps_fourth);
		aRepsFourth.setText(excElements.get(16));
		TextView aLbsFourth = (TextView) findViewById(R.id.actual_lbs_fourth);
		aLbsFourth.setText(excElements.get(17));
		TextView tRepsFifth = (TextView) findViewById(R.id.target_reps_fifth);
		tRepsFifth.setText(excElements.get(18));
		tRepsFifth.setOnLongClickListener(this);
		TextView tLbsFifth = (TextView) findViewById(R.id.target_lbs_fifth);
		tLbsFifth.setText(excElements.get(19));
		tLbsFifth.setOnLongClickListener(this);
		TextView aRepsFifth = (TextView) findViewById(R.id.actual_reps_fifth);
		TextView aLbsFifth = (TextView) findViewById(R.id.actual_lbs_fifth);

		try {
			aRepsFifth.setText(excElements.get(20));
		} catch (IndexOutOfBoundsException i) {
			aRepsFifth.setText("");
		}
		try {
			aLbsFifth.setText(excElements.get(21));
		} catch (IndexOutOfBoundsException i) {
			aLbsFifth.setText("");
		}
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {

		/** Listeners for autofilling */

		case R.id.target_reps_first:
			TextView tRepFirst = (TextView) findViewById(R.id.target_reps_first);
			TextView aRepFirst = (TextView) findViewById(R.id.actual_reps_first);
			aRepFirst.setText(tRepFirst.getText().toString());
			break;

		case R.id.target_lbs_first:
			TextView tLbsFirst = (TextView) findViewById(R.id.target_lbs_first);
			TextView aLbsFirst = (TextView) findViewById(R.id.actual_lbs_first);
			aLbsFirst.setText(tLbsFirst.getText().toString());
			break;

		case R.id.target_reps_second:
			TextView tRepSecond = (TextView) findViewById(R.id.target_reps_second);
			TextView aRepSecond = (TextView) findViewById(R.id.actual_reps_second);
			aRepSecond.setText(tRepSecond.getText().toString());
			break;

		case R.id.target_lbs_second:
			TextView tLbsSecond = (TextView) findViewById(R.id.target_lbs_second);
			TextView aLbsSecond = (TextView) findViewById(R.id.actual_lbs_second);
			aLbsSecond.setText(tLbsSecond.getText().toString());
			break;

		case R.id.target_reps_third:
			TextView tRepThird = (TextView) findViewById(R.id.target_reps_third);
			TextView aRepThird = (TextView) findViewById(R.id.actual_reps_third);
			aRepThird.setText(tRepThird.getText().toString());
			break;

		case R.id.target_lbs_third:
			TextView tLbsThird = (TextView) findViewById(R.id.target_lbs_third);
			TextView aLbsThird = (TextView) findViewById(R.id.actual_lbs_third);
			aLbsThird.setText(tLbsThird.getText().toString());
			break;

		case R.id.target_reps_fourth:
			TextView tRepFourth = (TextView) findViewById(R.id.target_reps_fourth);
			TextView aRepFourth = (TextView) findViewById(R.id.actual_reps_fourth);
			aRepFourth.setText(tRepFourth.getText().toString());
			break;

		case R.id.target_lbs_fourth:
			TextView tLbsFourth = (TextView) findViewById(R.id.target_lbs_fourth);
			TextView aLbsFourth = (TextView) findViewById(R.id.actual_lbs_fourth);
			aLbsFourth.setText(tLbsFourth.getText().toString());
			break;

		case R.id.target_reps_fifth:
			TextView tRepFifth = (TextView) findViewById(R.id.target_reps_fifth);
			TextView aRepFifth = (TextView) findViewById(R.id.actual_reps_fifth);
			aRepFifth.setText(tRepFifth.getText().toString());
			break;

		case R.id.target_lbs_fifth:
			TextView tLbsFifth = (TextView) findViewById(R.id.target_lbs_fifth);
			TextView aLbsFifth = (TextView) findViewById(R.id.actual_lbs_fifth);
			aLbsFifth.setText(tLbsFifth.getText().toString());
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/** Listeners for numpad input */

		case R.id.exc_numpad_1:
			if (currentSelectedView != null) {
				String currentOne = currentSelectedView.getText().toString();
				if (currentOne.equalsIgnoreCase("")) {
					currentSelectedView.setText("1");
				} else {
					if (currentOne.length() <= 2) {
						currentSelectedView.setText(currentOne + "1");
					}
				}
			}
			break;

		case R.id.exc_numpad_2:
			if (currentSelectedView != null) {
				String currentTwo = currentSelectedView.getText().toString();
				if (currentTwo.equalsIgnoreCase("")) {
					currentSelectedView.setText("2");
				} else {
					if (currentTwo.length() <= 2) {
						currentSelectedView.setText(currentTwo + "2");
					}
				}
			}
			break;

		case R.id.exc_numpad_3:
			if (currentSelectedView != null) {
				String currentThree = currentSelectedView.getText().toString();
				if (currentThree.equalsIgnoreCase("")) {
					currentSelectedView.setText("3");
				} else {
					if (currentThree.length() <= 2) {
						currentSelectedView.setText(currentThree + "3");
					}
				}
			}
			break;

		case R.id.exc_numpad_4:
			if (currentSelectedView != null) {
				String currentFour = currentSelectedView.getText().toString();
				if (currentFour.equalsIgnoreCase("")) {
					currentSelectedView.setText("4");
				} else {
					if (currentFour.length() <= 2) {
						currentSelectedView.setText(currentFour + "4");
					}
				}
			}
			break;

		case R.id.exc_numpad_5:
			if (currentSelectedView != null) {
				String currentFive = currentSelectedView.getText().toString();
				if (currentFive.equalsIgnoreCase("")) {
					currentSelectedView.setText("5");
				} else {
					if (currentFive.length() <= 2) {
						currentSelectedView.setText(currentFive + "5");
					}
				}
			}
			break;

		case R.id.exc_numpad_6:
			if (currentSelectedView != null) {
				String currentSix = currentSelectedView.getText().toString();
				if (currentSix.equalsIgnoreCase("")) {
					currentSelectedView.setText("6");
				} else {
					if (currentSix.length() <= 2) {
						currentSelectedView.setText(currentSix + "6");
					}
				}
			}
			break;

		case R.id.exc_numpad_7:
			if (currentSelectedView != null) {
				String currentSeven = currentSelectedView.getText().toString();
				if (currentSeven.equalsIgnoreCase("")) {
					currentSelectedView.setText("7");
				} else {
					if (currentSeven.length() <= 2) {
						currentSelectedView.setText(currentSeven + "7");
					}
				}
			}
			break;

		case R.id.exc_numpad_8:
			if (currentSelectedView != null) {
				String currentEight = currentSelectedView.getText().toString();
				if (currentEight.equalsIgnoreCase("")) {
					currentSelectedView.setText("8");
				} else {
					if (currentEight.length() <= 2) {
						currentSelectedView.setText(currentEight + "8");
					}
				}
			}
			break;

		case R.id.exc_numpad_9:
			if (currentSelectedView != null) {
				String currentNine = currentSelectedView.getText().toString();
				if (currentNine.equalsIgnoreCase("")) {
					currentSelectedView.setText("9");
				} else {
					if (currentNine.length() <= 2) {
						currentSelectedView.setText(currentNine + "9");
					}
				}
			}
			break;

		case R.id.exc_numpad_del:
			if (currentSelectedView != null) {
				String currentDel = currentSelectedView.getText().toString();
				if (!currentDel.equalsIgnoreCase("")) {
					currentSelectedView.setText(currentDel.replace(
							currentDel.substring(currentDel.length() - 1), ""));
				}
			}
			break;

		case R.id.exc_numpad_0:
			if (currentSelectedView != null) {
				String currentZero = currentSelectedView.getText().toString();
				if (currentZero.equalsIgnoreCase("")) {
					currentSelectedView.setText("0");
				} else {
					if (currentZero.length() <= 2) {
						currentSelectedView.setText(currentZero + "0");
					}
				}
			}
			break;

		case R.id.exc_numpad_dot:
			if (currentSelectedView != null) {
				String currentDot = currentSelectedView.getText().toString();
				if (currentDot.equalsIgnoreCase("")) {
					currentSelectedView.setText(".");
				} else {
					if (currentDot.length() <= 2) {
						currentSelectedView.setText(currentDot + ".");
					}
				}
			}
			break;

		/** Listeners for making current cell selection */
		case R.id.actual_reps_first:
		case R.id.actual_lbs_first:
		case R.id.actual_reps_second:
		case R.id.actual_lbs_second:
		case R.id.actual_reps_third:
		case R.id.actual_lbs_third:
		case R.id.actual_reps_fourth:
		case R.id.actual_lbs_fourth:
		case R.id.actual_reps_fifth:
		case R.id.actual_lbs_fifth:
		case R.id.target_reps_first:
		case R.id.target_lbs_first:
		case R.id.target_reps_second:
		case R.id.target_lbs_second:
		case R.id.target_reps_third:
		case R.id.target_lbs_third:
		case R.id.target_reps_fourth:
		case R.id.target_lbs_fourth:
		case R.id.target_reps_fifth:
		case R.id.target_lbs_fifth:

			TextView repsFirst = (TextView) findViewById(R.id.actual_reps_first);
			repsFirst.setBackgroundColor(Color.TRANSPARENT);
			TextView lbsFirst = (TextView) findViewById(R.id.actual_lbs_first);
			lbsFirst.setBackgroundColor(Color.TRANSPARENT);
			TextView repsSecond = (TextView) findViewById(R.id.actual_reps_second);
			repsSecond.setBackgroundColor(Color.TRANSPARENT);
			TextView lbsSecond = (TextView) findViewById(R.id.actual_lbs_second);
			lbsSecond.setBackgroundColor(Color.TRANSPARENT);
			TextView repsThird = (TextView) findViewById(R.id.actual_reps_third);
			repsThird.setBackgroundColor(Color.TRANSPARENT);
			TextView lbsThird = (TextView) findViewById(R.id.actual_lbs_third);
			lbsThird.setBackgroundColor(Color.TRANSPARENT);
			TextView repsFourth = (TextView) findViewById(R.id.actual_reps_fourth);
			repsFourth.setBackgroundColor(Color.TRANSPARENT);
			TextView lbsFourth = (TextView) findViewById(R.id.actual_lbs_fourth);
			lbsFourth.setBackgroundColor(Color.TRANSPARENT);
			TextView repsFifth = (TextView) findViewById(R.id.actual_reps_fifth);
			repsFifth.setBackgroundColor(Color.TRANSPARENT);
			TextView lbsFifth = (TextView) findViewById(R.id.actual_lbs_fifth);
			lbsFifth.setBackgroundColor(Color.TRANSPARENT);
			TextView trepsFirst = (TextView) findViewById(R.id.target_reps_first);
			trepsFirst.setBackgroundColor(Color.TRANSPARENT);
			TextView tlbsFirst = (TextView) findViewById(R.id.target_lbs_first);
			tlbsFirst.setBackgroundColor(Color.TRANSPARENT);
			TextView trepsSecond = (TextView) findViewById(R.id.target_reps_second);
			trepsSecond.setBackgroundColor(Color.TRANSPARENT);
			TextView tlbsSecond = (TextView) findViewById(R.id.target_lbs_second);
			tlbsSecond.setBackgroundColor(Color.TRANSPARENT);
			TextView trepsThird = (TextView) findViewById(R.id.target_reps_third);
			trepsThird.setBackgroundColor(Color.TRANSPARENT);
			TextView tlbsThird = (TextView) findViewById(R.id.target_lbs_third);
			tlbsThird.setBackgroundColor(Color.TRANSPARENT);
			TextView trepsFourth = (TextView) findViewById(R.id.target_reps_fourth);
			trepsFourth.setBackgroundColor(Color.TRANSPARENT);
			TextView tlbsFourth = (TextView) findViewById(R.id.target_lbs_fourth);
			tlbsFourth.setBackgroundColor(Color.TRANSPARENT);
			TextView trepsFifth = (TextView) findViewById(R.id.target_reps_fifth);
			trepsFifth.setBackgroundColor(Color.TRANSPARENT);
			TextView tlbsFifth = (TextView) findViewById(R.id.target_lbs_fifth);
			tlbsFifth.setBackgroundColor(Color.TRANSPARENT);

			if (currentSelectedView == v) {
				currentSelectedView = null;
			} else {
				currentSelectedView = (TextView) findViewById(v.getId());
				currentSelectedView.setBackgroundColor(Color.LTGRAY);
			}
			break;

		case R.id.goSaveChanges:

			ArrayList<String> exercisesArray = new ArrayList<String>();
			String exercisesReplace = "";

			// Read data
			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(this);
			db.openDataBase();

			Cursor myCursor = db.getReadableDatabase().rawQuery(
					"SELECT items FROM programs WHERE id LIKE '"
							+ getIntent().getExtras().getString("pgr_id")
							+ "';", null);

			myCursor.moveToFirst();
			String old = myCursor.getString(0);
			String[] exercises = myCursor.getString(0).split(":");
			for (String exc : exercises) {
				exercisesArray.add(exc);
			}

			myCursor.close();
			db.close();

			// Write new data
			String newPgrItem = putNewItemTogether();

			for (String element : exercisesArray) {
				if (element.split(";")[0].equalsIgnoreCase(getIntent()
						.getExtras().getString("exc_id"))) {
					exercisesReplace += newPgrItem;
				} else {
					exercisesReplace += element;
				}
				exercisesReplace += ":";
			}

			exercisesReplace = exercisesReplace.substring(0,
					exercisesReplace.length() - 1);

			Log.d("old items", old);
			Log.d("new items", exercisesReplace);

			DBAdapter db_write = null;
			db_write = DBAdapter.getDBAdapterInstance(this);
			db_write.openDataBase();

			ContentValues args = new ContentValues();
			args.put("items", exercisesReplace);
			db_write.getWritableDatabase().update(
					"programs",
					args,
					"id LIKE '" + getIntent().getExtras().getString("pgr_id")
							+ "'", null);

			db_write.close();
			finish();
			break;
		}
	}
}
