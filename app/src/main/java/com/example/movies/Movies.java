package com.example.movies;

import android.icu.text.CaseMap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movies implements Parcelable {

    String mTitle, mPosterPath, mOverview, mReleaseDate, mBackdropPath;
    int mId, mVoteCount;
    float mAvg;

    public Movies(String title, int Id, String posterPath, String Overview, String releaseDate, int VoteCount, float avg, String backdropPath){
        mTitle = title;
        mId = Id;
        mPosterPath = posterPath;
        mOverview = Overview;
        mReleaseDate = releaseDate;
        mVoteCount = VoteCount;
        mAvg = avg;
        mBackdropPath = backdropPath;
    }

    public Movies(Parcel parcel){
        mTitle = parcel.readString();
        mId = parcel.readInt();
        mPosterPath = parcel.readString();
        mOverview = parcel.readString();
        mReleaseDate = parcel.readString();
        mVoteCount = parcel.readInt();
        mAvg = parcel.readFloat();
        mBackdropPath = parcel.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mID) {
        this.mId = mID;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate){
        this.mReleaseDate = mReleaseDate;
    }

    public int getVoteCount(){
        return mVoteCount;
    }

    public void setVoteCount(int mVoteCount){
        this.mVoteCount = mVoteCount;
    }

    public float getAvg(){
        return mAvg;
    }

    public void setAvg(float mAvg){
        this.mAvg = mAvg;
    }

    public String getBackdropPath(){
        return mBackdropPath;
    }

    public void setBackdropPath(String mBackdropPath){
        this.mBackdropPath = mBackdropPath;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: "+mTitle
                + "\nID: " + mId
                + "\nPoster path: " + mPosterPath
                + "\nOverview: " + mOverview
                + "\nRelease date: " + mReleaseDate
                + "\nVote count: " + mVoteCount
                + "\nVote average: " + mAvg
                + "\nBackdrop path: " + mBackdropPath + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(mId);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeFloat(mAvg);
        dest.writeInt(mVoteCount);
        dest.writeString(mBackdropPath);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
