package com.deepanshu.mymovieapp.ui.module;

import android.os.Parcel;
import android.os.Parcelable;

public class HomePageMovieView implements Parcelable {
    private int thumnailImages;
    private int movieProgress;
    private String vedioUrl;
    private String movieName;


    protected HomePageMovieView(Parcel in) {
        thumnailImages = in.readInt();
        movieProgress = in.readInt();
        vedioUrl = in.readString();
        movieName = in.readString();
    }

    public static final Creator<HomePageMovieView> CREATOR = new Creator<HomePageMovieView>() {
        @Override
        public HomePageMovieView createFromParcel(Parcel in) {
            return new HomePageMovieView(in);
        }

        @Override
        public HomePageMovieView[] newArray(int size) {
            return new HomePageMovieView[size];
        }
    };

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public HomePageMovieView(String movieName,int thumnailImages, int movieProgress, String vedioUrl) {
        this.movieName = movieName;
        this.thumnailImages = thumnailImages;
        this.movieProgress = movieProgress;
        this.vedioUrl = vedioUrl;
    }

    public int getThumnailImages() {
        return thumnailImages;
    }

    public void setThumnailImages(int thumnailImages) {
        this.thumnailImages = thumnailImages;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieProgress() {
        return movieProgress;
    }

    public void setMovieProgress(int movieProgress) {
        this.movieProgress = movieProgress;
    }

    public int getThumbNailImages() {
        return thumnailImages;
    }

    public void setThumbNamilImages(int images) {
        this.thumnailImages = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(thumnailImages);
        parcel.writeInt(movieProgress);
        parcel.writeString(vedioUrl);
        parcel.writeString(movieName);
    }
}

