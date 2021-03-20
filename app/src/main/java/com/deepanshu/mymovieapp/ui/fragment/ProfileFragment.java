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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


public  class ProfileFragment extends  BaseFragment implements View.OnClickListener {
    public LayoutInflater mInflater;
    private static final String TAG = "ProfileFragment";
    private PlayerView  playerView;
    private ProgressBar progressBar;
    private ImageView fullScreen;
    private SimpleExoPlayer simpleExoPlayer;
    private Boolean flag = false;



    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.profile_fragment,container,false);
        initView(view);
        setOnCLickListner();
        return  view;
    }

    private void setOnCLickListner() {
    }

    private void initView(View view) {
        playerView = view.findViewById(R.id.player_view);
        progressBar = view.findViewById(R.id.progress_bar);
        fullScreen = view.findViewById(R.id.bt_fullScreen);
        fullScreen.setOnClickListener(this);

        //todo make window full screen
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //video url
        Uri  videoUrl = Uri.parse("https://imgur.com/7bMqysJ.mp4");
        //initalise load control
        LoadControl loadControl = new DefaultLoadControl();
        //initialise Band width
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //intialise the track selector
        TrackSelector  trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        //initialsie simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
        //Initialsie dataa   source factory
        DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        //initalise extractors factory
        ExtractorsFactory extractorsFactory  = new DefaultExtractorsFactory();
        //initalise  media source
        MediaSource mediaSource = new ExtractorMediaSource(videoUrl,defaultHttpDataSourceFactory,extractorsFactory,null,null);
        //set player
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        //playvedio when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //check conditon
                if(playbackState==Player.STATE_BUFFERING){
                    //when buffering show progressbar
                    progressBar.setVisibility(View.VISIBLE);
                }else if(playbackState== Player.STATE_READY){
                    //when video ready hide progressbar
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

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
            case R.id.bt_fullScreen:
                //check conditons:
                if(flag){
                    //when flag is true --> set orientation or enter to full screen else in half screen
                    ((MainDashBoardActivity)getActivity()).visibleHideBottomNavigation(flag);
                    fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                }
                else{
                    ((MainDashBoardActivity)getActivity()).visibleHideBottomNavigation(flag);
                    fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);//stop vedio when ready
        simpleExoPlayer.getPlaybackState();//get playback state
    }

    @Override
    public void onResume() {
        super.onResume();
        //video play when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }
}