package com.deepanshu.mymovieapp.ui.module;

import java.util.ArrayList;
import java.util.List;

public class HomePageGrpMovieView {
    private ArrayList<HomePageMovieView> homePageMovieViews;
    private String homePageMovieHeaderTitle;
    private String showMoreText;

    public HomePageGrpMovieView(ArrayList<HomePageMovieView> homePageMovieViews, String homePageMovieHeaderTitle, String showMoreText) {
        this.homePageMovieViews = homePageMovieViews;
        this.homePageMovieHeaderTitle = homePageMovieHeaderTitle;
        this.showMoreText = showMoreText;
    }

    public List<HomePageMovieView> getHomePageMovieViews() {
        return homePageMovieViews;
    }

    public void setHomePageMovieViews(ArrayList<HomePageMovieView> homePageMovieViews) {
        this.homePageMovieViews = homePageMovieViews;
    }

    public String getHomePageMovieHeaderTitle() {
        return homePageMovieHeaderTitle;
    }

    public void setHomePageMovieHeaderTitle(String homePageMovieHeaderTitle) {
        this.homePageMovieHeaderTitle = homePageMovieHeaderTitle;
    }

    public String getShowMoreText() {
        return showMoreText;
    }

    public void setShowMoreText(String showMoreText) {
        this.showMoreText = showMoreText;
    }
}

