package com.deepanshu.mymovieapp.ui.fragment;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.ui.activity.MainDashBoardActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


public  class DownloadFragment extends  BaseFragment implements View.OnClickListener {

    public DownloadFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //public LayoutInflater mInflater = LayoutInflater.from(getActivity()); // 1
        mInflater = getActivity().getLayoutInflater();
        //View theInflatedView = inflater.inflate(R.layout.your_layout, null); // 2 and 3


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_base, container, false);
        View view = inflater.inflate(R.layout.main_download_fragment,container,false);
        initView(view);
        setOnCLickListner();
        return  view;
    }

    private void setOnCLickListner() {
    }

    private void initView(View view) {

    }



    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    //used to replace the frament... from one fragemnt to another
    private void replaceFragment(BaseFragment fragment, FragmentManager fragmentManager, boolean isAddedToBackStack) {
        /*  fragmentManager.addOnBackStackChangedListener(this);*/
        FragmentTransaction fragTrans = fragmentManager.beginTransaction();
        fragTrans.replace(R.id.fragment_container, fragment, fragment.toString());
        if (isAddedToBackStack)
            fragTrans.addToBackStack(fragment.toString());
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();
        fragmentManager.executePendingTransactions();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

          }
    }

    @Override
    public void onPause() {
        super.onPause();
   }

    @Override
    public void onResume() {
        super.onResume();
    }
}