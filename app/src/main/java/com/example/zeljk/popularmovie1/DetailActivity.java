package com.example.zeljk.popularmovie1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zeljk.popularmovie1.model.Movie;
import com.squareup.picasso.Picasso;



public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView movieTitle = findViewById(R.id.movie_title);
        ImageView moviePhoto = findViewById(R.id.movie_photo);
        TextView movieOverview = findViewById(R.id.movie_overview_text);
        TextView movieVote = findViewById(R.id.movie_vote);
        TextView movieReleaseDate = findViewById(R.id.movie_release_date);


        Movie parcelable = getIntent().getParcelableExtra("data");
        String title = parcelable.getTitle();
        String posterPath = parcelable.getPosterPath();
        String overview = parcelable.getOverview();
        double voteAverage = parcelable.getVoteAverage();
        String releaseDate = parcelable.getReleaseDate();


        movieTitle.setText(title);
        String path = MovieAdapter.URL_MOVIE_IMAGE + posterPath;
        Picasso.with(this)
                .load(path)
                .placeholder(R.drawable.movie_shape)
                .error(R.drawable.ic_movie_black_24dp)
                .into(moviePhoto);
        movieOverview.setText(overview);
        movieVote.setText(String.valueOf(voteAverage));
        movieReleaseDate.setText(releaseDate);

    }


}

