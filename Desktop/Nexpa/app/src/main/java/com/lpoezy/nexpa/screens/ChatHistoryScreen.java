package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.models.OnUpdateScreenListener;
import com.lpoezy.nexpa.utils.DividerItemDecoration;


public class ChatHistoryScreen extends Fragment implements OnUpdateScreenListener {

    public static final String TAG = ChatHistoryScreen.class.getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView mRvChatHistory;
    private ChatHistoryAdapter adapter;

    public ChatHistoryScreen() {
        // Required empty public constructor
    }


    public static ChatHistoryScreen newInstance() {
        ChatHistoryScreen fragment = new ChatHistoryScreen();
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
        View v = (View) inflater.inflate(R.layout.fragment_chat_history_screen, container, false);

        mToolbar = (Toolbar) v.findViewById(R.id.app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView) mToolbar.findViewById(R.id.tab_title)).setText("Chat History");

        ((ImageView) mToolbar.findViewById(R.id.ic_header_logo)).setVisibility(View.GONE);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mRvChatHistory = (RecyclerView) v.findViewById(R.id.rv_chat_history);


        mRvChatHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvChatHistory.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        adapter = new ChatHistoryAdapter(getActivity());
        mRvChatHistory.setAdapter(adapter);

        return v;
    }

    @Override
    public void onUpdateScreen() {

    }

    private class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ViewHolder> {

        private final Context context;
        private LayoutInflater inflater;

        public ChatHistoryAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);

            this.context = context;

        }


        @Override
        public int getItemCount() {

            return 10;
        }

        @Override
        public void onBindViewHolder(final ViewHolder vh, int position) {


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View itemView = inflater.inflate(R.layout.custom_chat_history_view, parent, false);
            return new ViewHolder(itemView);
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            public TextView tvUsername;

            public ViewHolder(View view) {
                super(view);

                tvUsername = (TextView) view.findViewById(R.id.tv_username);

            }

        }
    }

}
