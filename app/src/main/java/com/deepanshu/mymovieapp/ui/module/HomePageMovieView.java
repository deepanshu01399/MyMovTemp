package com.deepanshu.mymovieapp.ui.module;

public class HomePageMovieView {
    int images;
    int movieProgress;

    public HomePageMovieView(int images, int movieProgress) {
        this.images = images;
        this.movieProgress = movieProgress;

    }

    public int getMovieProgress() {
        return movieProgress;
    }

    public void setMovieProgress(int movieProgress) {
        this.movieProgress = movieProgress;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

}

