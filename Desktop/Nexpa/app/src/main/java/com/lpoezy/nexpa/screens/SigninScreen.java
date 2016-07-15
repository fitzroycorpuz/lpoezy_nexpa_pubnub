package com.lpoezy.nexpa.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.utils.AccountManager;
import com.lpoezy.nexpa.utils.L;
import com.lpoezy.nexpa.utils.Utils;

import org.w3c.dom.Text;


public class SigninScreen extends Fragment {


    public static final String TAG = SigninScreen.class.getSimpleName();

    public SigninScreen() {
        // Required empty public constructor
    }


    public static SigninScreen newInstance() {
        SigninScreen fragment = new SigninScreen();
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
        View v = (View)inflater.inflate(R.layout.fragment_signin_screen, container, false);


        //show user the home screen, when btn login is clicked
        ((Button)v.findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AccountManager am = new AccountManager(getActivity());
                am.setLoggedin(true);

                Intent act = new Intent(getActivity(), HomeTabActivity.class);
                startActivity(act);
                getActivity().finish();
            }
        });

        //show user the signup screen, when btn signup is clicked
        ((TextView)v.findViewById(R.id.tv_signup)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                SignupScreen signupScreen = SignupScreen.newInstance();
                trans.replace(R.id.fragment_holder, signupScreen, SignupScreen.TAG);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        //show user the forgot password screen, when btn forgot password is clicked
        ((TextView)v.findViewById(R.id.tv_forgot_password)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                ForgotPasswordScreen forgotPasswordScreen = ForgotPasswordScreen.newInstance();
                trans.replace(R.id.fragment_holder, forgotPasswordScreen, SignupScreen.TAG);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        return v;
    }

}
