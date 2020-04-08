package com.example.movies;

import android.net.Uri;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieFetch {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String METHOD_MOVIE_POPULAR = "popular";
    private static final String METHOD_MOVIE_TOP_RATED = "top_rated";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_PAGE = "page";
    private static final String API_KEY = "176076deb5406d179176f89c529e357e";
    private static final String LANGUAGE = "en-US";

    public static URL buildURL(int method, int pageNumber){
        String qMethod;
        switch (method){
            case 0:
                qMethod = METHOD_MOVIE_POPULAR;
                break;
            case 1:
                qMethod = METHOD_MOVIE_TOP_RATED;
                break;
            default:
                qMethod = METHOD_MOVIE_POPULAR;
                break;
        }
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(qMethod)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, Integer.toString(pageNumber))
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getHTTpResponse(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext())
                return scanner.next();
            else
                return null;
        } finally {
            connection.disconnect();
        }
    }
    private void parseMovies (String httpResponse, List MovieList) throws JSONException {
        JSONObject jsonObject = new JSONObject(httpResponse);
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        for(int i=0; i<jsonArray.length(); i++ ){
            JSONObject jsonMovie = jsonArray.getJSONObject(i);
            String title = jsonMovie.getString("title");
            int ID = jsonMovie.getInt("id");
            String posterPath = jsonMovie.getString("poster_path");
            String overview = jsonMovie.getString("overview");
            String releaseDate = jsonMovie.getString("release_date");
            int voteCount = jsonMovie.getInt("vote_count");
            float voteAverage = (float) jsonMovie.getDouble("vote_average");

            String backdropPath = jsonMovie.getString("backdrop_path");

            Movies movies = new Movies(title, ID, posterPath, overview, releaseDate, voteCount, voteAverage, backdropPath);
            MovieList.add(movies);
        }
    }

    public List<Movies> fetchMovies(int method, int pageNumber){
        List<Movies> movies = new ArrayList<>();
        try {
            String httpResponse = getHTTpResponse(buildURL(method, pageNumber));
            parseMovies(httpResponse, movies);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
