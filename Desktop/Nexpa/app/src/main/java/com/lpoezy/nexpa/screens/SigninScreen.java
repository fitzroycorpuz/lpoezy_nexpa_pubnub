package com.lpoezy.nexpa.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.dialogs.TryAgainDialog;
import com.lpoezy.nexpa.models.M_SignupScreen;


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

    private void showResultDialog(String msg) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        TryAgainDialog dialog = TryAgainDialog.newInstance(msg);
        //dialog.setCancelable(false);
        dialog.show(fm, TryAgainDialog.TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = (View)inflater.inflate(R.layout.fragment_signin_screen, container, false);

        final EditText etUname = (EditText)v.findViewById(R.id.et_username);
        final EditText etPassword = (EditText)v.findViewById(R.id.et_password);

        //show user the home screen, when btn login is clicked
        ((Button)v.findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String uname = etUname.getText().toString();
                String password = etPassword.getText().toString();

                M_SignupScreen signinScreen = new M_SignupScreen(getActivity());

                signinScreen.onSetRegistrationCompleteLister(new M_SignupScreen.OnRegistrationCompleteLister() {
                    @Override
                    public void OnRegistrationComplete(int result) {

                        String msg = "";
                        switch (result){
                            case M_SignupScreen.LOGIN_COMPLETE:

                                Intent act = new Intent(getActivity(), HomeTabActivity.class);
                                getActivity().startActivity(act);
                                getActivity().finish();
                                return;

                            case M_SignupScreen.UNAME_PASSWORD_INCORRECT:
                                msg = getActivity().getResources().getString(R.string.alert_invalid_uname_password);
                                break;

                            case M_SignupScreen.MISSING_FIELD:
                                msg = getActivity().getResources().getString(R.string.alert_missing_field);
                                break;


                        }

                        showResultDialog(msg);
                    }

                });

                signinScreen.login(uname, password);



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
