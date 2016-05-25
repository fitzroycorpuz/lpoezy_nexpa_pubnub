package com.lpoezy.nexpa;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.lpoezy.nexpa.screens.EditProfileScreen;
import com.lpoezy.nexpa.screens.ForgotPasswordScreen;
import com.lpoezy.nexpa.screens.HomeScreen;
import com.lpoezy.nexpa.screens.NotificationsScreen;
import com.lpoezy.nexpa.screens.SearchCriteriaScreen;
import com.lpoezy.nexpa.screens.SettingsScreen;
import com.lpoezy.nexpa.screens.SigninScreen;
import com.lpoezy.nexpa.screens.SignupScreen;
import com.lpoezy.nexpa.tabs.SlidingTabLayout;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


public class HomeTabActivity extends ActionBarActivity implements MaterialTabListener {


    private Toolbar mToolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private ArrayList<Fragment> mTabFragments;
    private MaterialTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);




        mTabFragments = new ArrayList<Fragment>();
        mTabFragments.add(HomeScreen.newInstance());
        mTabFragments.add(NotificationsScreen.newInstance());
        mTabFragments.add(SettingsScreen.newInstance());
        mTabFragments.add(EditProfileScreen.newInstance());
        mTabFragments.add(SearchCriteriaScreen.newInstance());

        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);

        mPager = (ViewPager)this.findViewById(R.id.pager);

        // insert all tabs from pagerAdapter data
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());


        mPager.setAdapter(pagerAdapter);


        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
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

    }
    int[] icons = new  int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private Drawable getIcon(int position) {
        Drawable drawable = getResources().getDrawable(icons[position]);
        return drawable;
    }

    @Override
    public void onTabSelected(MaterialTab tab) {

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
            icons = new  int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
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
