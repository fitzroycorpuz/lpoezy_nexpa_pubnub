package com.lpoezy.nexpa.screens;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.lpoezy.nexpa.R;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InviteFriendsScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFriendsScreen extends Fragment {

    public static final String TAG = InviteFriendsScreen.class.getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mWrappedAdapter;

    public InviteFriendsScreen() {

    }


    public static InviteFriendsScreen newInstance() {
        InviteFriendsScreen fragment = new InviteFriendsScreen();
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
        View v = (View)inflater.inflate(R.layout.fragment_invite_friends_screen, container, false);


        mToolbar = (Toolbar)v.findViewById(R.id.app_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        android.support.v7.app.ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

        ((TextView)mToolbar.findViewById(R.id.tab_title)).setText("Invite Friends");

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

        //noinspection ConstantConditions
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_invite_friends);


        Friend f0 = new Friend("Josh Adams");
        Friend f1 = new Friend("Alexa Taylor");
        Friend f2 = new Friend("Catherine Jones");
        Friend f3 = new Friend("Drew Barry");

        String[] smsList = getResources().getStringArray(R.array.social_media_sites);
        SocialMediaSite myContacts = new SocialMediaSite(smsList[0], Arrays.asList(f0));
        SocialMediaSite myFacebook = new SocialMediaSite(smsList[1], Arrays.asList(f1, f2, f3));
        SocialMediaSite myInstagram = new SocialMediaSite(smsList[2], Arrays.asList(f0));
        SocialMediaSite myTwitter = new SocialMediaSite(smsList[3], Arrays.asList(f1, f2, f3));
        SocialMediaSite mySnapchat = new SocialMediaSite(smsList[4], Arrays.asList(f0));

        List<SocialMediaSite> smSites = Arrays.asList(myContacts, myFacebook, myInstagram, myTwitter, mySnapchat);


        MyAdapter adapter = new MyAdapter(getActivity(), smSites);


        adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {

            }

            @Override
            public void onListItemCollapsed(int position) {


            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private class MyAdapter extends ExpandableRecyclerAdapter<SMSViewHolder, FriendViewHolder> {

        private LayoutInflater mInflator;

        public MyAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
            super(parentItemList);
            mInflator = LayoutInflater.from(context);
        }

        // onCreate ...
        @Override
        public SMSViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
            View recipeView = mInflator.inflate(R.layout.custom_invite_friends_parent_view, parentViewGroup, false);
            return new SMSViewHolder(recipeView);
        }

        @Override
        public FriendViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
            View ingredientView = mInflator.inflate(R.layout.custom_invite_friends_child_view, childViewGroup, false);
            return new FriendViewHolder(ingredientView);
        }

        // onBind ...
        @Override
        public void onBindParentViewHolder(SMSViewHolder smsViewHolder, int position, ParentListItem parentListItem) {
            SocialMediaSite recipe = (SocialMediaSite) parentListItem;
            smsViewHolder.bind(recipe);
        }

        @Override
        public void onBindChildViewHolder(FriendViewHolder friendViewHolder, int position, Object childListItem) {
            Friend friend = (Friend) childListItem;
            friendViewHolder.bind(friend);
        }
    }

    private class SocialMediaSite implements ParentListItem {


        private final String name;
        private List socialMediaSites;

        public SocialMediaSite(String name, List socialMediaSites) {
            this.socialMediaSites = socialMediaSites;
            this.name = name;
        }

        @Override
        public List getChildItemList() {
            return socialMediaSites;
        }

        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }


        public final String getName() {
            return this.name;
        }
    }

    private class Friend{
        private final String name;

        public Friend(String name){this.name = name;}

        public final String getName(){return this.name;}
    }

    private class SMSViewHolder extends ParentViewHolder {

        private final TextView tvSMS;

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;
        private final ImageView arrowExpandImageView;


        public SMSViewHolder(View itemView) {
            super(itemView);
            this.tvSMS = (TextView) itemView.findViewById(R.id.tv_sms);
            this.arrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);
        }

        public void bind(SocialMediaSite sms) {
            this.tvSMS.setText(sms.getName());
        }

        @SuppressLint("NewApi")
        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (expanded) {
                    this.arrowExpandImageView.setRotation(ROTATED_POSITION);
                } else {
                    this.arrowExpandImageView.setRotation(INITIAL_POSITION);
                }
            }
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                RotateAnimation rotateAnimation;
                if (expanded) { // rotate clockwise
                    rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else { // rotate counterclockwise
                    rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                this.arrowExpandImageView.startAnimation(rotateAnimation);
            }
        }
    }

    class FriendViewHolder extends ChildViewHolder {

        private final TextView tvUsername;
        private TextView mIngredientTextView;

        public FriendViewHolder(View itemView) {
            super(itemView);

            this.tvUsername = (TextView) itemView.findViewById(R.id.tv_username);

        }

        public void bind(Friend friend) {
            this.tvUsername.setText(friend.getName());
        }
    }

}
