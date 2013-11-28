package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.activities.MainActivity.TabListener;
import com.tartutrainer.adapters.PageAdapter;
import com.tartutrainer.fragments.AllExercisesFragment;
import com.tartutrainer.fragments.AllProgramsFragment;
import com.tartutrainer.fragments.ProgramAddExercisesFragment;
import com.tartutrainer.fragments.ProgramExercisesFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ProgramActivity extends FragmentActivity {

	PageAdapter pageAdapter;
	public ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_program);

		// Build fragments
		buildMainFragment();
	}

	private void buildMainFragment() {

		// Get the fragments and set the data adapters
		final List<Fragment> fragments = getFragments();
		//pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager_program);
		pager.setAdapter(pageAdapter);

		// Set up ActionBar
		actionBar = getActionBar();
		actionBar.show();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tab = actionBar
				.newTab()
				.setText(getIntent().getExtras().getString("pgr_name"))
				.setTabListener(
						new TabListener<ProgramExercisesFragment>(this,
								getIntent().getExtras().getString("pgr_name"),
								ProgramExercisesFragment.class));
		actionBar.addTab(tab);

		tab = actionBar
				.newTab()
				.setText("Exercises")
				.setTabListener(
						new TabListener<ProgramAddExercisesFragment>(this, "Exercises_Program",
								ProgramAddExercisesFragment.class));
		actionBar.addTab(tab);
		
		
	}
	
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(Fragment.instantiate(this, ProgramExercisesFragment.class.getName()));
		fList.add(Fragment.instantiate(this, ProgramAddExercisesFragment.class.getName()));
		return fList;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		if (actionBar.getSelectedNavigationIndex() == 0) {
			menuInflater.inflate(R.menu.exercisesprogram_menu, menu);
		} else {
			menuInflater.inflate(R.menu.exctopgr_menu, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		
		MenuInflater menuInflater = getMenuInflater();
		if (actionBar.getSelectedNavigationIndex() == 0) {
			menuInflater.inflate(R.menu.exercisesprogram_menu, menu);
		} else {
			menuInflater.inflate(R.menu.exctopgr_menu, menu);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ProgramExercisesFragment pgrexc = (ProgramExercisesFragment) getFragmentManager()
				.findFragmentByTag(getIntent().getExtras().getString("pgr_name"));
		ProgramAddExercisesFragment pgrexcs = (ProgramAddExercisesFragment) getFragmentManager()
				.findFragmentByTag("Exercises_Program");
		
		switch (item.getItemId())
		{
		case R.id.action_newheader:
			pgrexc.addHeader();
			return true;
		
		case R.id.action_saveprogram:
			pgrexc.saveTemplate();
			return true;
			
		case R.id.action_filterexercisespgr:
			pgrexcs.filterExercisesToAdd();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // Check if the fragment is already initialized
	    	mActivity.invalidateOptionsMenu();
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
