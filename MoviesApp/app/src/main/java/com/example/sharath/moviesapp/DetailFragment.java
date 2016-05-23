package com.example.sharath.moviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharath.moviesapp.data.MovieContract;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.sharath.moviesapp.adapter.ReviewAdapter;
import com.example.sharath.moviesapp.adapter.TrailerAdapter;
import com.example.sharath.moviesapp.model.Movie;
import com.example.sharath.moviesapp.model.Review;
import com.example.sharath.moviesapp.model.Trailer;
import java.util.ArrayList;

import static com.example.sharath.moviesapp.R.layout.fragment_detail;

/**
 * Created by sharath on 21/5/16.
 */
public class DetailFragment extends Fragment {
    private ReviewAdapter reviewadapter;
    private TrailerAdapter traileradapter;
    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    public static final String ITEM_ID = "item_id";

    public static final String TAG = DetailFragment.class.getSimpleName();

    static final String DETAIL_MOVIE = "DETAIL_MOVIE";

    public DetailFragment() {
    }
    private Movie movie;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (movie != null) {
            inflater.inflate(R.menu.menu_detail, menu);
            final MenuItem favicon = menu.findItem(R.id.action_fav);
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    return Utility.movieindb(getActivity(), movie.movie_id);
                }

                @Override
                protected void onPostExecute(Integer isfav) {
                    if (isfav == 0) {
                        favicon.setIcon(R.drawable.favorite_add_icon);
                        Toast.makeText(getActivity(), "Add to fav", Toast.LENGTH_SHORT).show();
                    } else if (isfav == 1) {
                        favicon.setIcon(R.drawable.favorite_remove_icon);
                        Toast.makeText(getActivity(), "Already Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.action_fav) {
            Log.v(LOG_TAG, "Added to fav");
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    return Utility.movieindb(getActivity(), movie.movie_id);
                }

                @Override
                protected void onPostExecute(Integer isfav) {
                    if (isfav == 0) {
                        new AsyncTask<Void, Void, Uri>() {
                            @Override
                            protected Uri doInBackground(Void... params) {
                                ContentValues values = new ContentValues();

                                values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.movie_id);
                                values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.title);
                                values.put(MovieContract.MovieEntry.COLUMN_IMAGE, movie.poster_image);
                                values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.overview);
                                values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.rating);
                                values.put(MovieContract.MovieEntry.COLUMN_DATE, movie.release_date);

                                return getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,
                                        values);
                            }

