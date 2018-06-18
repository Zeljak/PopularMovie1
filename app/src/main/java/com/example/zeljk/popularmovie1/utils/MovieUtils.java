package com.example.zeljk.popularmovie1.utils;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by zeljk on 21/03/2018.
 */

/**
 * These utilities will be used to communicate with the network.
 */
public class MovieUtils {


    /**
     * This method returns the entire result from the HTTP response.
     */

    public static String getResponseFromHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
public static String buildPosterUrl(String posterPath){
        return "http://image.tmdb.org/t/p/w500/" + posterPath;
}

}






