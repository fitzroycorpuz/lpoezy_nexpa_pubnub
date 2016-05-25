package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.lpoezy.nexpa.utils.DividerItemDecoration;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;


public class NotificationsScreen extends Fragment {


    private RecyclerView mRvNotifications;
    private NotificationsAdapter adapter;
    private SwipyRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;

    public NotificationsScreen() {
        // Required empty public constructor
    }


    public static NotificationsScreen newInstance() {
        NotificationsScreen fragment = new NotificationsScreen();
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

        View v = (View)inflater.inflate(R.layout.fragment_notifications_screen, container, false);

        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Notifications");

         mRvNotifications= (RecyclerView) v.findViewById(R.id.rv_notifications);


        mRvNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvNotifications.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));



        adapter = new NotificationsAdapter(getActivity());
        mRvNotifications.setAdapter(adapter);


        mSwipeRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.niagara, R.color.buttercup, R.color.niagara);
        mSwipeRefreshLayout.setBackgroundColor(getResources().getColor(R.color.carrara));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {



            }
        });

        return v;
    }

    private class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

        private final Context context;
        private LayoutInflater inflater;



        public NotificationsAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);

            this.context = context;

        }


        @Override
        public int getItemCount() {

            return 5;
        }

        @Override
        public void onBindViewHolder(final ViewHolder vh, int position) {



        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View itemView = inflater.inflate(R.layout.custom_notifications_view, parent, false);
            return new ViewHolder(itemView);
        }

        class ViewHolder extends RecyclerView.ViewHolder  {


            TextView tvNotification;
            ImageView imgProfilePic;
            int position;

            public ViewHolder(View view) {
                super(view);

                tvNotification = (TextView) view.findViewById(R.id.tv_notification);
                imgProfilePic = (ImageView) view.findViewById(R.id.img_profile_pic);

            }

        }
    }

    class Notification {
        public String body;
        public Notification(){

        }
    }

}
