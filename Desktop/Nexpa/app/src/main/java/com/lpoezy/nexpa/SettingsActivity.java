package com.lpoezy.nexpa;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lpoezy.nexpa.screens.SettingsScreen;
import com.lpoezy.nexpa.screens.SigninScreen;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Fragment seetingsScreen = SettingsScreen.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, seetingsScreen, SettingsScreen.TAG).commit();
    }
}
