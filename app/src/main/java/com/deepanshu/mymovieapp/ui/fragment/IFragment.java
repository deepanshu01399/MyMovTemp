package com.deepanshu.mymovieapp.ui.fragment;

import android.os.Bundle;

public interface IFragment {
    public void showSnackBarMessage(String message);
    public void showProgress();
    public void hideProgress();
    public void openHomeFromBaseFragment(Bundle bundle);
    void hideToolbarNext();
    void showToolbarNext();
    void manageToolbar();
    public void updateToolbarBackButtonText(String backTextValue);
    public void updateToolbarNextButtonText(String nextTextValue);
    public void showSnackbarInfo(String message);
    void onFragmentBackPress();

}
