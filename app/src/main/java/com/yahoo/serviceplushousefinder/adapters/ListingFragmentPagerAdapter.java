package com.yahoo.serviceplushousefinder.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yahoo.serviceplushousefinder.fragments.ListingFragment;

/**
 * Created by aliku on 2015/8/27.
 */
public class ListingFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Price", "Area", "Time" };
    private Context context;
    private ListingFragment[] fragments = new ListingFragment[] {null, null, null};

    public ListingFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        ListingFragment lF = ListingFragment.newInstance(position + 1);
        fragments[position] = lF;
        return lF;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public ListingFragment getFragmentInstanceByTab(int tab) {
        return fragments[tab];

    }
}