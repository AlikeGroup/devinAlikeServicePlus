package com.yahoo.serviceplushousefinder.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aliku on 2015/8/30.
 */

@Table(name = "SearchFilter")
public class SearchFilter extends Model implements Parcelable {
    private String ageRangeText[] = new String[] {
            "10年以下",
            "11-20 年",
            "21-30 年",
            "超過30年"
    };
    private String zone;//中山區
    private String positionX;//E121.472
    private String positionY;//N25.0323
    private int areaRange;

    @Column(name = "city")
    private String city;//台北市
    @Column(name = "buyOrRent")
    private int buyOrRent;//rent,buy
    @Column(name = "room")
    private int roomRange;
    @Column(name = "age")
    private int ageRange;
    @Column(name = "price")
    private int pricaeRange;
    //@Column(name = "id")
    //private int id;

    public String getCity() {
        return city;
    }

    public String getZone() {
        return zone;
    }

    public int getBuyOrRent() {
        return buyOrRent;
    }

    public String getPositionX() {
        return positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public int getRoomRange() {
        return roomRange;
    }

    public int getAgeRange() {
        return ageRange;
    }

    public int getAreaRange() {
        return areaRange;
    }

    public int getPricaeRange() {
        return pricaeRange;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setBuyOrRent(int buyOrRent) {
        this.buyOrRent = buyOrRent;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public void setRoomRange(int roomRange) {
        this.roomRange = roomRange;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }

    public void setAreaRange(int areaRange) {
        this.areaRange = areaRange;
    }

    public void setPricaeRange(int pricaeRange) {
        this.pricaeRange = pricaeRange;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(city);
        out.writeInt(buyOrRent);
        out.writeInt(roomRange);
        out.writeInt(pricaeRange);
        out.writeInt(ageRange);
    }

    private SearchFilter(Parcel in) {
        city = in.readString();
        buyOrRent = in.readInt();
        roomRange = in.readInt();
        pricaeRange = in.readInt();
        ageRange = in.readInt();
    }

    public SearchFilter(){

    }

    public static final Parcelable.Creator<SearchFilter> CREATOR
            = new Parcelable.Creator<SearchFilter>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public SearchFilter createFromParcel(Parcel in) {
            return new SearchFilter(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public SearchFilter[] newArray(int size) {
            return new SearchFilter[size];
        }
    };

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
