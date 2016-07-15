package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.MainActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.utils.AccountManager;
import com.lpoezy.nexpa.utils.L;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashScreen extends Fragment {


    public static final java.lang.String TAG = SplashScreen.class.getSimpleName();

    public SplashScreen() {
        // Required empty public constructor
    }


    public static SplashScreen newInstance() {
        SplashScreen fragment = new SplashScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {




                AccountManager am = new AccountManager(getActivity());

                try{

                    boolean isUserLoggedin = am.isLoggedin();

                    if (isUserLoggedin) {

                        Intent act = new Intent(getActivity(), HomeTabActivity.class);
                        startActivity(act);
                        getActivity().finish();

                    } else {

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Intent act = new Intent(getActivity(), MainActivity.class);
                        startActivity(act);
                        getActivity().finish();

                    }

                }catch (NullPointerException e){

                }



            }
        }).start();
    }
}
