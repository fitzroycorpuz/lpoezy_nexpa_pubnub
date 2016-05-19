package com.lpoezy.nexpa;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lpoezy.nexpa.models.NexpaScreen;
import com.lpoezy.nexpa.screens.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment splashScreen = SplashScreen.newInstance();
        getFragmentManager().beginTransaction().add(R.id.fragment_holder, splashScreen, SplashScreen.TAG).commit();


    }

   
}
