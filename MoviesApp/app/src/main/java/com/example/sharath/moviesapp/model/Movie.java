package com.example.sharath.moviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by sharath on 20/3/16.
 */
public class Movie implements Parcelable {
    public String movie_id;
    public String poster_image;
    public String title;
    public String release_date;
    public double rating;
    public String overview;

    public Movie(String movie_id, String poster_image, String title, String release_date, double rating, String overview) {
        this.movie_id = movie_id;
        this.poster_image = poster_image;
        this.title = title;
        this.release_date = release_date;
        this.rating = rating;
        this.overview = overview;
    }

    public Movie(Parcel in) {
        movie_id = in.readString();
        poster_image = in.readString();
        title = in.readString();
        release_date = in.readString();
        rating = in.readDouble();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movie_id);
        dest.writeString(poster_image);
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeDouble(rating);
        dest.writeString(overview);
    }
}
