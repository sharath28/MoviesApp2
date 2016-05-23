package com.example.sharath.moviesapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.sharath.moviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATE = "date";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

//        }

    /**
     * Created by sharath on 22/5/16.
     */
//    public static final class MovieEntry implements BaseColumns {
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
//
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
//
//        public static final String TABLE_NAME = "movie";
//
//        public static final String COLUMN_MOVIE_ID = "movie_id";
//        public static final String COLUMN_POSTER_IMAGE = "poster_image";
//        public static final String COLUMN_TITLE = "movie_title";
//        public static final String COLUMN_RELEASE_DATE = "release_date";
//        public static final String COLUMN_RATING = "movie_rating";
//        public static final String COLUMN_OVERVIEW = "movie_overview";
//        public static final String COLUMN_TRAILER_ID = "trailer_id";
//        public static final String COLUMN_REVIEW_ID = "review_id";
//
//        public static Uri buildMovieUri(long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//    }
//
//        public static final class TrailerEntry implements BaseColumns {
//            public static final String TABLE_NAME = "trailer";
//
//            public static final String COLUMN_TRAILER_NAME = "trailer_name";
//            public static final String COLUMN_TRAILER_KEY = "trailer_key";
//            public static final String COLUMN_TRAILER_SIZE = "trailer_size";
//            public static final String COLUMN_TRAILER_SITE = "trailer_site";
//        }
//
//        public static final class ReviewEntry implements BaseColumns {
//            public static final String TABLE_NAME = "review";
//
//            public static final String COLUMN_REVIEW_CONTENT = "review_content";
//            public static final String COLUMN_REVIEW_AUTHOR = "review_author";

}

