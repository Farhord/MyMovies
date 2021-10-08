package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.adapters.ReviewAdapter;
import com.example.mymovies.adapters.TrailerAdapter;
import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.data.MovieDatabase;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;
    private ImageView imageViewFavouriteStar;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private ScrollView scrollViewLandscape;

    private MovieDatabase database;
    private MainViewModel viewModel;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private int movieId;
    private Movie movie;
    private FavouriteMovie favouriteMovie;
    private String lang;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        lang = Locale.getDefault().getLanguage();
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewFavouriteStar = findViewById(R.id.imageViewFavouriteStar);
        scrollViewLandscape = findViewById(R.id.scrollViewLandscape);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        trailerAdapter = new TrailerAdapter();
        reviewAdapter = new ReviewAdapter();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            movieId = intent.getIntExtra("id", -1);
        } else finish();
        getMovie(movieId, lang);
        scrollViewLandscape.smoothScrollTo(0,0);
    }

    private void getMovie(int movieId, String lang) {
        JSONObject jsonObjectMovie = NetworkUtils.getJSONForMovie(movieId, lang);
        if (jsonObjectMovie == null) {
            movie = viewModel.getMovieById(movieId);
        }else movie = JSONUtils.getMovieDetailFromJSON(jsonObjectMovie);
        Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.internetlost).into(imageViewPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewRating.setText(Double.toString(movie.getVoteAvg()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewDescription.setText(movie.getOverview());
        checkFavourite();
        getTrailerAndReviewData();
        setOnTrailerClickListener();
    }

    public void onClickAddToFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, R.string.remove_from_favourite, Toast.LENGTH_SHORT).show();
        }
        checkFavourite();
    }

    private void checkFavourite() {
        favouriteMovie = viewModel.getFavouriteMovieById(movieId);
        if (favouriteMovie == null) {
            imageViewFavouriteStar.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
        } else
            imageViewFavouriteStar.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
    }

    private void getTrailerAndReviewData() {
        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        JSONObject jsonObjectTrailers = NetworkUtils.getJSONForVideos(movieId, lang);
        JSONObject jsonObjectReviews = NetworkUtils.getJSONForReviews(movieId);
        ArrayList<Trailer> trailerArrayList = JSONUtils.getVideosFromJSON(jsonObjectTrailers);
        ArrayList<Review> reviewArrayList = JSONUtils.getReviewsFromJSON(jsonObjectReviews);
        trailerAdapter.setTrailerList(trailerArrayList);
        reviewAdapter.setReviewList(reviewArrayList);
    }

    private void setOnTrailerClickListener() {
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}