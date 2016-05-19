package com.lpoezy.nexpa.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lpoezy.nexpa.R;


public class ForgotPasswordScreen extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password_screen, container, false);
    }

}
