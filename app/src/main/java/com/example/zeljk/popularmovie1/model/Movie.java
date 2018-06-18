package com.example.zeljk.popularmovie1.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zeljk on 21/03/2018.
 */

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private int mId;
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private String mReleaseDate;
    private double mVoteAverage;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate, double voteAverage) {

        this.mId = id;
        this.mTitle = title;
        this.mPosterPath = posterPath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mVoteAverage = voteAverage;

    }

    private Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readDouble();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeDouble(mVoteAverage);

    }


}
