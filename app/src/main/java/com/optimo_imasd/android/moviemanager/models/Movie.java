package com.optimo_imasd.android.moviemanager.models;

import java.io.Serializable;

/**
 * Created by Jose Maria on 14/03/2017.
 */

public class Movie implements Serializable {


    String id;
    String title;
    String overview;
    float voteAverage;
    float voteCount;
    String posterPath;
    String backdropPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(float voteCount) {
        this.voteCount = voteCount;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
       // return String.format("https://image.tmdb.org/t/p/w342/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", posterPath);

       // return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {


        return String.format("https://image.tmdb.org/t/p/w780%s",backdropPath);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }



    public Movie(String id, String title, String overview, float voteAverage, float voteCount, String posterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        //this.posterPath = posterPath;
        this.posterPath=posterPath;
        this.backdropPath = backdropPath;
    }
}
