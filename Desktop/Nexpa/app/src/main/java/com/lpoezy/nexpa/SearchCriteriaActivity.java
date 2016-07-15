package com.lpoezy.nexpa;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lpoezy.nexpa.screens.ChatHistoryScreen;
import com.lpoezy.nexpa.screens.SearchCriteriaScreen;

public class SearchCriteriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);

        Fragment searchCriteriaScreen = SearchCriteriaScreen.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, searchCriteriaScreen, SearchCriteriaScreen.TAG).commit();
    }
}
