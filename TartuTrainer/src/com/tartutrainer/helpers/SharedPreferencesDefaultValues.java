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
		e.putString("0", "All Modalities");
		e.putString("1", "Strength");
		e.putString("2", "Flexibility");
		e.putString("3", "Cardiovascular");
		e.putString("4", "Balance");
		e.putString("5", "Stability");
		e.putString("6", "Power");
		e.putString("7", "Plyometric");
		e.putString("8", "SAQ");
		e.putString("9", "Mobility");
		e.putString("10", "Endurance");
		e.putString("11", "Rehabilitation");
		e.commit();
	}
	private static void setMuscleGroups(Activity a) {
		SharedPreferences musclePrefs = a.getSharedPreferences(MUSCLE_GROUPS, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = musclePrefs.edit();
		e.putString("0", "All Muscles");
		e.putString("1", "Abs/Core");
		e.putString("2", "Calves");
		e.putString("3", "Full Body");
		e.putString("4", "Shoulders");
		e.putString("5", "Back");
		e.putString("6", "Chest");
		e.putString("7", "Gluteals");
		e.putString("8", "Triceps");
		e.putString("9", "Biceps");
		e.putString("10", "Forearms");
		e.putString("11", "Legs");
		e.putString("12", "Upper Body");
		e.commit();
	}
	private static void setLabels(Activity a) {
		SharedPreferences labelsPrefs = a.getSharedPreferences(LABELS, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = labelsPrefs.edit();
		e.putString("0", "All Labels");
		e.putString("1", "Reps");
		e.putString("2", "Lb.");
		e.putString("3", "Kg.");
		e.putString("4", "Sec.");
		e.putString("5", "Min.");
		e.commit();
	}
	private static void setCategories(Activity a) {
		SharedPreferences categoriesPrefs = a.getSharedPreferences(CATEGORIES, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = categoriesPrefs.edit();
		e.putString("0", "All Categories");
		e.putString("1", "Basic");
		e.putString("2", "Intermediate");
		e.putString("3", "Rehabilitation");
		e.putString("4", "Speed/Power");
		e.putString("5", "Core");
		e.putString("6", "Yoga");
		e.commit();
	}
}
