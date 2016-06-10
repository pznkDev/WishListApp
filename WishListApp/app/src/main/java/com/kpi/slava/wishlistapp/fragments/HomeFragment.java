package com.kpi.slava.wishlistapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpi.slava.wishlistapp.R;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private final int LAYOUT = R.layout.fragment_home;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }

}