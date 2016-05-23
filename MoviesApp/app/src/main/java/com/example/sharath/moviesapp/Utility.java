package com.example.sharath.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.sharath.moviesapp.data.MovieContract;

/**
 * Created by sharath on 23/5/16.
 */
public class Utility  {
    public static int movieindb(Context context, String id) {
        final String LOG_TAG = Utility.class.getSimpleName();
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   // projection
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", // selection
                new String[] { id },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();

        //Log.v(LOG_TAG, "saldnfkasjdgfnasljg asdjgjlhkjadghajksghkjdsafghkajsgh ljkasdf" + cursor.getCount());
        cursor.close();
        return numRows;
    }
}