                            @Override
                            protected void onPostExecute(Uri returnUri) {
                                item.setIcon(R.drawable.favorite_remove_icon);
                                Toast.makeText(getActivity(), "Added To favorite", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();
                    } else if (isfav == 1) {
                        new AsyncTask<Void, Void, Integer>() {
                            @Override
                            protected Integer doInBackground(Void... params) {
                                return getActivity().getContentResolver().delete(
                                        MovieContract.MovieEntry.CONTENT_URI,
                                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                                        new String[]{movie.movie_id}
                                );
                            }

                            @Override
                            protected void onPostExecute(Integer rowsDeleted) {
                                item.setIcon(R.drawable.favorite_add_icon);
                                Toast.makeText(getActivity(), "Deleted From favorite", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();
                    }
                }
            }.execute();

        }
        else if (id == R.id.action_settings)
        {
            Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(movie!=null) {
            FetchMoviesReviews moviesReviewsTask = new FetchMoviesReviews();
            moviesReviewsTask.execute(movie.movie_id);

            FetchMoviesTrailers moviesTrailersTask = new FetchMoviesTrailers();
            moviesTrailersTask.execute(movie.movie_id);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Context context = getActivity();
        View rootView = inflater.inflate(fragment_detail, container, false);
        Bundle arguments = getArguments();
        reviewadapter = new ReviewAdapter(context, new ArrayList<Review>());

        // Get a reference to the GridView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.list_movie_reviews);
        listView.setAdapter(reviewadapter);

        traileradapter = new TrailerAdapter(context, new ArrayList<Trailer>());

        // Get a reference to the GridView, and attach this adapter to it.
        GridView trailergridView = (GridView) rootView.findViewById(R.id.list_movie_trailers);
        trailergridView.setAdapter(traileradapter);

        trailergridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trailer trailer = traileradapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.trailer_key));
                startActivity(intent);
            }

//        @Override
//        public void onItemClick(ListView trailerlistView, View view,
//        int position, long id) {
//            Trailer trailer = trailerAdapter.getItem(position);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
//            startActivity(intent);
//        }
        });

        if(arguments != null) {
            Log.v(LOG_TAG, "Details Page");
//            movie = arguments.getParcelable("key");
            movie = arguments.getParcelable(DetailFragment.DETAIL_MOVIE);

            if (movie != null) {
                ((TextView) rootView.findViewById(R.id.movie_title))
                        .setText(movie.title);
                ImageView iconView = (ImageView) rootView.findViewById(R.id.movie_poster);
                Picasso.with(getContext()).load(movie.poster_image).into(iconView);
                ((TextView) rootView.findViewById(R.id.movie_rating_text))
                        .setText("Rating:" + (float) movie.rating + "/ 10");
                ((RatingBar) rootView.findViewById(R.id.movie_rating)).setRating((float) movie.rating);
                ((TextView) rootView.findViewById(R.id.movie_overview_text))
                        .setText("Over View");
                ((TextView) rootView.findViewById(R.id.movie_overview))
                        .setText(movie.overview);
                ((TextView) rootView.findViewById(R.id.movie_release_date_text))
                        .setText("Release Date");
                ((TextView) rootView.findViewById(R.id.movie_release_date))
                        .setText(movie.release_date);
                ((TextView) rootView.findViewById(R.id.movie_review_text))
                        .setText("Review");
                ((TextView) rootView.findViewById(R.id.movie_trailer_text))
                        .setText("Trailer");
            }
        }
        return rootView;
    }

    public class FetchMoviesReviews extends AsyncTask<String, Void, Review[]>
    {
        @Override
        public Review[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String api_key = "08eff0c57313416b04009b2ccf767b8d";
            // Will contain the raw JSON response as a string.
            // Toast.makeText(getActivity(),"break1", Toast.LENGTH_SHORT).show();
            String moviesreviewsJsonStr = null;
            String baseUrl = null;
            try {
                // Construct the URL for the Moviesdb query
                baseUrl = "http://api.themoviedb.org/3/movie/"+params[0]+"/reviews?api_key="+api_key;
                URL url = new URL(baseUrl);

                // Create the request to Moviesdb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.v(LOG_TAG, "Input stream null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    Log.v(LOG_TAG, "Input Length zero");
                    return null;
                }
                moviesreviewsJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies Review Json String" + moviesreviewsJsonStr);
                //Toast.makeText(getActivity(),moviesreviewsJsonStr,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMoviesReviewFromJson(moviesreviewsJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(Review[] reviews) {
//                Toast.makeText(getActivity(),"break2", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(), reviews[0].review_content, Toast.LENGTH_SHORT).show();
            if (reviews != null) {
                reviewadapter.clear();
//                Log.v(LOG_TAG,"Result url string"+result);
                for (Review review : reviews) {
                    if (review != null) {
                        Log.v(LOG_TAG, "POST EXECUTE IMAGE URLS" + review);
                        reviewadapter.add(review);
                    }
                }
                reviewadapter.notifyDataSetChanged();

            }
        }

        private Review[] getMoviesReviewFromJson(String moviesreviewsJsonStr)
                throws JSONException {
            final String OWM_RESULT = "results";
            JSONObject MoviesJson = new JSONObject(moviesreviewsJsonStr);
            JSONArray moviesreviewsArray = MoviesJson.getJSONArray(OWM_RESULT);
            Log.v(LOG_TAG, "Final Reviews array" + moviesreviewsArray);
            Review[] reviewresults = new Review[10];

            for (int i = 0; i < moviesreviewsArray.length(); i++) {
                JSONObject singlereview = moviesreviewsArray.getJSONObject(i);
                String review_id = singlereview.getString("id");
                String review_author = singlereview.getString("author");
                String review_content = singlereview.getString("content");
                reviewresults[i] = new Review(review_id,review_author,review_content);
                Log.v(LOG_TAG, "Review" + reviewresults[i].review_content);
            }
            Log.v(LOG_TAG, "Final Reviews" + reviewresults);
            return reviewresults;
        }

    }

    public class FetchMoviesTrailers extends AsyncTask<String, Void, Trailer[]>
    {
        @Override
        public Trailer[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String api_key = "08eff0c57313416b04009b2ccf767b8d";
            // Will contain the raw JSON response as a string.
            // Toast.makeText(getActivity(),"break1", Toast.LENGTH_SHORT).show();
            String moviesTrailersJsonStr = null;
            String baseUrl = null;
            try {
                // Construct the URL for the Moviesdb query
                baseUrl = "http://api.themoviedb.org/3/movie/"+params[0]+"/videos?api_key="+api_key;
                URL url = new URL(baseUrl);

                // Create the request to Moviesdb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.v(LOG_TAG, "Input stream null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    Log.v(LOG_TAG, "Input Length zero");
                    return null;
                }
                moviesTrailersJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies Trailer Json String" + moviesTrailersJsonStr);
                //Toast.makeText(getActivity(),moviesreviewsJsonStr,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMoviesTrailerFromJson(moviesTrailersJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onPostExecute(Trailer[] trailers) {
            if (trailers != null) {
                traileradapter.clear();
                for (Trailer trailer : trailers) {
                    if (trailer != null) {
                        Log.v(LOG_TAG, "POST EXECUTE TRAILERS" + trailer);
                        traileradapter.add(trailer);
                    }
                }
                traileradapter.notifyDataSetChanged();
            }

        }

        private Trailer[] getMoviesTrailerFromJson(String moviesTrailersJsonStr)
                throws JSONException {
            final String OWM_RESULT = "results";
            JSONObject MoviesJson = new JSONObject(moviesTrailersJsonStr);
            JSONArray moviestrailersArray = MoviesJson.getJSONArray(OWM_RESULT);
            Log.v(LOG_TAG, "Final Trailers array" + moviestrailersArray);
            Trailer[] trailerresults = new Trailer[10];

            for (int i = 0; i < moviestrailersArray.length(); i++) {
                JSONObject singletrailer = moviestrailersArray.getJSONObject(i);
                String trailer_id = singletrailer.getString("id");
                String trailer_name = singletrailer.getString("name");
                String trailer_key = singletrailer.getString("key");
                String trailer_site = singletrailer.getString("site");
                int trailer_size = singletrailer.getInt("size");
                trailerresults[i] = new Trailer(trailer_id,trailer_key,trailer_name,trailer_site,trailer_size);
                Log.v(LOG_TAG, "Trailer key" + trailerresults[i].trailer_key);
            }
            Log.v(LOG_TAG, "Final Trailers" + trailerresults);
            return trailerresults;
        }
    }
}