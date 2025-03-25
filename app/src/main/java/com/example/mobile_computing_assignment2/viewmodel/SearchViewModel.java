package com.example.mobile_computing_assignment2.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_computing_assignment2.model.MovieItemModel;
import com.example.mobile_computing_assignment2.util.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchViewModel extends ViewModel {
     // mutableLiveData for the of list of movies
    private final MutableLiveData<List<MovieItemModel>> movieListLiveData = new MutableLiveData<>();

    // expose data so it can be observed in SearchMovieActivity
    public LiveData<List<MovieItemModel>> getMovieListLiveData() {
        return movieListLiveData;
    }

    //function to search using api call
    public void Search(String searchText) {
        //test hard coded url
        String urlString = "https://www.omdbapi.com/?apikey=67ec043e&type=movie&s="+ searchText.trim();
        Log.d("Test", "The created URL is " + urlString);


        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("API Failure", "API request failed: " + e.getMessage(), e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               //stored response of failed api request
                assert response.body() != null;
                //server response
                String responseData = response.body().string();
                //test to see if response is received
                Log.d("Test", "Response Code: " + response.code());
                Log.d("Test", "Raw Response: " + response.toString());

                if (response.body() == null) {
                    Log.e("Test", "Response body is null");
                    return;
                }


                //parse out response to a json
                try {

                    JSONObject json = new JSONObject(responseData);
                    JSONArray arrayOfMovies = json.getJSONArray("Search");
                    if (!json.has("Search")) {
                        Log.e("Test", "No movies found in json");
                        return;
                    }

                    //create list of movies
                    List<MovieItemModel> movieList = new ArrayList<>();

                    //loop through array of movies and add each to the list
                    for (int i = 0; i < arrayOfMovies.length(); i++) {
                        JSONObject movieJson = arrayOfMovies.getJSONObject(i);

                        MovieItemModel movie = new MovieItemModel();
                        movie.setTitle(movieJson.getString("Title"));
                        movie.setYear(movieJson.getString("Year"));
                        movie.setPoster(movieJson.getString("Poster"));

                        movieList.add(movie);
                    }

                    // post value to LiveData so UI observes it
                    movieListLiveData.postValue(movieList);

                } catch (JSONException e) {
                    Log.e("Test", "Failed to parse out movies", e);
                }
            }
        });
    }
}
