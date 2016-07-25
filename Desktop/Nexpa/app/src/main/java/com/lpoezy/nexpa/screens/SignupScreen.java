package com.lpoezy.nexpa.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.dialogs.LogoutConfirmationDialog;
import com.lpoezy.nexpa.dialogs.TryAgainDialog;
import com.lpoezy.nexpa.models.M_SignupScreen;
import com.lpoezy.nexpa.utils.L;

public class SignupScreen extends Fragment {


    public static final String TAG = SignupScreen.class.getSimpleName();;
    private android.support.v7.widget.Toolbar mToolbar;

    public SignupScreen() {
        // Required empty public constructor
    }


    public static SignupScreen newInstance() {
        SignupScreen fragment = new SignupScreen();
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
        View v = (View)inflater.inflate(R.layout.fragment_signup_screen, container, false);

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

        //get views
        final EditText etEmail = (EditText) v.findViewById(R.id.et_email);
        final EditText etUsername = (EditText) v.findViewById(R.id.et_username);
        final EditText etPassword = (EditText) v.findViewById(R.id.et_password);
        final Button btnRegister = (Button) v.findViewById(R.id.btn_register);


        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                L.debug("btnRegister was clicked!");
                M_SignupScreen am = new M_SignupScreen(getActivity());


                String email, username, password;

                email = etEmail.getText().toString();
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                am.onSetRegistrationCompleteLister(new M_SignupScreen.OnRegistrationCompleteLister()
                {
                    @Override
                    public void OnRegistrationComplete(int result) {

                        String msg = "";

                        switch (result){

                            case M_SignupScreen.REGISTRATION_COMPLETE:

                                Intent act = new Intent(getActivity(), HomeTabActivity.class);
                                getActivity().startActivity(act);
                                getActivity().finish();

                                return;

                            case M_SignupScreen.EMAIL_NOT_VALID:
                                msg = getActivity().getResources().getString(R.string.alert_invalid_email);

                                break;

                            case M_SignupScreen.UNAME_ALREADY_EXISTS:
                                msg = getActivity().getResources().getString(R.string.alert_uname_already_exists);
                                break;

                            case M_SignupScreen.PASSWORD_NOT_VALID:
                                msg = getActivity().getResources().getString(R.string.alert_invalid_password);
                                break;

                            case M_SignupScreen.EMAIL_ALREADY_EXISTS:
                                msg = getActivity().getResources().getString(R.string.alert_email_already_exists);
                                break;

                            case M_SignupScreen.MISSING_FIELD:
                                msg = getActivity().getResources().getString(R.string.alert_missing_field);
                                break;

                        }

                        showResultDialog(msg);
                    }

                });

                am.register(email, username, password);
            }


        });




//        if(database!=null){
//            // retrieve the document from the database
//            Document retrievedDocument = database.getDocument(documentId);
//            // display the retrieved document
//            L.debug("retrievedDocument=" + String.valueOf(retrievedDocument.getProperties()));
//        }

        return v;
    }

    private void showResultDialog(String msg) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
        TryAgainDialog dialog = TryAgainDialog.newInstance(msg);
        //dialog.setCancelable(false);
        dialog.show(fm, TryAgainDialog.TAG);
    }


}
