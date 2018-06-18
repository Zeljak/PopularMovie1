package com.example.zeljk.popularmovie1.utils;
/**
 * Created by zeljk on 02/04/2018.
 */


import android.util.Log;

import com.example.zeljk.popularmovie1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Return an object by parsing out information about movie
 */
public class MovieJsonUtils {

    private final static String MOVIE_RESULTS = "results";
    private final static String MOVIE_ID = "id";
    private final static String MOVIE_TITLE = "title";
    private final static String MOVIE_POSTER_PATH = "poster_path";
    private final static String MOVIE_OVERVIEW = "overview";
    private final static String MOVIE_RELEASE_DATE = "release_date";
    private final static String MOVIE_VOTE_AVERAGE = "vote_average";



    public static Movie[] extractMoviesFromJson(String movieJSON) throws JSONException {

        //movieJSON = movieJSON.replace("\\/", "");
        Log.e("", "json: " + movieJSON);
        JSONObject jsonMovieString = new JSONObject(movieJSON);
        JSONArray jsonArray = jsonMovieString.getJSONArray(MOVIE_RESULTS);
        Movie[] movies = new Movie[jsonArray.length()];
        /*Create an empty ArrayList where we can add new movies to*/
       /*After successful parsing add a new movie to the list
         */
        for (int i = 0; i < jsonArray.length(); i++) {

            /*Get the JSON object representing the movie*/
            JSONObject jsonMOvie = jsonArray.optJSONObject(i);
            int id = jsonMOvie.optInt(MOVIE_ID);
            String title = jsonMOvie.optString(MOVIE_TITLE);
            Log.e("Hello", "poster path: " + jsonMOvie.optString(MOVIE_POSTER_PATH));
            String posterPath = jsonMOvie.optString(MOVIE_POSTER_PATH);
            String overview = jsonMOvie.optString(MOVIE_OVERVIEW);
            String releaseDate = jsonMOvie.optString(MOVIE_RELEASE_DATE);
            double voteAverage = jsonMOvie.optDouble(MOVIE_VOTE_AVERAGE);

            movies[i] = new Movie(id, title, posterPath, overview, releaseDate, voteAverage);
        }

        return movies;
    }
}

