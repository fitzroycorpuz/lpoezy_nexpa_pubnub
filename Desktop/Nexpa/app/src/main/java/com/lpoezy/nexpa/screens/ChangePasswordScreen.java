package com.lpoezy.nexpa.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.models.OnUpdateScreenListener;


public class ChangePasswordScreen extends Fragment implements OnUpdateScreenListener {

    public static final String TAG = ChangePasswordScreen.class.getSimpleName();
    private Toolbar mToolbar;

    public ChangePasswordScreen() {

    }


    public static ChangePasswordScreen newInstance() {
        ChangePasswordScreen fragment = new ChangePasswordScreen();
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
        View v = (View)inflater.inflate(R.layout.fragment_change_password_screen, container, false);

        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Change Password");

        ((ImageView)mToolbar.findViewById(R.id.ic_header_logo)).setVisibility(View.GONE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                SettingsScreen settingsScreen = SettingsScreen.newInstance();
                trans.replace(R.id.fragment_holder, settingsScreen, SettingsScreen.TAG);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return v;
    }

    @Override
    public void onUpdateScreen() {

    }
}
