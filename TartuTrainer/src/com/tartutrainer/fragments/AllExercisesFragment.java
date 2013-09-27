package com.tartutrainer.fragments;

import com.tartutrainer.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllExercisesFragment {

	LayoutInflater inflater;
	ViewGroup container;

	public AllExercisesFragment(LayoutInflater inflater, ViewGroup container) {
		this.inflater = inflater;
		this.container = container;
	}

	public View displayFragment() {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_allexercises, container,
				false);
		return view;
	}

}
