/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {
    public String id ;
    public String title;
    public String description;
    public String link;
    public String type;
    @SerializedName("datetime")
    public long dateTime ;
    public int width;
    public int height;
    public int ups;
    public int downs;
    public int score;

    protected Image(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        link = in.readString();
        type = in.readString();
        dateTime = in.readLong();
        width = in.readInt();
        height = in.readInt();
        ups = in.readInt();
        downs = in.readInt();
        score = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public boolean isGif(){
        return type!= null && type.trim().toLowerCase().equals("image/gif");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(link);
        parcel.writeString(type);
        parcel.writeLong(dateTime);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeInt(ups);
        parcel.writeInt(downs);
        parcel.writeInt(score);
    }
}
