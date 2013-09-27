package com.tartutrainer.helpers;

import java.util.ArrayList;

import com.tartutrainer.R;
import com.tartutrainer.fragments.AllExercisesFragment;
import com.tartutrainer.fragments.AllProgramsFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class FragmentBuilder extends Fragment {

	private static final String IDENTIFIER = "identifier";

	public static final FragmentBuilder newInstance(String identifier) {
		FragmentBuilder f = new FragmentBuilder();
		Bundle bdl = new Bundle(1);
		bdl.putString(IDENTIFIER, identifier);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get the identifier of the specific fragment
		String message = getArguments().getString(IDENTIFIER);

		// Get available forecast dates as fragment is created

		if (message.equalsIgnoreCase("allprograms")) {
			/*
			View v = inflater.inflate(R.layout.fragment_forecastlist,
					container, false);
			v = addUpperElementsToView(v, "forecast");

			return fillForecastFragment(v, availableDates.get(0));
			*/
			
			View v = inflater.inflate(R.layout.fragment_allprograms, container, false);
			return v;
			//AllProgramsFragment allPrograms = new AllProgramsFragment();
			//return allPrograms.getView();
		
		} else if (message.equalsIgnoreCase("allexercises")) {
			// Current weather fragment
			/*
			View v = inflater.inflate(R.layout.fragment_current, container,
					false);
			v = addUpperElementsToView(v, "current");
			return fillObservationFragment(v, availableLocations.get(0));
			*/
			
			View v = inflater.inflate(R.layout.fragment_allexercises, container, false);
			return v;
			
			//AllExercisesFragment allExercises = new AllExercisesFragment();
			//return allExercises.getView();
		} else {
			/*
			View v = inflater.inflate(R.layout.fragment_forecastlist,
					container, false);
			return v;
			*/
			View v = inflater.inflate(R.layout.fragment_allexercises, container, false);
			return v;
		}
	}

	/**
	 * Method to add the upper spinner and fields to the fragment layout
	 * 
	 * @param v
	 *            - View object to connect objects with layout fields
	 * @param identifier
	 *            - String to know which fragment to fill
	 * @return
	 */
	/*
	private View addUpperElementsToView(View v, String identifier) {

		String text;
		ArrayList<String> spinnerOptions;
		LinearLayout linlay;

		if (identifier.equalsIgnoreCase("forecast")) {
			text = "Vali päev: ";
			spinnerOptions = replaceMonths(availableDates);
			linlay = (LinearLayout) v.findViewById(R.id.selectDayLayout);
		} else {
			text = "Vali asukoht: ";
			spinnerOptions = availableLocations;
			linlay = (LinearLayout) v.findViewById(R.id.selectLocation);
		}

		linlay.setGravity(Gravity.RIGHT);

		TextView sortBy = new TextView(getActivity());
		sortBy.setPadding(25, 0, 0, 0);
		sortBy.setText(text);
		sortBy.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Medium);
		linlay.addView(sortBy);
		linlay.addView(addSpinner(v, spinnerOptions, identifier));

		return v;
	}
	*/

	/**
	 * Method to add spinner to the upper part of the layout
	 * 
	 * @param v
	 *            - View object to connect objects with layout fields
	 * @param spinnerOptions
	 *            - fields that will be available in the spinner object
	 * @param identifier
	 *            - String to know which fragment to fill the data for
	 * @return
	 */
	/*
	@SuppressWarnings("deprecation")
	private Spinner addSpinner(final View v, ArrayList<String> spinnerOptions,
			final String identifier) {
		Spinner sortSpinner = new Spinner(getActivity());
		sortSpinner.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		sortSpinner.setPadding(25, 0, 0, 0);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				spinnerOptions);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortSpinner.setAdapter(arrayAdapter);
		SharedPreferences preferences = getActivity().getSharedPreferences(
				PREFS_NAME, Context.MODE_PRIVATE);
		if (identifier.equalsIgnoreCase("current")) {
			sortSpinner.setSelection(preferences.getInt("savedLocation", 0));
		}
		sortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (identifier.equalsIgnoreCase("forecast")) {
					fillForecastFragment(v, availableDates.get(arg2));
				} else if (identifier.equalsIgnoreCase("current")) {
					SharedPreferences.Editor pref_editor = getActivity()
							.getSharedPreferences(PREFS_NAME,
									Context.MODE_PRIVATE).edit();
					pref_editor.putInt("savedLocation", arg2);
					pref_editor.commit();
					fillObservationFragment(v, availableLocations.get(arg2));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		return sortSpinner;
	}
	*/

	/**
	 * Method to fill the Observation fragment
	 * 
	 * @param v
	 *            - View object to connect objects with layout fields
	 * @param name
	 *            - Name of the location
	 * @return The View object
	 */
	/*
	@SuppressWarnings("deprecation")
	private View fillObservationFragment(View v, String name) {

		Observation observation = new Observation();

		// Get the day&night forecasts for the date from the DB
		try {
			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(getActivity());
			db.openDataBase();

			Cursor mycursor = db.getReadableDatabase().rawQuery(
					"SELECT * FROM 'observations' WHERE name == '" + name
							+ "';", null);
			mycursor.moveToFirst();
			observation.setName(mycursor.getString(1));
			observation.setPhenomenon(mycursor.getString(2));
			observation.setAirPressure(mycursor.getString(3));
			observation.setAirTemp(mycursor.getString(4));
			observation.setHumidity(mycursor.getString(5));
			observation.setWindDir(mycursor.getString(6));
			observation.setWindSpd(mycursor.getString(7));
			observation.setWindSpdMax(mycursor.getString(8));
			mycursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Fill the layouts
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		int imgWidth = display.getWidth() / 4;

		// Initializations of the fields
		TextView nameView = (TextView) v.findViewById(R.id.currentName);
		TextView phenView = (TextView) v.findViewById(R.id.currentPhen);
		TextView airPressureView = (TextView) v
				.findViewById(R.id.currentAirPressure);
		TextView airTempView = (TextView) v.findViewById(R.id.currentTemp);
		TextView humidityView = (TextView) v.findViewById(R.id.currentHumidity);
		TextView windDirView = (TextView) v.findViewById(R.id.currentWindDir);
		TextView windSpdView = (TextView) v.findViewById(R.id.currentWindSpd);
		TextView windSpdMaxView = (TextView) v
				.findViewById(R.id.currentWindSpdMax);

		// Place values if they exist, otherwise hide the wrapper
		nameView.setText(observation.getName());

		LinearLayout tempWordsWrapper = (LinearLayout) v
				.findViewById(R.id.currentAirTempWordsWrapper);
		if (!observation.getAirTemp().equalsIgnoreCase("")) {
			airTempView.setText(observation.getAirTemp() + " °C");
			TextView currentAirTempWords = (TextView) v
					.findViewById(R.id.currentAirTempWords);
			tempWordsWrapper.setVisibility(View.VISIBLE);
			airTempView.setVisibility(View.VISIBLE);
			currentAirTempWords.setText(TranslateTemperature
					.replaceTemperature(observation.getAirTemp()));

		} else {
			tempWordsWrapper.setVisibility(View.GONE);
			airTempView.setVisibility(View.GONE);
		}

		LinearLayout phenWrapper = (LinearLayout) v
				.findViewById(R.id.currentPhenWrapper);
		ImageView icon = (ImageView) v.findViewById(R.id.currentIcon);
		if (!observation.getPhenomenon().equalsIgnoreCase("")) {
			phenView.setText(TranslatePhenomenon.replacePhenomenon(observation
					.getPhenomenon()));
			phenWrapper.setVisibility(View.VISIBLE);
			icon.setVisibility(View.VISIBLE);
		} else {
			phenWrapper.setVisibility(View.GONE);
			icon.setVisibility(View.GONE);
		}
		LinearLayout pressureWrapper = (LinearLayout) v
				.findViewById(R.id.currentAirPressureWrapper);
		if (!observation.getAirPressure().equalsIgnoreCase("")) {
			airPressureView.setText(observation.getAirPressure() + " hPa");
			pressureWrapper.setVisibility(View.VISIBLE);
		} else {
			pressureWrapper.setVisibility(View.GONE);
		}
		LinearLayout humWrapper = (LinearLayout) v
				.findViewById(R.id.currentHumWrapper);
		if (!observation.getHumidity().equalsIgnoreCase("")) {
			humidityView.setText(observation.getHumidity() + " %");
			humWrapper.setVisibility(View.VISIBLE);
		} else {
			humWrapper.setVisibility(View.GONE);
		}
		LinearLayout windDirWrapper = (LinearLayout) v
				.findViewById(R.id.currentWindDirWrapper);
		if (!observation.getWindDir().equalsIgnoreCase("")) {
			windDirView.setText(getWindDirection(observation.getWindDir()));
			windDirWrapper.setVisibility(View.VISIBLE);
		} else {
			windDirWrapper.setVisibility(View.GONE);
		}
		LinearLayout windSpdWrapper = (LinearLayout) v
				.findViewById(R.id.currentWindSpdWrapper);
		if (!observation.getWindSpd().equalsIgnoreCase("")) {
			windSpdView.setText(observation.getWindSpd() + " m/s");
			windSpdWrapper.setVisibility(View.VISIBLE);
		} else {
			windSpdWrapper.setVisibility(View.GONE);
		}
		LinearLayout windSpdMaxWrapper = (LinearLayout) v
				.findViewById(R.id.currentWindSpdMaxWrapper);
		if (!observation.getWindSpdMax().equalsIgnoreCase("")) {
			windSpdMaxView.setText(observation.getWindSpdMax() + " m/s");
			windSpdMaxWrapper.setVisibility(View.VISIBLE);
		} else {
			windSpdMaxWrapper.setVisibility(View.GONE);
		}

		// Set the icon in case a phenomenon field is present
		icon.setLayoutParams(new LinearLayout.LayoutParams(imgWidth, imgWidth));
		icon.setBackgroundResource(getBackground(observation.getPhenomenon(),
				"day"));

		return v;
	}
	*/

	/**
	 * Method to fill the Forecast fragment
	 * 
	 * @param v
	 *            - View object to connect objects with layout fields
	 * @param name
	 *            - Name of the location
	 * @return The View object
	 */
	/*
	@SuppressWarnings("deprecation")
	private View fillForecastFragment(View v, String date) {

		Forecast nightForecast = new Forecast();
		Forecast dayForecast = new Forecast();

		// Get the day&night forecasts for the date from the DB
		try {
			DBAdapter db = null;
			db = DBAdapter.getDBAdapterInstance(getActivity());
			db.openDataBase();

			Cursor mycursor = db.getReadableDatabase().rawQuery(
					"SELECT * FROM 'forecasts' WHERE date == '" + date + "';",
					null);

			mycursor.moveToFirst();
			nightForecast.setId(mycursor.getString(0));
			nightForecast.setText(mycursor.getString(1));
			nightForecast.setTempmax(mycursor.getString(2));
			nightForecast.setTempmin(mycursor.getString(3));
			nightForecast.setPhenomenon(mycursor.getString(4));
			nightForecast.setDate(mycursor.getString(5));
			nightForecast.setTimeOfDay(mycursor.getString(6));
			nightForecast.setWindmin(mycursor.getString(7));
			nightForecast.setWindmax(mycursor.getString(8));
			nightForecast.setHasMoreData(mycursor.getString(9));
			nightForecast.setPlaces(mycursor.getString(10));

			mycursor.moveToNext();
			dayForecast.setId(mycursor.getString(0));
			dayForecast.setText(mycursor.getString(1));
			dayForecast.setTempmax(mycursor.getString(2));
			dayForecast.setTempmin(mycursor.getString(3));
			dayForecast.setPhenomenon(mycursor.getString(4));
			dayForecast.setDate(mycursor.getString(5));
			dayForecast.setTimeOfDay(mycursor.getString(6));
			dayForecast.setWindmin(mycursor.getString(7));
			dayForecast.setWindmax(mycursor.getString(8));
			dayForecast.setHasMoreData(mycursor.getString(9));
			dayForecast.setPlaces(mycursor.getString(10));

			mycursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		int imgWidth = display.getWidth() / 4; // deprecated

		// Initialize variables
		TextView night_header = (TextView) v.findViewById(R.id.nightHeaderText);
		TextView night_temp = (TextView) v
				.findViewById(R.id.nightForecastTempVal);
		TextView night_text = (TextView) v
				.findViewById(R.id.nightForecastDescVal);
		TextView day_header = (TextView) v.findViewById(R.id.dayHeaderText);
		TextView day_temp = (TextView) v.findViewById(R.id.dayForecastTempVal);
		TextView day_text = (TextView) v.findViewById(R.id.dayForecastDescVal);

		// Set values
		night_text.setText(nightForecast.getText());
		night_temp.setText(nightForecast.getTempmin().trim() + ".."
				+ nightForecast.getTempmax().trim() + " °C");
		night_header.setText(TranslatePhenomenon
				.replacePhenomenon(nightForecast.getPhenomenon()));
		day_text.setText(dayForecast.getText());
		day_temp.setText(dayForecast.getTempmin().trim() + ".."
				+ dayForecast.getTempmax().trim() + " °C");
		day_header.setText(TranslatePhenomenon.replacePhenomenon(dayForecast
				.getPhenomenon()));

		// Set values and fields in case the object has more data
		if (nightForecast.getHasMoreData().equalsIgnoreCase("true")) {

			// Initialize and set visible
			TextView night_wind_key = (TextView) v
					.findViewById(R.id.nightForecastWindKey);
			TextView night_wind_val = (TextView) v
					.findViewById(R.id.nightForecastWindVal);
			TextView day_wind_key = (TextView) v
					.findViewById(R.id.dayForecastWindKey);
			TextView day_wind_val = (TextView) v
					.findViewById(R.id.dayForecastWindVal);
			night_wind_key.setVisibility(View.VISIBLE);
			night_wind_val.setVisibility(View.VISIBLE);
			day_wind_key.setVisibility(View.VISIBLE);
			day_wind_val.setVisibility(View.VISIBLE);

			// Set values
			night_wind_val.setText(nightForecast.getWindmin().trim() + ".."
					+ nightForecast.getWindmax().trim() + "m/s");
			day_wind_val.setText(dayForecast.getWindmin().trim() + ".."
					+ dayForecast.getWindmax().trim() + "m/s");

			// Place specific weather
			fillExtraWeatherData("night", nightForecast.getPlaces(), v);
			fillExtraWeatherData("day", dayForecast.getPlaces(), v);

		} else {
			TextView night_wind_key = (TextView) v
					.findViewById(R.id.nightForecastWindKey);
			TextView night_wind_val = (TextView) v
					.findViewById(R.id.nightForecastWindVal);
			TextView day_wind_key = (TextView) v
					.findViewById(R.id.dayForecastWindKey);
			TextView day_wind_val = (TextView) v
					.findViewById(R.id.dayForecastWindVal);
			LinearLayout locationNight = (LinearLayout) v
					.findViewById(R.id.locationWeatherNight);
			LinearLayout locationDay = (LinearLayout) v
					.findViewById(R.id.locationWeatherDay);
			night_wind_key.setVisibility(View.GONE);
			night_wind_val.setVisibility(View.GONE);
			day_wind_key.setVisibility(View.GONE);
			day_wind_val.setVisibility(View.GONE);
			locationDay.setVisibility(View.GONE);
			locationNight.setVisibility(View.GONE);

		}

		// Set icons
		ImageView night_icon = (ImageView) v.findViewById(R.id.nightIcon);
		night_icon.setLayoutParams(new LinearLayout.LayoutParams(imgWidth,
				imgWidth));
		night_icon.setBackgroundResource(getBackground(
				nightForecast.getPhenomenon(), "night"));
		ImageView day_icon = (ImageView) v.findViewById(R.id.dayIcon);
		day_icon.setLayoutParams(new LinearLayout.LayoutParams(imgWidth,
				imgWidth));
		day_icon.setBackgroundResource(getBackground(
				dayForecast.getPhenomenon(), "day"));

		return v;
	}
	*/

	/**
	 * Method to fill the lower part of forecast
	 * 
	 * @param daytime
	 *            - identifier to know, which forecast to fill
	 * @param placesAll
	 *            - all places to set in the spinner selection
	 * @param v
	 *            - View object to connect objects with layout fields
	 */
	/*
	private void fillExtraWeatherData(String daytime, String placesAll, View v) {

		ArrayList<String> placeNames = new ArrayList<String>();
		final ArrayList<String> placeTemps = new ArrayList<String>();
		final ArrayList<String> placePhens = new ArrayList<String>();
		ArrayList<String> places = new ArrayList<String>();

		for (String place : placesAll.split("\\,")) {
			places.add(place);
		}
		for (String place : places) {
			String[] place_split = place.split("\\.");
			placeNames.add(place_split[0].replace("[", "").trim());
			placePhens.add(place_split[1]);
			placeTemps.add(place_split[2].replace("]", "").trim());
		}

		LinearLayout location;
		final TextView placePhen;
		final TextView placeTemp;
		Spinner locationSpinner;

		if (daytime.equalsIgnoreCase("night")) {

			location = (LinearLayout) v.findViewById(R.id.locationWeatherNight);
			placePhen = (TextView) v.findViewById(R.id.placePhenNight);
			placeTemp = (TextView) v.findViewById(R.id.placeTempNight);
			locationSpinner = (Spinner) v
					.findViewById(R.id.locationSpinnerNight);

		} else {
			location = (LinearLayout) v.findViewById(R.id.locationWeatherDay);
			placePhen = (TextView) v.findViewById(R.id.placePhenDay);
			placeTemp = (TextView) v.findViewById(R.id.placeTempDay);
			locationSpinner = (Spinner) v.findViewById(R.id.locationSpinnerDay);
		}

		location.setVisibility(View.VISIBLE);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				placeNames);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		locationSpinner.setAdapter(arrayAdapter);
		locationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				changeLocationData(placePhen, placePhens.get(arg2), placeTemp,
						placeTemps.get(arg2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
	}
	*/

}
