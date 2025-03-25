package com.example.mobile_computing_assignment2.model;

import java.util.List;

public class MovieSearchModel {
    //Model for the list response containing the individual movies
    private List<MovieItemModel> Search;

    //Getters and setters
    public List<MovieItemModel> getSearch() {
        return Search;
    }

    public void setSearch(List<MovieItemModel> search) {
        Search = search;
    }
}