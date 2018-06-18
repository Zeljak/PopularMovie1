package com.example.zeljk.popularmovie1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zeljk.popularmovie1.model.Movie;
import com.example.zeljk.popularmovie1.utils.MovieJsonUtils;
import com.example.zeljk.popularmovie1.utils.MovieUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String API_KEY = "your_api_key_here";

    private static final String URL_POPULAR_MOVIE = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    private static final String URL_HIGHEST_RATED_MOVIE = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
    private static MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //Using findViewById, got a reference to RecyclerView form xml. This allows us to
        //set the adapter of the RecyclerView and toggle the visibility.
        //
        mRecyclerView = findViewById(R.id.recyclerview_movies);



        /*This TextView is used to display errors and will be hidden if there are no errors.*/
        mErrorMessageDisplay = findViewById(R.id.error_message_display);

        /*GridLayoutManager will display movies in the main layout with their corresponding movie poster thumbnails*/
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        /*This setting is used to improve performance.*/
        mRecyclerView.setHasFixedSize(true);
        /*The MovieAdapter is responsible for linking our movie data with the Views.*/
        mMovieAdapter = new MovieAdapter(this);
        /*Setting the adapter attaches it to the RecyclerView in layout*/
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = findViewById(R.id.loading_indicator);


            /*One all views are setup, we can load the movie data. */
        loadMovies();
    }


    /*This method will allow user˙s preferred criteria: most popular or highest rated movies.*/
    private void loadMovies() {
        showMoviesDataView();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = sharedPreferences.getString(getString(R.string.popular_movies), getString(R.string.top_rated_movies));
        String urlToExecute = "";
        if (sortOrder.equals(getString(R.string.popular_movies))) {
            urlToExecute = URL_POPULAR_MOVIE;
        } else if (sortOrder.equals(getString((R.string.top_rated_movies)))) {
            urlToExecute = URL_HIGHEST_RATED_MOVIE;
        }
        new FetchMovieTask().execute(urlToExecute);


    }

    /**
     * This method will make the View for the movie data visible and hide the error message.
     */
    private void showMoviesDataView() {
        /*First, the error is invisible*/
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /*Then, the movie data is visible.*/
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the View for the movie data.
     */
    private void showErrorMessage(String string) {
        /*First, we are going to hide the currently visible data.*/
        mRecyclerView.setVisibility(View.INVISIBLE);
        /*Then, we are showing the error.*/
        mErrorMessageDisplay.setText(string);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method is overridden by MainActivity class in order to handle RecyclerView item clicks.
     */
    @Override
    public void onClick(int id, String title, String posterPath, String overview, String releaseDate, double voteAverage) {
        Context context = this;
        Movie parcelable = new Movie(id, title, posterPath, overview, releaseDate, voteAverage);
        Intent intentToStartDetailActivity = new Intent(context, DetailActivity.class);
        intentToStartDetailActivity.putExtra("data", parcelable);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        FetchMovieTask fetchMovieTask;
        switch (selectedItem) {
            case R.id.popular_movie:
                fetchMovieTask = new FetchMovieTask();
                fetchMovieTask.execute(URL_POPULAR_MOVIE);
                break;
            case R.id.top_rated_movie:
                fetchMovieTask = new FetchMovieTask();
                fetchMovieTask.execute(URL_HIGHEST_RATED_MOVIE);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);

        }


        @Override
        protected Movie[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String urlString = params[0];


            try {
                URL movieRequestUrl = new URL(urlString);
                String jsonMovieResponse = MovieUtils.getResponseFromHttpRequest(movieRequestUrl);

                return MovieJsonUtils.extractMoviesFromJson(jsonMovieResponse);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("URLException", "MalformedURLException");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("AsyncTask", "Error in AsyncTask");
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONAsync", "Problem in JSON");
                return null;
            }
        }


        @Override
        protected void onPostExecute(Movie[] movies) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviesDataView();
                mMovieAdapter.setMovieData(movies);
            } else {
                showErrorMessage(getString(R.string.error));
            }

        }
    }
}


