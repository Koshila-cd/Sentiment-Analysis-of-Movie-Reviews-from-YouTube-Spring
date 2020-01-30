package com.example.movie.entity;

import com.google.api.client.util.DateTime;

public class MoviesVO {

    private String movieName;

    private String trailerUrl;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Override
    public String toString() {
        return "MoviesVO{" +
                ", movieName='" + movieName + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                '}';
    }
}
