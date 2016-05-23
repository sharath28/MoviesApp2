package com.example.sharath.moviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sharath on 16/5/16.
 */
public class Trailer implements Parcelable {
    public String trailer_id;
    public String trailer_name;
    public String trailer_key;
    public int trailer_size;
    public String trailer_site;

    public Trailer(String trailer_id, String trailer_key, String trailer_name, String trailer_site,int trailer_size) {
        this.trailer_id = trailer_id;
        this.trailer_key = trailer_key;
        this.trailer_name = trailer_name;
        this.trailer_site = trailer_site;
        this.trailer_size = trailer_size;
    }


    protected Trailer(Parcel in) {
        trailer_id = in.readString();
        trailer_name = in.readString();
        trailer_key = in.readString();
        trailer_site = in.readString();
        trailer_size = in.readInt();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trailer_id);
        dest.writeString(trailer_key);
        dest.writeString(trailer_name);
        dest.writeString(trailer_site);
        dest.writeInt(trailer_size);

    }
}
