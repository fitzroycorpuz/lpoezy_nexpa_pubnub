package com.lpoezy.nexpa.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lpoezy.nexpa.R;


public class EditProfileScreen extends Fragment {

    private Toolbar mToolbar;

    public EditProfileScreen() {
        // Required empty public constructor
    }


    public static EditProfileScreen newInstance() {
        EditProfileScreen fragment = new EditProfileScreen();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = (View)inflater.inflate(R.layout.fragment_edit_profile_screen, container, false);

        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Edit Profile");

        return v;
    }

}
