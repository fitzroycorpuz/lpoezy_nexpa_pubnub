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

import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.models.OnUpdateScreenListener;


public class ForgotPasswordScreen extends Fragment implements OnUpdateScreenListener {


    private Toolbar mToolbar;

    public ForgotPasswordScreen() {
        // Required empty public constructor
    }


    public static ForgotPasswordScreen newInstance() {
        ForgotPasswordScreen fragment = new ForgotPasswordScreen();
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
        View v = (View)inflater.inflate(R.layout.fragment_forgot_password_screen, container, false);

        mToolbar = (android.support.v7.widget.Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        ((ImageView)mToolbar.findViewById(R.id.ic_header_logo)).setVisibility(View.GONE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                SigninScreen signinScreen = SigninScreen.newInstance();
                trans.replace(R.id.fragment_holder, signinScreen, SigninScreen.TAG);
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
