package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie{
    public FavouriteMovie(int autoId, int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backdropPath, double voteAvg, String releaseDate) {
        super(autoId, id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAvg, releaseDate);
    }

    @Ignore
    public FavouriteMovie (Movie movie) {
        super(movie.getAutoId(), movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getBackdropPath(), movie.getVoteAvg(), movie.getReleaseDate());
    }
}
