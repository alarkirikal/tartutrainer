package com.tartutrainer.helpers;

import com.tartutrainer.R;
import com.tartutrainer.fragments.AllExercisesFragment;
import com.tartutrainer.fragments.AllProgramsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

		// Get the specified fragments to display
		if (message.equalsIgnoreCase("allprograms")) {
			
			AllProgramsFragment allPrograms = new AllProgramsFragment(inflater, container);
			return allPrograms.displayFragment();
		
		} else if (message.equalsIgnoreCase("allexercises")) {
			
			AllExercisesFragment allExercises = new AllExercisesFragment(inflater, container);
			return allExercises.displayFragment();
			
		} else {
			// Must not reach this.
			View v = inflater.inflate(R.layout.fragment_allexercises, container, false);
			return v;
		}
	}

}