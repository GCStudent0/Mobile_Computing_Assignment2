package com.example.mobile_computing_assignment2.model;

public class MovieItemModel {
    //Model for basic details of each movie in the list of the MovieSearchModel
        private String Title;
        private String Year;
        private String imdbID;
        private String type;
        private String Poster;

//Getters and setters
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }
}
