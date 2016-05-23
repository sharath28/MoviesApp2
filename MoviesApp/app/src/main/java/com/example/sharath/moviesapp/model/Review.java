package com.example.sharath.moviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sharath on 16/5/16.
 */
public class Review implements Parcelable {
    public String review_id;
    public String review_content;
    public String review_author;

    public Review(String review_id, String review_author, String review_content) {
        this.review_id = review_id;
        this.review_author = review_author;
        this.review_content = review_content;
    }


    protected Review(Parcel in) {
        review_id = in.readString();
        review_content = in.readString();
        review_author = in.readString();
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
        dest.writeString(review_id);
        dest.writeString(review_author);
        dest.writeString(review_content);

    }
}
