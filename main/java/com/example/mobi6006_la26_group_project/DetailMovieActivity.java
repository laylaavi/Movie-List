package com.example.mobi6006_la26_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobi6006_la26_group_project.Object.Movie;

public class DetailMovieActivity extends AppCompatActivity {
    TextView tvTitle, tvGenre, tvRating, tvRuntime, tvReleased, tvDirector, tvWriter, tvActor, tvProduction, tvDescription;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvTitle = findViewById(R.id.tv_MovieTitle);
        tvGenre = findViewById(R.id.tv_MovieGenre);
        tvRating = findViewById(R.id.tv_MovieRating);
        tvRuntime = findViewById(R.id.tv_MovieRuntime);
        tvReleased = findViewById(R.id.tv_MovieReleased);
        tvDirector = findViewById(R.id.tv_MovieDirector);
        tvWriter = findViewById(R.id.tv_MovieWriter);
        tvActor = findViewById(R.id.tv_MovieActor);
        tvProduction = findViewById(R.id.tv_MovieProduction);
        tvDescription = findViewById(R.id.tv_MoviePlot);
        ivPoster = findViewById(R.id.iv_MoviePoster);

        Movie movie = HomeActivity.movieArrayList.get(getIntent().getIntExtra("position", 0));

        tvTitle.setText(movie.getTitle());
        tvGenre.setText(movie.getGenre());
        tvRating.setText("Rating: " + movie.getRating());
        tvRuntime.setText("Runtime: " + movie.getRuntime());
        tvReleased.setText("Released: " + movie.getReleased());
        tvDirector.setText("Director: " + movie.getDirector());
        tvWriter.setText("Writer: " + movie.getWriter());
        tvActor.setText("Actor: " + movie.getActor());
        tvProduction.setText("Production: " + movie.getProduction());
        tvDescription.setText("Short Description:\n" + movie.getDescription());
        Glide.with(this)
                .load(movie.getPoster())
                .thumbnail(0.5f)
                .into(ivPoster);
    }
}