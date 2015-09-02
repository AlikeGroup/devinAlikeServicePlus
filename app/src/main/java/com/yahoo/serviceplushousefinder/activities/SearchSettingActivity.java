package com.yahoo.serviceplushousefinder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.models.SearchFilter;

public class SearchSettingActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerPriceRange;
    Spinner spinnerBoR;
    Spinner spinnerRoom;
    Spinner spinnerAge;
    EditText etCity;
    EditText etPositionX;
    EditText etPositionY;

    ArrayAdapter<CharSequence> adapterSpinnerPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_setting);
        etCity = (EditText) findViewById(R.id.etCity);
        spinnerBoR = (Spinner) findViewById(R.id.spinnerBuyOrRent);
        spinnerPriceRange = (Spinner) findViewById(R.id.spinnerPriceRange);
        spinnerRoom = (Spinner) findViewById(R.id.spinnerRoom);
        spinnerAge = (Spinner) findViewById(R.id.spinnerAge);

        setDefault();
        spinnerBoR.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
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

    public void btnSaveOnClick(View view) {
        SearchFilter filter = getFilter();
        filter.save();
        Intent intent = new Intent();
        intent.putExtra("filter", filter);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void setDefault(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerBoR = ArrayAdapter.createFromResource(this,
                R.array.buyorrent_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerBoR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBoR.setAdapter(adapterSpinnerBoR);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerRoom = ArrayAdapter.createFromResource(this,
                R.array.roomrange_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRoom.setAdapter(adapterSpinnerRoom);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterSpinnerAge = ArrayAdapter.createFromResource(this,
                R.array.agerange_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinnerAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerAge.setAdapter(adapterSpinnerAge);
    }

    private SearchFilter getFilter(){
        SearchFilter filter = new SearchFilter();
        filter.setAgeRange(spinnerAge.getSelectedItemPosition());
        filter.setBuyOrRent(spinnerBoR.getSelectedItemPosition());
        filter.setCity(etCity.getText().toString());
        filter.setPricaeRange(spinnerPriceRange.getSelectedItemPosition());
        filter.setRoomRange(spinnerRoom.getSelectedItemPosition());
        return filter;
    }

    private void setPriceRange(int position){
        //Log.e("Selected", Integer.toString(position));
        if(position == 0){
            adapterSpinnerPrice = ArrayAdapter.createFromResource(this,
                    R.array.buy_pricerange_array, android.R.layout.simple_spinner_item);
            adapterSpinnerPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPriceRange.setAdapter(adapterSpinnerPrice);
        }else if (position == 1){
            adapterSpinnerPrice = ArrayAdapter.createFromResource(this,
                    R.array.rent_pricerange_array, android.R.layout.simple_spinner_item);
            adapterSpinnerPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPriceRange.setAdapter(adapterSpinnerPrice);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Log.e("Selected",Integer.toString(position));
        setPriceRange(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


/*

    public void btnSaveOnClick(View view) {
        setResult(RESULT_OK);
        add(etCity.getText().toString()
                , spinnerBoR.getSelectedItemPosition()
                , spinnerPriceRange.getSelectedItemPosition()
                , spinnerRoom.getSelectedItemPosition()
                , spinnerAge.getSelectedItemPosition()
                , "0"
                , "0");
                //, etPositionX.getText().toString()
                //, etPositionY.getText().toString());
        closeDB();
        finish();
    }

private void add(String city, int mode, int price, int room, int age, String x, String y){
        SQLiteDatabase db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_DATE", getDateTime());
        values.put("_MODE", mode);
        values.put("_CITY", city);
        values.put("_ROOMRANGE", room);
        values.put("_AGERANGE", age);
        values.put("_PRICERANGE", price);
        values.put("_POSITIONX", x);
        values.put("_POSITIONY", y);
        db.insert("SearchFilter", null, values);
    }
    private void openDB(){
        DH = new DBHelper(this);
    }

    private void closeDB(){
        DH.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }
 */