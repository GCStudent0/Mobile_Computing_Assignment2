package com.example.mobile_computing_assignment2.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_computing_assignment2.model.MovieItemModel;
import com.example.mobile_computing_assignment2.model.SelectedMovieModel;
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

public class SelectedViewModel extends ViewModel {

    // mutableLiveData for movie details
    private final MutableLiveData<SelectedMovieModel> movieDetailsLiveData = new MutableLiveData<>();

    // expose data so it can be observed in selectedMovieActivity
    public LiveData<SelectedMovieModel> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }

    public void getMovieDetails(String movieTitle) {
        //test hard coded url
        //String urlString = "https://www.omdbapi.com/?apikey=67ec043e&plot=full&t=A%20Clockwork%20Orange";

        //url with movie title appended
        String urlString = "https://www.omdbapi.com/?apikey=67ec043e&plot=full&t=" + movieTitle;

        //Log.d("Check url", "URL: " + urlString);

        ApiClient.get(urlString, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("Test", "SelectedViewModel API response failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //store response if api request fails
                assert response.body() != null;
                //store the response
                String responseData = response.body().string();
                //parse out response to a json
                try {
                    JSONObject responseDataJson = new JSONObject(responseData);

                    //instantiate the selected movie model
                    SelectedMovieModel movieDetails = new SelectedMovieModel();

                    //set the values of the model with json data
                    movieDetails.setTitle(responseDataJson.getString("Title"));
                    movieDetails.setYear(responseDataJson.getString("Year"));
                    movieDetails.setRated(responseDataJson.getString("Rated"));
                    movieDetails.setGenre(responseDataJson.getString("Genre"));
                    movieDetails.setDirector(responseDataJson.getString("Director"));
                    movieDetails.setWriter(responseDataJson.getString("Writer"));
                    movieDetails.setActors(responseDataJson.getString("Actors"));
                    movieDetails.setPlot(responseDataJson.getString("Plot"));
                    movieDetails.setPoster(responseDataJson.getString("Poster"));

                    // post value to livedata so the UI observes it
                    movieDetailsLiveData.postValue(movieDetails);

                } catch (JSONException e) {
                    Log.e("Test", "Failed to parse out movies", e);
                }
            }
        });
    }
}
