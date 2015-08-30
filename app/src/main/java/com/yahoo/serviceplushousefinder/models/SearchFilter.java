package com.yahoo.serviceplushousefinder.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aliku on 2015/8/30.
 */
public class SearchFilter implements Parcelable {
    private String ageRangeText[] = new String[] {
            "10年以下",
            "11-20 年",
            "21-30 年",
            "超過30年"
    };

    private String city;//台北市
    private String zone;//中山區
    private String buyOrRent;//rent,buy
    private String positionX;//E121.472
    private String positionY;//N25.0323
    private int roomRange;
    private int ageRange;
    private int areaRange;
    private int pricaeRange;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
