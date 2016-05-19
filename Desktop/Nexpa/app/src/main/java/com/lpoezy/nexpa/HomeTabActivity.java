package com.lpoezy.nexpa;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.lpoezy.nexpa.screens.ForgotPasswordScreen;
import com.lpoezy.nexpa.screens.SigninScreen;
import com.lpoezy.nexpa.screens.SignupScreen;
import com.lpoezy.nexpa.tabs.SlidingTabLayout;

import java.util.ArrayList;


public class HomeTabActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private ArrayList<Fragment> mTabFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);

        mToolbar = (Toolbar)this.findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);


        mTabFragments = new ArrayList<Fragment>();
        mTabFragments.add(ForgotPasswordScreen.newInstance());
        mTabFragments.add(SigninScreen.newInstance());
        mTabFragments.add(SignupScreen.newInstance());
        mTabFragments.add(ForgotPasswordScreen.newInstance());
        mTabFragments.add(SigninScreen.newInstance());

        mPager = (ViewPager)this.findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout)this.findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tv_tab_text);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        mTabs.setViewPager(mPager);



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
