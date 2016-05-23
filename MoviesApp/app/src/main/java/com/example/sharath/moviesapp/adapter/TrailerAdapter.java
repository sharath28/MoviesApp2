package com.example.sharath.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharath.moviesapp.R;
import com.example.sharath.moviesapp.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sharath on 22/5/16.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> {
    private Context context;
    private LayoutInflater inflater;
    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        super(context, R.layout.movie_trailer,trailers);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Trailer trailer = (Trailer) getItem(position);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.movie_trailer, parent, false);
        }
        if (trailer != null)
        {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_trailer_image);
            String movietrailerimg= "http://img.youtube.com/vi/" + trailer.trailer_key + "/0.jpg";
            Picasso
                    .with(context)
                    .load(movietrailerimg)
                    .into(imageView);

            TextView textview = (TextView) convertView.findViewById(R.id.movie_trailer_name);
            textview.setText(trailer.trailer_name);
        }
        return convertView;
    }
}
