package com.example.mobile_computing_assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mobile_computing_assignment2.R;
import com.example.mobile_computing_assignment2.databinding.MovieSelectedBinding;
import com.example.mobile_computing_assignment2.model.SelectedMovieModel;
import com.example.mobile_computing_assignment2.viewmodel.SelectedViewModel;

public class SelectedMovieActivity extends AppCompatActivity {


    // perform following during oncreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up binding
       MovieSelectedBinding binding = MovieSelectedBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

        // receive the selected movies title from SearchMovieActivity intent
        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra("KEY_ONE");

        // test to see if movie title is received
        //Toast.makeText(this, "From Intent: " + movieTitle, Toast.LENGTH_SHORT).show();

        // initialize the view model
        SelectedViewModel selectedViewModel = new ViewModelProvider(this).get(SelectedViewModel.class);

        // observe live data for movie details
        selectedViewModel.getMovieDetailsLiveData().observe(this, movieDetails -> {
            binding.movieTitle.setText(String.format("Title: %s", movieDetails.getTitle()));
            binding.movieYear.setText(String.format("Year: %s", movieDetails.getYear()));
            binding.movieRated.setText(String.format("Rated: %s", movieDetails.getRated()));
            binding.movieGenre.setText(String.format("Genre: %s", movieDetails.getGenre()));
            binding.movieDirector.setText(String.format("Director: %s", movieDetails.getDirector()));
            binding.movieWriters.setText(String.format("Writers: %s", movieDetails.getWriter()));
            binding.movieActors.setText(String.format("Actors: %s", movieDetails.getActors()));
            binding.moviePlot.setText(String.format("Plot: %s", movieDetails.getPlot()));

            // Load poster using Glide lib
            Glide.with(this)
                    .load(movieDetails.getPoster())
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.posterView);

        });

        // trigger api call to get movie details
            selectedViewModel.getMovieDetails(movieTitle);


        //button with onclick to return to search page
        binding.returnButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(SelectedMovieActivity.this, SearchMovieActivity.class);
            startActivity(intent1);
        });
        }

}
