package com.yahoo.serviceplushousefinder.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.adapters.SearchFilterArrayAdapter;
import com.yahoo.serviceplushousefinder.models.SearchFilter;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends ActionBarActivity {

    private ArrayList<SearchFilter> filter;
    private SearchFilterArrayAdapter filterAdapter;
    private ListView lvFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lvFilter = (ListView) findViewById(R.id.lvFilter);
        setUpFilterAdapter();
        List<SearchFilter> data = new Select().from(SearchFilter.class).execute();
        filterAdapter.addAll(data);
        filterAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnBackOnClick(View view) {
        setResult(RESULT_OK);
        finish();
    }

    private void setUpFilterAdapter() {

        filter = new ArrayList<>();
        filterAdapter = new SearchFilterArrayAdapter(this, filter);
        lvFilter.setAdapter(filterAdapter);
        //client = RestApplication.getRestClient(); // singleton client
        filterAdapter.clear();
    }
}
