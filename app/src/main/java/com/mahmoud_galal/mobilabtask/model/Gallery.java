/*
 * Copyright (c) 2019 Mahmoud Galal.
 * Mahmoudgalal57@yahoo.com
 */

package com.mahmoud_galal.mobilabtask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Gallery implements Parcelable {

    public String id ;
    public String title;
    @SerializedName("datetime")
    public long dateTime ;
    @SerializedName("cover")
    public String coverId;
    @SerializedName("account_url")
    public String accountUrl ;
    @SerializedName("account_id")
    public String account_id;
    public String link;
    @SerializedName("is_album")
    public boolean isAlbum ;
    public int ups;
    public int downs;
    public int score;
    public List<Image> images;


    protected Gallery(Parcel in) {
        id = in.readString();
        title = in.readString();
        dateTime = in.readLong();
        coverId = in.readString();
        accountUrl = in.readString();
        account_id = in.readString();
        link = in.readString();
        isAlbum = in.readByte() != 0;
        ups = in.readInt();
        downs = in.readInt();
        score = in.readInt();
        images =  in.readArrayList(Image.class.getClassLoader());
    }

    public static final Creator<Gallery> CREATOR = new Creator<Gallery>() {
        @Override
        public Gallery createFromParcel(Parcel in) {
            return new Gallery(in);
        }

        @Override
        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeLong(dateTime);
        parcel.writeString(coverId);
        parcel.writeString(accountUrl);
        parcel.writeString(account_id);
        parcel.writeString(link);
        parcel.writeByte((byte) (isAlbum ? 1 : 0));
        parcel.writeInt(ups);
        parcel.writeInt(downs);
        parcel.writeInt(score);
        parcel.writeList(images);
    }

    /**
     * Returns the Cover Image of the gallery
     * @return
     */
    public Image findCoverImage(){
        if(coverId != null && images != null){
            for(Image img:images){
                if(img.id.toLowerCase().equals(coverId.toLowerCase()))
                    return img ;
            }
        }
        return  null;
    }
}
