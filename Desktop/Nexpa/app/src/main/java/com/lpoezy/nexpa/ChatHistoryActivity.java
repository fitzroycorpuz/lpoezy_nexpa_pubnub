package com.lpoezy.nexpa;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lpoezy.nexpa.screens.ChatHistoryScreen;
import com.lpoezy.nexpa.screens.SigninScreen;

public class ChatHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        Fragment chatHistoryScreen = ChatHistoryScreen.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, chatHistoryScreen, ChatHistoryScreen.TAG).commit();
    }
}
