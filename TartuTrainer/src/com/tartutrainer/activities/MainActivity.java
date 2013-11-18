package com.tartutrainer.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.adapters.PageAdapter;
import com.tartutrainer.database.DBAdapter;
import com.tartutrainer.fragments.AllExercisesFragment;
import com.tartutrainer.fragments.AllProgramsFragment;
import com.tartutrainer.helpers.SharedPreferencesDefaultValues;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;

public class MainActivity extends FragmentActivity {

	PageAdapter pageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set initial values to SharedPrefs after installation
		setInitialPreferenceValues();
		
		// Check database files
		checkDB();
		
		// Build fragments
		buildMainFragment();
	}
	
	private void setInitialPreferenceValues() {
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		boolean isFirstRun = p.getBoolean("FIRSTRUN", true);
		if (isFirstRun)
		{
		    SharedPreferences.Editor editor = p.edit();
		    editor.putBoolean("FIRSTRUN", false);
		    SharedPreferencesDefaultValues.init(this);
		    editor.commit();
		}
	}

	private void buildMainFragment() {
		
		// Get the fragments and set the data adapters
		List<Fragment> fragments = getFragments();
		pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager_main);
		pager.setAdapter(pageAdapter);

		// Set up ActionBar
		ActionBar actionBar = getActionBar();
		actionBar.show();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}

		};

		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		// Navigation tabs
		actionBar.addTab(actionBar.newTab().setText("Programs")
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Exercises")
				.setTabListener(tabListener));
		
		// Listener for which tab to show
		try {
			if (getIntent().getExtras().getString("select_tab").equalsIgnoreCase("exercise")) {
				actionBar.setSelectedNavigationItem(1);
			}
		} catch (Exception e) {
			Log.d("[MainActivity]", "Initial launch");
		}
	}

	// Add all fragments to the ViewPager object
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(Fragment.instantiate(this, AllProgramsFragment.class.getName()));
		fList.add(Fragment.instantiate(this, AllExercisesFragment.class.getName()));
		return fList;
	}
	
	// Check if database exists, if not - create one
	private void checkDB() {
		DBAdapter db = null;
		db = DBAdapter.getDBAdapterInstance(this);
		try {
			db.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		db.openDataBase();
		db.close();
	}

}
