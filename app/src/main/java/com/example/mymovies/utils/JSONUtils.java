package com.example.mymovies.utils;

import com.example.mymovies.data.Movie;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_RESULTS = "results";
    //вся информация о фильме
    private static final String KEY_ID = "id";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    //для отзывов
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";
    //для видео
    private static final String BASE_VIDEO_URL = "https://www.youtube.com/watch?v=";
    private static final String KEY_KEY_OF_VIDEO = "key";
    private static final String KEY_NAME = "name";
    //постеры
    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE = "w185";
    private static final String BIG_POSTER_SIZE = "w780";

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        if (jsonObject == null) {
            return movieArrayList;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                int id = jsonArray.getJSONObject(i).getInt(KEY_ID);
                int voteCount = jsonArray.getJSONObject(i).getInt(KEY_VOTE_COUNT);
                String title = jsonArray.getJSONObject(i).getString(KEY_TITLE);
                String originalTitle = jsonArray.getJSONObject(i).getString(KEY_ORIGINAL_TITLE);
                String overview = jsonArray.getJSONObject(i).getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + jsonArray.getJSONObject(i).getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + jsonArray.getJSONObject(i).getString(KEY_POSTER_PATH);
                String backdropPath = jsonArray.getJSONObject(i).getString(KEY_BACKDROP_PATH);
                double voteAvg = jsonArray.getJSONObject(i).getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = jsonArray.getJSONObject(i).getString(KEY_RELEASE_DATE);
                movieArrayList.add(new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAvg, releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieArrayList;
    }

    public static Movie getMovieDetailFromJSON(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        try {
            int id = jsonObject.getInt(KEY_ID);
            int voteCount = jsonObject.getInt(KEY_VOTE_COUNT);
            String title = jsonObject.getString(KEY_TITLE);
            String originalTitle = jsonObject.getString(KEY_ORIGINAL_TITLE);
            String overview = jsonObject.getString(KEY_OVERVIEW);
            String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + jsonObject.getString(KEY_POSTER_PATH);
            String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + jsonObject.getString(KEY_POSTER_PATH);
            String backdropPath = jsonObject.getString(KEY_BACKDROP_PATH);
            double voteAvg = jsonObject.getDouble(KEY_VOTE_AVERAGE);
            String releaseDate = jsonObject.getString(KEY_RELEASE_DATE);
            return new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAvg, releaseDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Trailer> getVideosFromJSON(JSONObject jsonObject) {
        ArrayList<Trailer> trailerArrayList = new ArrayList<>();
        if (jsonObject == null) {
            return trailerArrayList;
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                String key = BASE_VIDEO_URL + jsonArray.getJSONObject(i).getString(KEY_KEY_OF_VIDEO);
                String name = jsonArray.getJSONObject(i).getString(KEY_NAME);
                trailerArrayList.add(new Trailer(key, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerArrayList;
    }

    public static ArrayList<Review> getReviewsFromJSON(JSONObject jsonObject) {
        ArrayList<Review> reviewArrayList = new ArrayList<>();
        if (jsonObject == null) {
            return reviewArrayList;
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                String author = jsonArray.getJSONObject(i).getString(KEY_AUTHOR);
                String content = jsonArray.getJSONObject(i).getString(KEY_CONTENT);
                reviewArrayList.add(new Review(author, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewArrayList;
    }
}
