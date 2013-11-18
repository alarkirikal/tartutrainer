package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.adapters.PageAdapter;
import com.tartutrainer.fragments.ProgramAddExercisesFragment;
import com.tartutrainer.fragments.ProgramExercisesFragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class ProgramActivity extends FragmentActivity {

	PageAdapter pageAdapter;

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
		pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager_program);
		pager.setAdapter(pageAdapter);

		// Set up ActionBar
		ActionBar actionBar = getActionBar();
		actionBar.show();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				if (tab.getPosition() == 0) {
					try {
						fragments.get(0).onResume();
					} catch (RuntimeException r) {
						
					}
				}
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		};

		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					try {
						fragments.get(0).onResume();
					} catch (RuntimeException r) {
						
					}
				}
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		// Navigation tabs
		actionBar.addTab(actionBar.newTab().setText(getIntent().getExtras().getString("pgr_name"))
				.setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Add Exercises")
				.setTabListener(tabListener));
	}
	
	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(Fragment.instantiate(this, ProgramExercisesFragment.class.getName()));
		fList.add(Fragment.instantiate(this, ProgramAddExercisesFragment.class.getName()));
		return fList;
	}

}
