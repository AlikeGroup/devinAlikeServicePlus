package com.yahoo.serviceplushousefinder.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.adapters.ListingFragmentPagerAdapter;
import com.yahoo.serviceplushousefinder.fragments.ListingFragment;
import com.yahoo.serviceplushousefinder.models.Item;
import com.yahoo.serviceplushousefinder.models.SearchFilter;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements ListingFragment.OnItemLoadedListener {

    private SearchFilter searchFilter;
    private int tab;
    private ListingFragmentPagerAdapter lfpAdapter;
    private ViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        vPager = (ViewPager) findViewById(R.id.viewpager);
        lfpAdapter = new ListingFragmentPagerAdapter(getSupportFragmentManager(), this);
        vPager.setAdapter(lfpAdapter);
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(vPager);


        setSearchFilter(new SearchFilter());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bar_search_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSetting(MenuItem item) {
        Intent intent = new Intent(this, SearchSettingActivity.class);
        intent.putExtra("user", "user1");
        //startActivity(intent);
        startActivityForResult(intent,2888,null);
    }

    public void onClickProfile(MenuItem item) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("user", "user1");
        startActivityForResult(intent,3458,null);
    }

    public void onClickMap(MenuItem item) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("user", "user1");
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent it) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2888){
                SearchFilter filter = (SearchFilter) it.getParcelableExtra("filter");
                Log.e("filterFromSetting", filter.toString());

                SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("city", filter.getCity());
                edit.commit();

                //setSearchFilter(filter);
                Log.d("vPager.getCurrentItem", String.valueOf(vPager.getCurrentItem()));

                ListingFragment lFragment = lfpAdapter.getFragmentInstanceByTab(vPager.getCurrentItem());
                lFragment.searchGEO("0.0",
                        "0.0",
                        14,
                        1,
                        filter);

            }
            if (requestCode == 3458){
                SearchFilter filter = (SearchFilter) it.getParcelableExtra("filter");
                Log.e("filterFromProfile", filter.toString());

                SharedPreferences pref =
                        PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("city", filter.getCity());
                edit.commit();

                //setSearchFilter(filter);
                Log.d("vPager.getCurrentItem", String.valueOf(vPager.getCurrentItem()));

                ListingFragment lFragment = lfpAdapter.getFragmentInstanceByTab(vPager.getCurrentItem());
                lFragment.searchGEO("0.0",
                        "0.0",
                        14,
                        1,
                        filter);
            }
            //List<SearchFilter> data = new Select().from(SearchFilter.class).execute();
        }
    }

    @Override
    public void refreshMapMarker(ArrayList<Item> newItems) {
        return;
    }

    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }
}
