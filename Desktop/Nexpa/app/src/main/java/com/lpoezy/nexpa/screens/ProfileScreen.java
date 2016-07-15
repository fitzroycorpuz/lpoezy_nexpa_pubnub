package com.lpoezy.nexpa.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpoezy.nexpa.EditProfileActivity;
import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.SettingsActivity;
import com.lpoezy.nexpa.parallaxrecyclerview.ParallaxRecyclerAdapter;

import com.lpoezy.nexpa.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileScreen extends Fragment {

    private Toolbar mToolbar;
    private RecyclerView mRvBroadcasts;
    private ArrayList<MyBroadcast> mBroadcasts;
    private ParallaxRecyclerAdapter<MyBroadcast> mAdapter;

    public ProfileScreen() {
        // Required empty public constructor
    }


    public static ProfileScreen newInstance() {
        ProfileScreen fragment = new ProfileScreen();
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
        View v = (View)inflater.inflate(R.layout.fragment_profile_screen, container, false);
        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Profile");

        mRvBroadcasts = (RecyclerView) v.findViewById(R.id.rv_my_broadcasts);
        mBroadcasts = new ArrayList<MyBroadcast>();


        mAdapter = new ParallaxRecyclerAdapter<MyBroadcast>(mBroadcasts) {


            @Override
            public void onBindViewHolderImpl(android.support.v7.widget.RecyclerView.ViewHolder viewHolder,
                                             ParallaxRecyclerAdapter<MyBroadcast> adapter, int position) {





            }

            @Override
            public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup parent, ParallaxRecyclerAdapter<MyBroadcast> adapter, int i) {
                View itemView = getActivity().getLayoutInflater().inflate(R.layout.custom_broadcast_view, parent, false);
                return new ViewHolder(itemView);
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<MyBroadcast> adapter) {

                return 10;
            }


        };



        mRvBroadcasts.setLayoutManager(new LinearLayoutManager(getActivity()));
        View header = getActivity().getLayoutInflater().inflate(R.layout.profile_header, mRvBroadcasts, false);
        mAdapter.setParallaxHeader(header, mRvBroadcasts);
        mAdapter.setData(mBroadcasts);
        mRvBroadcasts.setAdapter(mAdapter);


        ((ImageView)header.findViewById(R.id.btn_edit_profile)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent act = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(act);

            }
        });

        ((ImageView)mToolbar.findViewById(R.id.btn_settings)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent act = new Intent(getActivity(), SettingsActivity.class);
                startActivity(act);

            }
        });

        return v;
    }

    private class MyBroadcast{}

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);


        }
    }

}
