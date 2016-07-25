package com.lpoezy.nexpa.screens;


import android.content.Context;
import android.content.Intent;
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

import com.lpoezy.nexpa.ChatHistoryActivity;
import com.lpoezy.nexpa.HomeTabActivity;
import com.lpoezy.nexpa.R;
import com.lpoezy.nexpa.SearchCriteriaActivity;
import com.lpoezy.nexpa.models.M_Broadcast;
import com.lpoezy.nexpa.models.M_Broadcasts;
import com.lpoezy.nexpa.models.OnUpdateScreenListener;
import com.lpoezy.nexpa.service.MyPubNubService;
import com.lpoezy.nexpa.utils.DividerItemDecoration;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


public class HomeScreen extends Fragment implements OnUpdateScreenListener{


    private Toolbar mToolbar;
    private RecyclerView mRvBroadcasts;
    private BroadcastsAdapter mAdapter;
    private SwipyRefreshLayout mSwipeRefreshLayout;

    public HomeScreen() {

    }


    public static HomeScreen newInstance() {
        HomeScreen fragment = new HomeScreen();
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

    private List<M_Broadcast> mBroadcasts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = (View)inflater.inflate(R.layout.fragment_home_screen, container, false);
        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        mRvBroadcasts = (RecyclerView) v.findViewById(R.id.rv_broadcasts);


        mRvBroadcasts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvBroadcasts.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));


        mBroadcasts = new ArrayList<M_Broadcast>();
        mAdapter = new BroadcastsAdapter(getActivity());
        mRvBroadcasts.setAdapter(mAdapter);


        mSwipeRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.niagara, R.color.buttercup, R.color.niagara);
        mSwipeRefreshLayout.setBackgroundColor(getResources().getColor(R.color.carrara));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {



            }
        });

        ((ImageView)mToolbar.findViewById(R.id.btn_refine_search)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getActivity(), SearchCriteriaActivity.class);
                getActivity().startActivity(activity);
            }
        });

        ((ImageView)mToolbar.findViewById(R.id.btn_message)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(getActivity(), ChatHistoryActivity.class);
                getActivity().startActivity(activity);
            }
        });


        return v;
    }

    @Override
    public void onResume() {

        super.onResume();

        onUpdateScreen();

        onProcessReceivedMessage();
    }

    private void onProcessReceivedMessage() {

        if(((HomeTabActivity)getActivity()).isServiceConnected()){
            ((HomeTabActivity)getActivity()).getService().addOnPubNubCallbackListener(new MyPubNubService.OnPubNubCallbackListener() {
                @Override
                public void onMessageReceived(Object object) {
                    onUpdateScreen();
                }

                @Override
                public void onMessageSent(Object object) {

                }

                @Override
                public void onMessageSendingFailed() {

                }
            });
        }

    }


    @Override
    public void onUpdateScreen() {

        M_Broadcasts broadcasts = new M_Broadcasts(getActivity());

        broadcasts.addOnReadingCompleteListener(new M_Broadcasts.OnReadingCompleteListener() {
            @Override
            public void onReadingComplete(int result, List<M_Broadcast> broadcasts) {

                if(result==M_Broadcasts.READING_COMPLETE){
                    mBroadcasts.clear();
                    mBroadcasts.addAll(broadcasts);
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

        broadcasts.read();

    }

    private class BroadcastsAdapter extends RecyclerView.Adapter<BroadcastsAdapter.ViewHolder> {

        private final Context context;
        private LayoutInflater inflater;



        public BroadcastsAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);

            this.context = context;

        }


        @Override
        public int getItemCount() {

            return mBroadcasts.size();
        }

        @Override
        public void onBindViewHolder(final ViewHolder vh, int position) {

            vh.tvUsername.setText(mBroadcasts.get(position).getFrm());
            vh.tvBroadcastMsg.setText(mBroadcasts.get(position).getMsg());

            long millis = mBroadcasts.get(position).getTimeStamp() / 10000000;
            DateTime dt = new DateTime(millis);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mma");
            String str = fmt.print(dt);

            vh.tvDateLocation.setText(str);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View itemView = inflater.inflate(R.layout.custom_broadcast_view, parent, false);
            return new ViewHolder(itemView);
        }

        class ViewHolder extends RecyclerView.ViewHolder  {


            TextView tvUsername;
            TextView tvDateLocation;
            TextView tvBroadcastMsg;
            TextView tvReachedCount;
            TextView tvLikesCount;
            TextView tvCommentsCount;
            ImageView imgProfilePic;
            ImageView btnLikes;
            ImageView btnComments;
            ImageView btnShare;
            ImageView btnMute;
            int position;

            public ViewHolder(View view) {
                super(view);

                tvUsername = (TextView) view.findViewById(R.id.tv_username);
                tvDateLocation = (TextView) view.findViewById(R.id.tv_date_location);
                tvBroadcastMsg = (TextView) view.findViewById(R.id.tv_broadcast_msg);
                tvReachedCount = (TextView) view.findViewById(R.id.tv_reached_count);
                tvLikesCount = (TextView) view.findViewById(R.id.tv_likes_count);
                tvCommentsCount = (TextView) view.findViewById(R.id.tv_comments_count);
                imgProfilePic = (ImageView) view.findViewById(R.id.img_profile_pic);//

                btnLikes = (ImageView) view.findViewById(R.id.btn_likes);
                btnComments = (ImageView) view.findViewById(R.id.btn_comments);
                btnShare = (ImageView) view.findViewById(R.id.btn_share);
                btnMute = (ImageView) view.findViewById(R.id.btn_mute);

            }

        }
    }
}
