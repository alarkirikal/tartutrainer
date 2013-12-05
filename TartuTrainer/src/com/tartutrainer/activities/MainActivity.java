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
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	PageAdapter pageAdapter;
	ActionBar actionBar;
	boolean sortedBy = true;
	
	public static FragmentManager fm;

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
		SharedPreferences p = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean isFirstRun = p.getBoolean("FIRSTRUN", true);
		if (isFirstRun) {
			SharedPreferences.Editor editor = p.edit();
			editor.putBoolean("FIRSTRUN", false);
			SharedPreferencesDefaultValues.init(this);
			editor.commit();
		}
	}

	private void buildMainFragment() {

		// Get the fragments and set the data adapters
		List<Fragment> fragments = getFragments();
		// pageAdapter = new PageAdapter(getSupportFragmentManager(),
		// fragments);
		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager_main);
		pager.setAdapter(pageAdapter);
		
		fm = getFragmentManager();

		// Set up ActionBar
		actionBar = getActionBar();
		actionBar.show();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);

		Tab tab = actionBar
				.newTab()
				.setText("Programs")
				.setTabListener(
						new TabListener<AllProgramsFragment>(this, "Programs",
								AllProgramsFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText("Exercises")
				.setTabListener(
						new TabListener<AllExercisesFragment>(this,
								"Exercises", AllExercisesFragment.class));
		actionBar.addTab(tab);

		/*
		 * ActionBar.TabListener tabListener = new ActionBar.TabListener() {
		 * 
		 * @Override public void onTabReselected(Tab tab, FragmentTransaction
		 * ft) { }
		 * 
		 * @Override public void onTabSelected(Tab tab, FragmentTransaction ft)
		 * { pager.setCurrentItem(tab.getPosition()); }
		 * 
		 * @Override public void onTabUnselected(Tab tab, FragmentTransaction
		 * ft) { }
		 * 
		 * };
		 */

		/*
		 * pager.setOnPageChangeListener(new
		 * ViewPager.SimpleOnPageChangeListener() {
		 * 
		 * @Override public void onPageSelected(int position) {
		 * getActionBar().setSelectedNavigationItem(position); } });
		 */

		// Navigation tabs
		// actionBar.addTab(actionBar.newTab().setText("Programs")
		// .setTabListener(tabListener));
		// actionBar.addTab(actionBar.newTab().setText("Exercises")
		// .setTabListener(tabListener));

		// Listener for which tab to show
		try {
			if (getIntent().getExtras().getString("select_tab")
					.equalsIgnoreCase("exercise")) {
				actionBar.setSelectedNavigationItem(1);
			}
		} catch (Exception e) {
			Log.d("[MainActivity]", "Initial launch");
		}
	}

	// Add all fragments to the ViewPager object
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(Fragment.instantiate(this,
				AllProgramsFragment.class.getName()));
		fList.add(Fragment.instantiate(this,
				AllExercisesFragment.class.getName()));
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		if (actionBar.getSelectedNavigationIndex() == 0) {
			menuInflater.inflate(R.menu.allprograms_menu, menu);
		} else {
			menuInflater.inflate(R.menu.allexercises_menu, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		MenuInflater menuInflater = getMenuInflater();
		if (actionBar.getSelectedNavigationIndex() == 0) {
			menuInflater.inflate(R.menu.allprograms_menu, menu);
		} else {
			menuInflater.inflate(R.menu.allexercises_menu, menu);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		AllProgramsFragment allProgramsFragment = (AllProgramsFragment) getFragmentManager()
				.findFragmentByTag("Programs");
		AllExercisesFragment allExercisesFragment = (AllExercisesFragment) getFragmentManager()
				.findFragmentByTag("Exercises");
		switch (item.getItemId()) {
		
		case R.id.action_newprogram:
			Intent intent = new Intent(this, ClientsActivity.class);
			startActivity(intent);
			return true;
			
		case R.id.action_filterprograms:
			allProgramsFragment.onSort(sortedBy);
			sortedBy = !sortedBy;
			return true;
		
		case R.id.action_addexercise:
			Intent intent_addexc = new Intent(this,
					EditExerciseActivity.class);
			intent_addexc.putExtra("action", "New Exercise");
			startActivity(intent_addexc);
			return true;
		
		case R.id.action_filterexercises:
			allExercisesFragment.filterExerciseDialog();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static class TabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			mActivity.invalidateOptionsMenu();
			fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
		}
	}

}
