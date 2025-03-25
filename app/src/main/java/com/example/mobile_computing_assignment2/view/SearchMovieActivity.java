package com.example.mobile_computing_assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mobile_computing_assignment2.ReceyclerView.ItemClickListener;
import com.example.mobile_computing_assignment2.ReceyclerView.MyAdapter;
import com.example.mobile_computing_assignment2.databinding.MovieSearchBinding;
import com.example.mobile_computing_assignment2.model.MovieItemModel;
import com.example.mobile_computing_assignment2.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieActivity extends AppCompatActivity implements ItemClickListener {

    //declare instance of adapter
    MyAdapter myAdapter;

    //Do following onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up viewbinding
        MovieSearchBinding binding = MovieSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get search view model instance
        SearchViewModel viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // initialize the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        //create list for movies
        List<MovieItemModel> movieList = new ArrayList<>();

        //set up adapter and bind to recycler view
        myAdapter = new MyAdapter(getApplicationContext(), movieList);
        binding.recyclerView.setAdapter(myAdapter);

        // observe the live data to update the adapter with new data and refresh the UI
        viewModel.getMovieListLiveData().observe(this, movies -> {
            if (movies != null) {
                myAdapter.updateData(movies);
            }

        });


        //Get text from search bar to get movie list
        binding.searchButton.setOnClickListener(viewSearch -> {
            String searchText = binding.movieSearchBar.getText().toString().trim();
            if (!searchText.isEmpty()) {
                viewModel.Search(searchText);
            } else {
                Toast.makeText(this, "Search field is empty", Toast.LENGTH_SHORT).show();
            }
        });

        //Have adapter listen for clicks and pass back to activity
        myAdapter.setClickListener(this);
    }

    //use the implemented itemclick interface to handle item click events
    @Override
    public void onClick(View v, int pos) {
        //once data is received, display a toast and start the SelectedMovieActivity
        Toast.makeText(this, "You Choose: " + myAdapter.movieItemModelArrayList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();

        //Start the activity and pass intent with movie title when item is clicked
        Intent intent = new Intent(SearchMovieActivity.this, SelectedMovieActivity.class);
        intent.putExtra("KEY_ONE",myAdapter.movieItemModelArrayList.get(pos).getTitle());

        //start the activity and pass intent with movie title
        startActivity(intent);
    }
}