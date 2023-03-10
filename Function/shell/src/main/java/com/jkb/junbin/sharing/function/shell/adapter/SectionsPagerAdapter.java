package com.jkb.junbin.sharing.function.shell.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.jkb.junbin.sharing.function.shell.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Integer> tabTitles = new ArrayList<>();

    public SectionsPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments,List<Integer> tabTitles) {
        super(fm);
        mContext = context;
        this.fragments = fragments;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(tabTitles.get(position));
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return tabTitles.size();
    }
}