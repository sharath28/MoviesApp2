package com.example.sharath.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.sharath.moviesapp.model.Movie;

import com.example.sharath.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sharath on 8/3/16.
 */
public class ImageListAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private LayoutInflater inflater;

    private ArrayList<String> imageUrls;

    public ImageListAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.grid_image,movies);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_image, parent, false);
        }

        Picasso
                .with(context)
                .load(movie.poster_image)
                .into((ImageView) convertView);

        return convertView;
    }
}