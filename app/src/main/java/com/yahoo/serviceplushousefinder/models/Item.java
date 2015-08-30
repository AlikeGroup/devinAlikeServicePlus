package com.yahoo.serviceplushousefinder.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aliku on 2015/8/28.
 */
public class Item implements Parcelable {
    private String id;
    private String title;
    private String address;
    private String latlong;
    private int pageview;
    private String price;
    private String descs;
    private String imageurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public int getPageview() {
        return pageview;
    }

    public void setPageview(int pageview) {
        this.pageview = pageview;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public static Item fromJSON(JSONArray fieldArray) {
        Item item = new Item();
        String name;
        for(int i =0; i< fieldArray.length(); i++) {
            try {
                Boolean hasContent = fieldArray.getJSONObject(i).has("content");
                name = fieldArray.getJSONObject(i).getString("name");
                switch (name) {
                    case "mid":
                        item.id = fieldArray.getJSONObject(i).getString("content");
                        break;
                    case "title":
                        item.title = fieldArray.getJSONObject(i).getString("content");
                        break;
                    case "location":
                        item.address = fieldArray.getJSONObject(i).getString("content");
                        break;
                    case "latlong":
                        if (hasContent) {
                            item.latlong = fieldArray.getJSONObject(i).getString("content");
                        } else {
                            item.latlong = "";
                        }
                        break;
                    case "pageview":
                        item.pageview = fieldArray.getJSONObject(i).getInt("content");
                        break;
                    case "price":
                        item.price = fieldArray.getJSONObject(i).getString("content");
                        break;
                    case "descs":
                        item.descs = fieldArray.getJSONObject(i).getString("content");
                        break;
                    case "images":
                        if (hasContent) {
                            String imageJsonStr = fieldArray.getJSONObject(i).getString("content");
                            item.imageurl = getImageUrl(imageJsonStr);
                        } else {
                            item.imageurl = "";
                        }
                        break;
                }
                //item.save(); // insert into sqllite
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    private static String getImageUrl(String imageJsonStr) {
        String imgUrl = "";
        try {
            JSONObject obj = new JSONObject(imageJsonStr);
            imgUrl = obj.getJSONObject("large").getString("url");
            //Log.d("imgurl", imgUrl);
        } catch (Throwable t) {
            Log.e("imgurl", "Could not parse malformed JSON: \"" + imageJsonStr + "\"");
        }
        return imgUrl;
    }

    public static ArrayList<Item> fromJSONObject(JSONObject json){
        ArrayList<Item> items = new ArrayList<>();
        try {

            JSONObject query = json.getJSONObject("query");
            //Log.d("query", query.toString());
            JSONObject results = query.getJSONObject("results");
            JSONArray jsonHits = results.getJSONArray("hit");
            for(int i =0; i< jsonHits.length(); i++){
                JSONArray fieldJson = jsonHits.getJSONObject(i).getJSONArray("field");
                Item item = Item.fromJSON(fieldJson);
                //Log.d("item", item.getTitle());
                if (item != null){
                    items.add(item);
                }
            }
            return items;
        } catch (JSONException e) {
            e.printStackTrace();
            return items;
        }
    }

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(title);
        out.writeString(address);
        out.writeString(latlong);
        out.writeString(price);
        out.writeString(descs);
        out.writeString(imageurl);
        out.writeInt(pageview);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Item(Parcel in) {
        id = in.readString();
        title = in.readString();
        address = in.readString();
        latlong = in.readString();
        price = in.readString();
        descs = in.readString();
        imageurl = in.readString();
        pageview = in.readInt();
    }

    public Item() {
        // Normal actions performed by class, since this is still a normal object!
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

}
