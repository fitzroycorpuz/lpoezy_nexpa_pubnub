package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.SearchCriteriaActivity;


public class NearMeScreen extends Fragment {

    private Toolbar mToolbar;
    private NearMeAdapter mAdapter;
    private GridView mGrid;

    public NearMeScreen() {
        // Required empty public constructor
    }


    public static NearMeScreen newInstance() {
        NearMeScreen fragment = new NearMeScreen();
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
        View v = inflater.inflate(R.layout.fragment_near_me_screen, container, false);

        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Near Me");

        mAdapter = new NearMeAdapter(getActivity());


//
        mGrid = (GridView) v.findViewById(R.id.grid);

        mGrid.setAdapter(mAdapter);
        mGrid.setBackgroundColor(Color.WHITE);
        mGrid.setVerticalSpacing(5);
        mGrid.setHorizontalSpacing(5);

        ((ImageView)mToolbar.findViewById(R.id.btn_refine_search)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getActivity(), SearchCriteriaActivity.class);
                getActivity().startActivity(activity);
            }
        });

        return v;
    }

    private class NearMeAdapter extends BaseAdapter{

        private final Context context;

        public NearMeAdapter(Context c) {
            this.context = c;
            //this.Imageid = Imageid;


        }
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View grid;
            grid = new View(this.context);

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                grid = inflater.inflate(R.layout.custom_nearme_view, null);


            } else {
                grid = (View) convertView;
            }

            return grid;
        }
    }

}
