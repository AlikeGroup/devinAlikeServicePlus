package com.yahoo.serviceplushousefinder.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aliku on 2015/8/28.
 */
public class Item {
    private String id;
    private String title;
    private String address;
    private String latlong;
    private int pageview;
    private String price;
    private String descs;

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
                }
                //item.save(); // insert into sqllite
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    public static ArrayList<Item> fromJSONObject(JSONObject json){
        ArrayList<Item> items = new ArrayList<>();
        try {

            JSONObject query = json.getJSONObject("query");
            Log.d("query", query.toString());
            JSONObject results = query.getJSONObject("results");
            JSONArray jsonHits = results.getJSONArray("hit");
            for(int i =0; i< jsonHits.length(); i++){
                JSONArray fieldJson = jsonHits.getJSONObject(i).getJSONArray("field");
                Item item = Item.fromJSON(fieldJson);
                Log.d("item", item.getTitle());
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

}
