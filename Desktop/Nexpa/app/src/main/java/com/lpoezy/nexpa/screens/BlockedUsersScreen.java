package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.lpoezy.nexpa.dialogs.LogoutConfirmationDialog;
import com.lpoezy.nexpa.utils.DividerItemDecoration;


public class BlockedUsersScreen extends Fragment {

    public static final String TAG = BlockedUsersScreen.class.getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView mRvBlockedUsers;
    private BlockedUsersAdapter adapter;

    public BlockedUsersScreen() {
        // Required empty public constructor
    }


    public static BlockedUsersScreen newInstance() {
        BlockedUsersScreen fragment = new BlockedUsersScreen();
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

        View v = (View)inflater.inflate(R.layout.fragment_blocked_users_screen, container, false);

        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Blocked Users");

        ((ImageView)mToolbar.findViewById(R.id.ic_header_logo)).setVisibility(View.GONE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                SettingsScreen settingsScreen = SettingsScreen.newInstance();
                trans.replace(R.id.fragment_holder, settingsScreen, SettingsScreen.TAG);
                trans.addToBackStack(null);
                trans.commit();
            }
        });

        mRvBlockedUsers= (RecyclerView) v.findViewById(R.id.rv_blocked_users);


        mRvBlockedUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvBlockedUsers.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));



        adapter = new BlockedUsersAdapter(getActivity());
        mRvBlockedUsers.setAdapter(adapter);

        return v;
    }

    private class BlockedUsersAdapter extends RecyclerView.Adapter<BlockedUsersAdapter.ViewHolder> {

        private final Context context;
        private LayoutInflater inflater;

        public BlockedUsersAdapter(Context context) {
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
            View itemView = inflater.inflate(R.layout.custom_blocked_user_view, parent, false);
            return new ViewHolder(itemView);
        }

        class ViewHolder extends RecyclerView.ViewHolder{



            public TextView tvUsername;

            public ViewHolder(View view) {
                super(view);

                tvUsername = (TextView) view.findViewById(R.id.tv_username);

            }

        }
    }

}
