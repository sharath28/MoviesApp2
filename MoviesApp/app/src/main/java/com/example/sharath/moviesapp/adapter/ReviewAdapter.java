package com.example.sharath.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharath.moviesapp.R;
import com.example.sharath.moviesapp.model.Review;

import java.util.ArrayList;

/**
 * Created by sharath on 22/5/16.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {
    private Context context;
    private LayoutInflater inflater;
    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        super(context, R.layout.movie_review,reviews);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Review review = (Review) getItem(position);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.movie_review, parent, false);
        }
        if (review != null)
        {
            TextView textview = (TextView) convertView.findViewById(R.id.movie_review_text);
            textview.setText(review.review_content);

            TextView textview1 = (TextView) convertView.findViewById(R.id.movie_review_author);
            textview1.setText(review.review_author);
        }
        return convertView;
    }
}
