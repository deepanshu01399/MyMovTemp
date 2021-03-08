package com.deepanshu.mymovieapp.interfaces;

import android.widget.TextView;

public interface IEssentialFeatures {
    String getHeaderTitle();
    void setHeaderTitle(String headerTitle);
    int getLayoutByID();
    void getViewById();
    void hideToolBarnextValue();
    void updateToolBarNextValue(String nextValue);
    void updateToolBarBackValue(String backTxtValue);
    void hadleToolBarNextValue(TextView textView);
    void handleToolBarBackValue(TextView textView);
    void onNetworkChangeStatus(boolean networkStatus, String msg);
    void showSnackBarMessage(String message);
    void showSnackbarAlert(String message);
    void showSnackbarInfo(String message);
    void showProgressBar();
    void hideProgressbar();
    void hideToolbarNext();
    void showToolbarNext();
    void manageToolBar();

    void onNetworkChange(boolean networkStatus, String msg);


}
