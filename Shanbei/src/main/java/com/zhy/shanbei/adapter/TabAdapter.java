package com.zhy.shanbei.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhy.shanbei.activity.MainFragment;

public class TabAdapter extends FragmentPagerAdapter
{


    public static final String[] TITLES = new String[] { "unit 1","unit 2","unit 3" ,"unit 4"};
    public TabAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int arg0) {
        MainFragment fragment = new MainFragment(arg0+1);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return TITLES[position % TITLES.length];
    }

    @Override
    public int getCount()
    {
        return TITLES.length;
    }

}
