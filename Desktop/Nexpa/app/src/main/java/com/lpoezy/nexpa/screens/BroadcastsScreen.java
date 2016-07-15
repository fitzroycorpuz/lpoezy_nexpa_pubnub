package com.lpoezy.nexpa.screens;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lpoezy.nexpa.R;

import java.util.ArrayList;
import java.util.List;


public class BroadcastsScreen extends Fragment {

    private Toolbar mToolbar;

    public BroadcastsScreen() {
        // Required empty public constructor
    }


    public static BroadcastsScreen newInstance() {
        BroadcastsScreen fragment = new BroadcastsScreen();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
       View v = (View)inflater.inflate(R.layout.fragment_broadcasts_screen, container, false);
        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Your Audience");
        List<String> age = new ArrayList<String>();
        for(int i=0;i<=75;++i){
            age.add(Integer.toString(i));
        }

        MaterialSpinner spinnerMin = (MaterialSpinner) v.findViewById(R.id.spinner_min_age);
        spinnerMin.setItems(age);
        spinnerMin.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        MaterialSpinner spinnerMax = (MaterialSpinner) v.findViewById(R.id.spinner_max_age);
        spinnerMax.setItems(age);
        spinnerMax.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }

}
