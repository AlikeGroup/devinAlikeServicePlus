package com.yahoo.serviceplushousefinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.adapters.ListingFragmentPagerAdapter;
import com.yahoo.serviceplushousefinder.fragments.ListingFragment;
import com.yahoo.serviceplushousefinder.models.SearchFilter;
import com.yahoo.serviceplushousefinder.models.Item;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements ListingFragment.OnItemLoadedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager vPager = (ViewPager) findViewById(R.id.viewpager);
        vPager.setAdapter(new ListingFragmentPagerAdapter(getSupportFragmentManager(), this));
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(vPager);
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
        startActivity(intent);
    }

    public void onClickMap(MenuItem item) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("user", "user1");
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent it) {
        if (resultCode == RESULT_OK) {
            SearchFilter filter = (SearchFilter) it.getParcelableExtra("filter");
        }
    }

    @Override
    public void refreshMapMarker(ArrayList<Item> newItems) {
        return;
    }
}
