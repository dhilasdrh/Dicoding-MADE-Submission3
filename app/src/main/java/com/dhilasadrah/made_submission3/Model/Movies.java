package com.dhilasadrah.made_submission3.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movies implements Parcelable {
    private int movieId;
    private String movieTitle, releaseDate, overview, poster, backdrop;
    private double voteAverage;

    public Movies(JSONObject object) {
        try {
            Integer movieId = object.getInt("id");
            String movieTitle = object.getString("title");
            String releaseDate = object.getString("release_date");
            String overview = object.getString("overview");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");
            double voteAverage = object.getDouble("vote_average");

            this.movieId = movieId;
            this.poster = poster;
            this.backdrop = backdrop;
            this.movieTitle = movieTitle;
            this.releaseDate = releaseDate;
            this.overview = overview;
            this.voteAverage = voteAverage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return movieTitle;
    }

    public void setTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieId);
        dest.writeString(this.movieTitle);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
        dest.writeDouble(this.voteAverage);
    }

    protected Movies(Parcel in) {
        this.movieId = in.readInt();
        this.movieTitle = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
