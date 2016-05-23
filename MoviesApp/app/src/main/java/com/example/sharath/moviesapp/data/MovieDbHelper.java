package com.example.sharath.moviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sharath.moviesapp.data.MovieContract.MovieEntry;
import com.example.sharath.moviesapp.model.Movie;

/**
 * Created by sharath on 23/5/16.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "movie.db";
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerEntry.TABLE_NAME + " (" +
//                TrailerEntry._ID + " INTEGER PRIMARY KEY," +
//                TrailerEntry.COLUMN_TRAILER_NAME + " TEXT, " +
//                TrailerEntry.COLUMN_TRAILER_KEY + " TEXT UNIQUE NOT NULL, " +
//                TrailerEntry.COLUMN_TRAILER_SITE + " TEXT, " +
//                TrailerEntry.COLUMN_TRAILER_SIZE + " TEXT " +
//                " );";
//
//
//        db.execSQL(SQL_CREATE_TRAILER_TABLE);

//        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
//                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                MovieEntry.COLUMN_MOVIE_ID + " TEXT UNIQUE NOT NULL " +
//                MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" +
//                MovieEntry.COLUMN_IMAGE + " TEXT NOT NULL " +
//                MovieEntry.COLUMN_RATING + " REAL NOT NULL " +
//                MovieEntry.COLUMN_DATE + " TEXT NOT NULL " +
//                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL );";
//
//        db.execSQL(SQL_CREATE_MOVIE_TABLE);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
//        onCreate(db);
//    }


            final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                    MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    MovieEntry.COLUMN_IMAGE + " TEXT, " +
                    MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                    MovieEntry.COLUMN_RATING + " REAL, " +
                    MovieEntry.COLUMN_DATE + " TEXT);";

            db.execSQL(SQL_CREATE_MOVIE_TABLE);
        }

        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
            onCreate(db);
        }

}
