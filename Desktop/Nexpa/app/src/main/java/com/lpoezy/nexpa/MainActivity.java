package com.lpoezy.nexpa;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.SidePropagation;
import android.view.MenuItem;

import com.lpoezy.nexpa.models.NexpaScreen;
import com.lpoezy.nexpa.screens.SigninScreen;
import com.lpoezy.nexpa.screens.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment signinScreen = SigninScreen.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, signinScreen, SigninScreen.TAG).commit();


    }


}
