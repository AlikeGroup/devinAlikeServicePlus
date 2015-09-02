package com.yahoo.serviceplushousefinder.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.activities.DetailActivity;
import com.yahoo.serviceplushousefinder.models.Item;

import java.util.List;

/**
 * Created by aliku on 2015/8/29.
 */
public class ItemsArrayAdapter extends ArrayAdapter<Item> {
    Context context;

    public ItemsArrayAdapter(Context context, List<Item> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    // override and setup custom template


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listing_item,parent,false);
        }

        ImageView ivHouseImage = (ImageView) convertView.findViewById(R.id.ivHouseImage);
        if (item.getImageurl() != "") {
            // insert the image using picasso
            Picasso.with(getContext())
                    .load(item.getImageurl())
                    .placeholder(R.drawable.ic_picture)
                    .error(R.drawable.ic_picture)
                    .fit()
                    .into(ivHouseImage);
        } else {
            ivHouseImage.setImageResource(R.drawable.ic_picture);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(item.getTitle());
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        tvAddress.setText(item.getAddress());
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        tvPrice.setText(item.getPrice());
        TextView tvPageview = (TextView) convertView.findViewById(R.id.tvPageview);
        tvPageview.setText(String.valueOf(item.getPageview()));

        final RelativeLayout rlTweetCard = (RelativeLayout) convertView.findViewById(R.id.rlListingItem);
        rlTweetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(item);
            }
        });

        return convertView;
    }

    private void onClickItem(Item item) {
        Intent intent = new Intent(this.context, DetailActivity.class);
        intent.putExtra("item", item);
        this.context.startActivity(intent);
    }

}
