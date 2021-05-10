package com.deepanshu.mymovieapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.FragmentChangeListener;
import com.deepanshu.mymovieapp.ui.fragment.BaseFragment;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MusicPlayListActvity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public MusicPlayListActvity() {
    }

    private void getBundleData() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {

        }

    }

    @Override
    public String getHeaderTitle() {
        return "Local MovieDetail";
    }

    @Override
    public int getLayoutByID() {
        return R.layout.moviedetail_activity;
    }

    @Override
    public void getViewById() {
        getBundleData();
        initMovieHeaderView();


        checkPermission();
        setonClickListner();
        //setRecentlyRecylerView();

        //dummyAudioScrolling();
        //dummyBrightNessScrolling();


    }

    private void updateMovieDetailinfo( HomePageMovieView homePageMovieView) {
        //txtVedioDesc.setText(homePageMovieView.getVedioUrl());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        }
        return super.onTouchEvent(event);
    }



    private void setonClickListner() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }



    private void initMovieHeaderView() {

    }


    private void animate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (detaillayout != null)
//                TransitionManager.beginDelayedTransition(detaillayout); // this line for expanding view
       }
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(MusicPlayListActvity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MusicPlayListActvity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(MusicPlayListActvity.this, "Please allow storage permission", Toast.LENGTH_LONG).show();
            } else {
            }
        }
    }



    @Override
    public void onBackPressed() {
        int orientation = MusicPlayListActvity.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }

               super.onBackPressed();

    }
    @Override
    public void hideToolBarnextValue() {

    }

    @Override
    public void updateToolBarNextValue(String nextValue) {

    }

    @Override
    public void updateToolBarBackValue(String backTxtValue) {

    }

    @Override
    public void hadleToolBarNextValue(TextView textView) {

    }

    @Override
    public void handleToolBarBackValue(TextView textView) {

    }


    @Override
    public void onNetworkChangeStatus(boolean networkStatus, String msg) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressbar() {

    }

    @Override
    public void hideToolbarNext() {

    }

    @Override
    public void showToolbarNext() {

    }

    @Override
    public void manageToolBar() {

    }

    @Override
    public void onNetworkChange(boolean networkStatus, String msg) {

    }

    @Override
    public void replaceFragment(BaseFragment fragment, FragmentManager fragmentManager, boolean isAddedToBackStack) {

    }

    @Override
    public void addFragmentWithoutReplace(BaseFragment fragment, FragmentManager fragmentManager, boolean isAddedToBackStack) {

    }

    @Override
    public void updateActiveFragment(String headername) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


}