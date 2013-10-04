package com.tartutrainer.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesDefaultValues {
	
	private final static String LEVELS = "levels";
	private final static String MODALITIES = "modalities";
	private final static String MUSCLE_GROUPS = "muscle_groups";
	private final static String LABELS = "labels";
	private final static String CATEGORIES = "categories";
	
	
	public static void init(Activity a) {
		setLevels(a);
		setModalities(a);
		setMuscleGroups(a);
		setLabels(a);
		setCategories(a);
	}
	
	private static void setLevels(Activity a) {
		SharedPreferences levelsPrefs = a.getSharedPreferences(LEVELS, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = levelsPrefs.edit();
		e.putString("0", "All Levels");
		e.putString("1", "Beginner");
		e.putString("2", "Novice");
		e.putString("3", "Intermediate");
		e.putString("4", "Advanced");
		e.commit();
	}
	private static void setModalities(Activity a) {
		SharedPreferences modalitiesPrefs = a.getSharedPreferences(MODALITIES, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = modalitiesPrefs.edit();
		e.putString("0", "Strength");
		e.putString("1", "Flexibility");
		e.putString("2", "Cardiovascular");
		e.putString("3", "Balance");
		e.putString("4", "Stability");
		e.putString("5", "Power");
		e.putString("6", "Plyometric");
		e.putString("7", "SAQ");
		e.putString("8", "Mobility");
		e.putString("9", "Endurance");
		e.putString("10", "Rehabilitation");
		e.commit();
	}
	private static void setMuscleGroups(Activity a) {
		SharedPreferences musclePrefs = a.getSharedPreferences(MUSCLE_GROUPS, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = musclePrefs.edit();
		e.putString("0", "Abs/Core");
		e.putString("1", "Calves");
		e.putString("2", "Full Body");
		e.putString("3", "Shoulders");
		e.putString("4", "Back");
		e.putString("5", "Chest");
		e.putString("6", "Gluteals");
		e.putString("7", "Triceps");
		e.putString("8", "Biceps");
		e.putString("9", "Forearms");
		e.putString("10", "Legs");
		e.putString("11", "Upper Body");
		e.commit();
	}
	private static void setLabels(Activity a) {
		SharedPreferences labelsPrefs = a.getSharedPreferences(LABELS, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = labelsPrefs.edit();
		e.putString("0", "Reps");
		e.putString("1", "Lb.");
		e.putString("2", "Kg.");
		e.putString("3", "Sec.");
		e.putString("4", "Min.");
		e.commit();
	}
	private static void setCategories(Activity a) {
		SharedPreferences categoriesPrefs = a.getSharedPreferences(CATEGORIES, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = categoriesPrefs.edit();
		e.putString("0", "Basic");
		e.putString("1", "Intermediate");
		e.putString("2", "Rehabilitation");
		e.putString("3", "Speed/Power");
		e.putString("4", "Core");
		e.putString("5", "Yoga");
		e.commit();
	}
}
