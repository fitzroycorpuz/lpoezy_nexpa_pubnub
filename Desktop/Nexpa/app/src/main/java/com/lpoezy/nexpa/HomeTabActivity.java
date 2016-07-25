package com.lpoezy.nexpa;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.lpoezy.nexpa.models.M_Broadcast;
import com.lpoezy.nexpa.models.M_Broadcasts;
import com.lpoezy.nexpa.models.OnUpdateScreenListener;
import com.lpoezy.nexpa.screens.BroadcastsScreen;
import com.lpoezy.nexpa.screens.EditProfileScreen;
import com.lpoezy.nexpa.screens.ForgotPasswordScreen;
import com.lpoezy.nexpa.screens.HomeScreen;
import com.lpoezy.nexpa.screens.NearMeScreen;
import com.lpoezy.nexpa.screens.NotificationsScreen;
import com.lpoezy.nexpa.screens.ProfileScreen;
import com.lpoezy.nexpa.screens.SearchCriteriaScreen;
import com.lpoezy.nexpa.screens.SettingsScreen;
import com.lpoezy.nexpa.screens.SigninScreen;
import com.lpoezy.nexpa.screens.SignupScreen;
import com.lpoezy.nexpa.service.MyPubNubService;
import com.lpoezy.nexpa.tabs.SlidingTabLayout;
import com.lpoezy.nexpa.utils.AccountManager;
import com.lpoezy.nexpa.utils.L;
import com.lpoezy.nexpa.views.TabsViewPager;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class HomeTabActivity extends ActionBarActivity implements MaterialTabListener {


    private Toolbar mToolbar;
    private TabsViewPager mPager;
    private SlidingTabLayout mTabs;
    private ArrayList<Fragment> mTabFragments;
    private MaterialTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);




        mTabFragments = new ArrayList<Fragment>();
        mTabFragments.add(HomeScreen.newInstance());
        mTabFragments.add(NearMeScreen.newInstance());
        mTabFragments.add(BroadcastsScreen.newInstance());
        mTabFragments.add(NotificationsScreen.newInstance());
        mTabFragments.add(ProfileScreen.newInstance());

        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);

        mPager = (TabsViewPager)this.findViewById(R.id.pager);
        mPager.setPagingEnabled(false);
        // insert all tabs from pagerAdapter data
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());


        mPager.setAdapter(pagerAdapter);


        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                tabHost.setSelectedNavigationItem(position);

            }
        });


        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(getIcon(i))
                            .setTabListener(this)
            );
        }

        RelativeLayout dummyView = (RelativeLayout) findViewById(R.id.dummyView);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Crouton.cancelAllCroutons();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(mServiceConn);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent service = new Intent(HomeTabActivity.this, MyPubNubService.class);

        startService(service);
        bindService(service, mServiceConn, BIND_AUTO_CREATE);
    }

    private boolean isServiceConnected;
    private MyPubNubService mService;

    public boolean isServiceConnected() {
        return isServiceConnected;
    }

    public MyPubNubService getService() {
        return mService;
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {

            isServiceConnected = true;

            MyPubNubService.LocalBinder binder = (MyPubNubService.LocalBinder) service;
            mService = binder.getService();

            AccountManager am = new AccountManager(HomeTabActivity.this);
            String uname = am.getUserName();
            String email = am.getEmail();

            //save message to couchbase
//            M_Broadcast broadcast = new M_Broadcast(getApplicationContext(), "hello world!!!", uname, 0,"Tondo Manila", "Y");
//            String msgId = broadcast.write();
//            mService.publishTo(email, msgId);



        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
            mService = null;
        }
    };

    int[] icons = new  int[]{R.drawable.ic_home_tab, R.drawable.ic_nearme_tab, R.drawable.ic_broadcast_tab, R.drawable.ic_notifications_tab, R.drawable.ic_profile_tab};
    private Drawable getIcon(int position) {
        Drawable drawable = getResources().getDrawable(icons[position]);
        return drawable;
    }

    @Override
    public void onTabSelected(MaterialTab tab) {

        ((OnUpdateScreenListener)mTabFragments.get(tab.getPosition())).onUpdateScreen();

        // when the tab is clicked the pager swipe content to the tab position
        mPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        String[] tabs;
        int[] icons;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
            //icons = new  int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
        }

        @Override
        public Fragment getItem(int position) {

            Fragment screen = mTabFragments.get(position);
            return screen;
        }

        @Override
        public int getCount() {
            return mTabFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);

            ImageSpan imgSpan = new ImageSpan(drawable);

            SpannableString spannableString = new SpannableString("");
            spannableString.setSpan(imgSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }

}
