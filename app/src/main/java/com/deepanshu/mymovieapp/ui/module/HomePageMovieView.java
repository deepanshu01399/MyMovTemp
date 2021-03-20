package com.deepanshu.mymovieapp.ui.module;

public class HomePageMovieView {
    private int thumnailImages;
    private int movieProgress;
    private String vedioUrl;


    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl;
    }

    public HomePageMovieView(int thumnailImages, int movieProgress, String vedioUrl) {
        this.thumnailImages = thumnailImages;
        this.movieProgress = movieProgress;
        this.vedioUrl = vedioUrl;
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

}

