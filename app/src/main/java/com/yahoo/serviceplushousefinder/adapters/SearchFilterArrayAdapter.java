package com.yahoo.serviceplushousefinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.models.SearchFilter;

import java.util.List;

/**
 * Created by daiyan on 9/2/15.
 */
public class SearchFilterArrayAdapter extends ArrayAdapter<SearchFilter> {
    public SearchFilterArrayAdapter(Context context, List<SearchFilter> objects) {
        super(context, android.R.layout.simple_expandable_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SearchFilter filter = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            // create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.filter_history, parent, false);
        }
        // Lookup the views for populating the data (image, caption)
        TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
        TextView tvBoR = (TextView) convertView.findViewById(R.id.tvBoR);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvRoom = (TextView) convertView.findViewById(R.id.tvRoom);
        TextView tvAge = (TextView) convertView.findViewById(R.id.tvAge);

        if (filter.getCity().equals("")) {
            tvCity.setText("All");
        } else {
            tvCity.setText(filter.getCity());
        }
        tvBoR.setText(filter.getBuyOrRentText());
        tvPrice.setText(filter.getPriceText());
        tvRoom.setText(filter.getRoomText());
        tvAge.setText(filter.getAgeText());
        return convertView;
    }
}
