package com.yahoo.serviceplushousefinder.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.models.Item;

import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends ActionBarActivity {

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        item = (Item) getIntent().getParcelableExtra("item");

        ImageView ivMainPhoto = (ImageView) findViewById(R.id.ivMainPhoto);
        Picasso.with(this).load(item.getImageurl()).into(ivMainPhoto);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(item.getTitle());
        TextView tvMid = (TextView) findViewById(R.id.tvMid);
        tvMid.setText("物件編號: 1" + item.getId());
        TextView tvModifyTime = (TextView) findViewById(R.id.tvModifyTime);
        tvModifyTime.setText("更新時間: " + getDate(item.getmTime()));
        Button btnPhone = (Button) findViewById(R.id.btnPhone);
        btnPhone.setText("Call me: "+item.getContactMobile());
        WebView wvDesc = (WebView) findViewById(R.id.wvDesc);
        wvDesc.getSettings().setBuiltInZoomControls(true);
        wvDesc.getSettings().setJavaScriptEnabled(true);
        wvDesc.loadDataWithBaseURL(null, item.getDescs(), "text/html", "UTF-8", null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    private String getDate(long time) {
        System.out.println(time);
        Calendar cal = Calendar.getInstance(Locale.TAIWAN);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("yyyy年MM月dd日", cal).toString();
        return date;
    }

    public void goDail(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + item.getContactMobile());
        intent.setData(uri);
        startActivity(intent);
    }
}
