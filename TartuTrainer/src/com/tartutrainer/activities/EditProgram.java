package com.tartutrainer.activities;

import java.util.ArrayList;
import java.util.List;

import com.tartutrainer.R;
import com.tartutrainer.adapters.PageAdapter;
import com.tartutrainer.helpers.FragmentBuilder;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class EditProgram extends FragmentActivity {

	PageAdapter pageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprogram);

		List<Fragment> fragments = getFragments();
		pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
		final ViewPager pager = (ViewPager) findViewById(R.id.viewpager_second);
		pager.setAdapter(pageAdapter);

		// ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				pager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
		});
		
		actionBar.addTab(actionBar.newTab().setText("Program").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Exercise").setTabListener(tabListener));
	}

	private List<Fragment> getFragments() {
		List<Fragment> fList = new ArrayList<Fragment>();
		fList.add(FragmentBuilder.newInstance("Program"));
		fList.add(FragmentBuilder.newInstance("Exercise"));
		return fList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
