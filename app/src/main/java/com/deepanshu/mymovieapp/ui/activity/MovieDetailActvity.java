package com.deepanshu.mymovieapp.ui.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.adapter.CommanMovieAdapter;
import com.deepanshu.mymovieapp.interfaces.FragmentChangeListener;
import com.deepanshu.mymovieapp.ui.fragment.BaseFragment;
import com.deepanshu.mymovieapp.ui.module.CommentModule;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.deepanshu.mymovieapp.ui.module.RecyclerViewOverScrollDecorAdapter;
import com.deepanshu.mymovieapp.util.StaticUtil;
import com.google.android.exoplayer2.C;
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
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;

import static com.deepanshu.mymovieapp.util.StaticUtil.getScreenHeight;
import static com.deepanshu.mymovieapp.util.StaticUtil.getScreenWidth;

public class MovieDetailActvity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextView.OnEditorActionListener {
    //private ImageView thumnailImage;
    private TextView txtVideoTitle, txtVedioDesc;
    private CheckBox txtShowMore;
    private RecyclerView recyclerRelatedVedio;
    private TextView txtCommentText;
    private ArrayList<HomePageMovieView> arrayList = new ArrayList<>();
    private LinearLayout includelistviewlayout, brightness_slider_container, brightness_center_text, volume_slider_container, vol_center_text;
    private RelativeLayout RRdescription, detaillayout, videolayoutBtns, videoHeaderRelLayout, unlock_panel, root;
    private int imageurl = 0, movieProgress = 0, moviePosition = 0;
    private String movieUrl, movieTitle;
    private static String TAG = "MovieDetailFrag";
    private String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20210228-WA0011.mp4";
    private Button btnWatchNow, download, btnMyList, btnBuyNow;
    private static final String STATE_COMMENT_LIST = "comments";
    private ArrayList<CommentModule> commentModuleList;
    public LayoutInflater mInflater;
    private PlayerView playerView;
    private ProgressBar progressBar;
    private ImageView fullScreen;
    private SimpleExoPlayer simpleExoPlayer;
    private Boolean wantsTOExit = false;
    private Boolean currentlyOnLandscapeMode = false;
    private RelativeLayout collapsingToolbarlayout;
    private int uiImmersiveOptions;
    private HomePageMovieView homePageMovieViewDetailData;
    int lastWindowIndex = 0; // global var in your class encapsulating exoplayer obj (Activity, etc.)
    private View exo_pause;
    private TextView exo_title, brigtness_perc_center_text, vol_perc_center_text;
    private AudioManager audioManager;
    private SeekBar volumeSeekBar, brightNessSeekbar;
    private ImageView brightnessIcon, brightness_image, volIcon, vol_image, lockExo;
    private ProgressBar brightness_slider, volume_slider;
    private int screenWidth, screenHeight;
    private boolean immersiveMode, intLeft, intRight, intTop, intBottom, finLeft, finRight, finTop, finBottom;
    private long diffX, diffY;
    private double seekSpeed = 0;
    private int calculatedTime;
    private String seekDur;
    private Boolean tested_ok = false, allThingsRegForFirstTime = false;
    private Boolean screen_swipe_move = false;
    private int methodcallNO = 0;
    private float baseX, baseY;

    public enum ControlsMode {
        LOCK, FULLCONTORLS
    }

    private static final int MIN_DISTANCE = 150;
    private ContentResolver cResolver;
    private Window window;
    private int brightness, mediavolume, device_height;

    private ControlsMode controlsState;
    private Boolean wantsToLockExoPlayer = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public MovieDetailActvity() {
    }

    private void getBundleData() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
           /* imageurl = intent.getExtras().getInt("imageurl");
            movieUrl = intent.getStringExtra("movieUrl");
            movieProgress = intent.getExtras().getInt("movieProgress");
           */
            //to get data from a  parceble intent
            homePageMovieViewDetailData = intent.getParcelableExtra("detailedCLickedMovie");
            imageurl = homePageMovieViewDetailData.getThumbNailImages();
            movieUrl = homePageMovieViewDetailData.getVedioUrl();
            movieProgress = homePageMovieViewDetailData.getMovieProgress();
            movieTitle = homePageMovieViewDetailData.getMovieName();
            moviePosition = intent.getExtras().getInt("movielistPosition");
        }

    }

    @Override
    public String getHeaderTitle() {
        return "MovieDetail";
    }

    @Override
    public int getLayoutByID() {
        return R.layout.moviedetail_activity;
    }

    @Override
    public void getViewById() {
        getBundleData();
        initMovieHeaderView();
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        fullScreen = findViewById(R.id.bt_fullScreen);
        fullScreen.setOnClickListener(this);
        //thumnailImage = findViewById(R.id.thumnailImage);
        txtVideoTitle = findViewById(R.id.txtVideoTitle);
        txtShowMore = findViewById(R.id.txtShowMore);
        txtVedioDesc = findViewById(R.id.txtVedioDesc);
        txtCommentText = findViewById(R.id.txtCommentText);
        recyclerRelatedVedio = findViewById(R.id.recycler_relatedVedio);
        includelistviewlayout = findViewById(R.id.includelistviewlayout);
        RRdescription = findViewById(R.id.RRdescription);
        detaillayout = findViewById(R.id.detaillayout);
        btnWatchNow = findViewById(R.id.btnWatchNow);
        download = findViewById(R.id.download);
        btnMyList = findViewById(R.id.btnMyList);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        collapsingToolbarlayout = findViewById(R.id.collapsingToolbarlayout);
        exo_title = findViewById(R.id.exo_title);
        videoHeaderRelLayout = findViewById(R.id.videoHeaderRelLayout);
        brightness_slider_container = findViewById(R.id.brightness_slider_container);
        brightnessIcon = findViewById(R.id.brightnessIcon);
        brightness_slider = findViewById(R.id.brightness_slider);
        brightness_center_text = findViewById(R.id.brightness_center_text);
        brightness_image = findViewById(R.id.brightness_image);
        brigtness_perc_center_text = findViewById(R.id.brigtness_perc_center_text);

        volume_slider_container = findViewById(R.id.volume_slider_container);
        volIcon = findViewById(R.id.volIcon);
        volume_slider = findViewById(R.id.volume_slider);
        vol_center_text = findViewById(R.id.vol_center_text);
        vol_image = findViewById(R.id.vol_image);
        vol_perc_center_text = findViewById(R.id.vol_perc_center_text);
        unlock_panel = findViewById(R.id.unlock_panel);
        root = findViewById(R.id.root);
        lockExo = findViewById(R.id.lockExo);

        screenWidth = getScreenWidth(this);
        screenHeight = getScreenHeight(this);

        //thumnailImage.setImageResource(imageurl);
        txtVideoTitle.setText(movieTitle);
        exo_title.setText(movieTitle);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volume_slider.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volume_slider.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));


        checkPermission();
        setonClickListner();
        setRecentlyRecylerView();

        //dummyAudioScrolling();
        //dummyBrightNessScrolling();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://when user press the finger on touch screen
                //actionDownTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE://when user moves its finger
                screen_swipe_move = true;
                if (!allThingsRegForFirstTime && methodcallNO == 0) {
                    methodcallNO = 1;
                    actionDownTouchEvent(event);
                }
                if (controlsState == ControlsMode.FULLCONTORLS) {
                    root.setVisibility(View.GONE);
                    diffX = (long) (Math.ceil(event.getX() - baseX));
                    diffY = (long) Math.ceil(event.getY() - baseY);
                    double brightnessSpeed = 0.05;
                    if (Math.abs(diffY) > MIN_DISTANCE) {
                        tested_ok = true;
                    }
                    if (Math.abs(diffY) > Math.abs(diffX)) {
                        if (intLeft) {
                            brightness_slider_container.setVisibility(View.VISIBLE);
                            brightness_center_text.setVisibility(View.VISIBLE);
                            volume_slider_container.setVisibility(View.GONE);
                            vol_center_text.setVisibility(View.GONE);
                            cResolver = getContentResolver();
                            window = getWindow();
                            try {
                                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                                brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
                            } catch (Settings.SettingNotFoundException e) {
                                e.printStackTrace();
                            }
                            int new_brightness = (int) (brightness - (diffY * brightnessSpeed));
                            //Log.e(TAG, "NewBrightness" + new_brightness);
                            if (new_brightness > 250) {
                                new_brightness = 250;
                            } else if (new_brightness < 1) {
                                new_brightness = 1;
                            }
                            double brightPerc = Math.ceil((((double) new_brightness / (double) 250) * (double) 100));
                            brightness_slider.setProgress((int) brightPerc);
                            if (brightPerc < 30) {
                                brightnessIcon.setImageResource(R.drawable.ic_brightness_min);
                                brightness_image.setImageResource(R.drawable.ic_brightness_min);
                            } else if (brightPerc > 30 && brightPerc < 80) {
                                brightnessIcon.setImageResource(R.drawable.brightness_medium);
                                brightness_image.setImageResource(R.drawable.brightness_medium);
                            } else if (brightPerc > 80) {
                                brightnessIcon.setImageResource(R.drawable.ic_brightness_high);
                                brightness_image.setImageResource(R.drawable.ic_brightness_high);
                            }
                            brigtness_perc_center_text.setText(" " + (int) brightPerc);
                            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (new_brightness));
                            WindowManager.LayoutParams layoutpars = window.getAttributes();
                            layoutpars.screenBrightness = brightness / (float) 255;
                            window.setAttributes(layoutpars);
                        } else if (intRight) {
                            volume_slider_container.setVisibility(View.VISIBLE);
                            vol_center_text.setVisibility(View.VISIBLE);
                            brightness_slider_container.setVisibility(View.GONE);
                            brightness_center_text.setVisibility(View.GONE);
                            mediavolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                            int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                            device_height = getScreenHeight(this);
                            double cal = (double) diffY * ((double) maxVol / (double) (device_height * 4));
                            int newMediaVolume = mediavolume - (int) cal;
                            if (newMediaVolume > maxVol) {
                                newMediaVolume = maxVol;
                            } else if (newMediaVolume < 1) {
                                newMediaVolume = 0;
                            }
                            audioManager.setStreamVolume(simpleExoPlayer.getAudioStreamType(), newMediaVolume, 0);
                            //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newMediaVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                            double volPerc = Math.ceil((((double) newMediaVolume / (double) maxVol) * (double) 100));
                            //Log.e(TAG, "diffy:"+diffY+",maxvol:"+maxVol+",currentVol:" + mediavolume + ",cal:"+cal+",newMediaVol:" + newMediaVolume + ",VolPercentage:" + volPerc);
                            vol_perc_center_text.setText(" " + (int) volPerc);
                            volume_slider.setProgress((int) volPerc);
                            if (volPerc < 1) {
                                volIcon.setImageResource(R.drawable.vol_mute);
                                vol_image.setImageResource(R.drawable.vol_mute);
                                vol_perc_center_text.setVisibility(View.GONE);
                            } else if (volPerc >= 1) {
                                volIcon.setImageResource(R.drawable.volume_icon);
                                vol_image.setImageResource(R.drawable.volume_icon);
                                vol_perc_center_text.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > (MIN_DISTANCE + 100)) {
                            tested_ok = true;
                            //root.setVisibility(View.VISIBLE);
                            //brightness_center_text.setVisibility(View.VISIBLE);
                            //onlySeekbar.setVisibility(View.VISIBLE);
                            //top_controls.setVisibility(View.GONE);
                            //bottom_controls.setVisibility(View.GONE);
                            String totime = "";
                            calculatedTime = (int) ((diffX) * seekSpeed);
                            if (calculatedTime > 0) {
                                seekDur = String.format("[ +%02d:%02d ]",
                                        TimeUnit.MILLISECONDS.toMinutes(calculatedTime) -
                                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(calculatedTime)),
                                        TimeUnit.MILLISECONDS.toSeconds(calculatedTime) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(calculatedTime)));
                            } else if (calculatedTime < 0) {
                                seekDur = String.format("[ -%02d:%02d ]",
                                        Math.abs(TimeUnit.MILLISECONDS.toMinutes(calculatedTime) -
                                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(calculatedTime))),
                                        Math.abs(TimeUnit.MILLISECONDS.toSeconds(calculatedTime) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(calculatedTime))));
                            }
                            totime = String.format("%02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(simpleExoPlayer.getCurrentPosition() + (calculatedTime)) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(simpleExoPlayer.getCurrentPosition() + (calculatedTime))), // The change is in this line
                                    TimeUnit.MILLISECONDS.toSeconds(simpleExoPlayer.getCurrentPosition() + (calculatedTime)) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(simpleExoPlayer.getCurrentPosition() + (calculatedTime))));
                            //txt_seek_secs.setText(seekDur);
                            //txt_seek_currTime.setText(totime);
                            //seekBar.setProgress((int) (simpleExoPlayer.getCurrentPosition() + (calculatedTime)));
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                allThingsRegForFirstTime = false;
                methodcallNO = 0;
                screen_swipe_move = false;
                tested_ok = false;
                vol_perc_center_text.setVisibility(View.GONE);
                brightness_center_text.setVisibility(View.GONE);
                vol_center_text.setVisibility(View.GONE);
                brightness_slider_container.setVisibility(View.GONE);
                volume_slider_container.setVisibility(View.GONE);
                //onlySeekbar.setVisibility(View.VISIBLE);
                //top_controls.setVisibility(View.VISIBLE);
                //bottom_controls.setVisibility(View.VISIBLE);
                //root.setVisibility(View.VISIBLE);
                calculatedTime = (int) (simpleExoPlayer.getCurrentPosition() + (calculatedTime));
                //simpleExoPlayer.seekTo(calculatedTime);
                showControls();
                break;

        }
        return super.onTouchEvent(event);
    }

    private void actionDownTouchEvent(MotionEvent event) {
        allThingsRegForFirstTime = true;
        tested_ok = false;
        screenWidth = getScreenWidth(this);
        screenHeight = getScreenHeight(this);
        Log.e(TAG, "ScreenWidth:" + screenWidth);
        if (event.getX() < (screenWidth / 2)) {
            intLeft = true;
            intRight = false;
        } else if (event.getX() > (screenWidth / 2)) {
            intLeft = false;
            intRight = true;
        }
        int upperLimit = (screenHeight / 4) + 100;
        int lowerLimit = ((screenHeight / 4) * 3) - 150;
        if (event.getY() < upperLimit) {
            intBottom = false;
            intTop = true;
        } else if (event.getY() > lowerLimit) {
            intBottom = true;
            intTop = false;
        } else {
            intBottom = false;
            intTop = false;
        }
        Log.e(TAG, "left:" + intLeft + ",right:" + intRight + ",intTop:" + intTop + ",intBottom:" + intBottom + "no. of call:" + methodcallNO + "");
        methodcallNO++;
        seekSpeed = (TimeUnit.MILLISECONDS.toSeconds(simpleExoPlayer.getDuration()) * 0.1);
        diffX = 0;
        calculatedTime = 0;
        seekDur = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(diffX) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffX)),
                TimeUnit.MILLISECONDS.toSeconds(diffX) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diffX)));

        //TOUCH STARTED
        baseX = event.getX();
        baseY = event.getY();


    }

    private void hideAllControls() {
        if (controlsState == ControlsMode.FULLCONTORLS) {
            if (root.getVisibility() == View.VISIBLE) {
                root.setVisibility(View.GONE);
            }
        } else if (controlsState == ControlsMode.LOCK) {
            if (unlock_panel.getVisibility() == View.VISIBLE) {
                unlock_panel.setVisibility(View.GONE);
            }
        }
        MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(uiImmersiveOptions);
    }

    private void showControls() {
        if (controlsState == ControlsMode.FULLCONTORLS) {
            if (root.getVisibility() == View.GONE) {
                root.setVisibility(View.VISIBLE);
            }
        } else if (controlsState == ControlsMode.LOCK) {
            if (unlock_panel.getVisibility() == View.GONE) {
                unlock_panel.setVisibility(View.VISIBLE);
            }
        }
    }


    private void setonClickListner() {
        // thumnailImage.setOnClickListener(this);
        txtShowMore.setOnCheckedChangeListener(this);
        txtCommentText.setOnEditorActionListener(this);
        btnBuyNow.setOnClickListener(this);
        btnMyList.setOnClickListener(this);
        btnWatchNow.setOnClickListener(this);
        download.setOnClickListener(this);
        lockExo.setOnClickListener(this);
        videoHeaderRelLayout.setVisibility(View.GONE);
        //todo make window full screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //video url
        //Uri  videoUrl = Uri.parse("https://imgur.com/7bMqysJ.mp4");
        //initalise load control
        LoadControl loadControl = new DefaultLoadControl();
        //initialise Band width
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //intialise the track selector
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        //initialsie simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(MovieDetailActvity.this, trackSelector, loadControl);
        //Initialsie data source factory used for setting http url files
        DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        //initalise extractors factory
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //initalise  media source
        //MediaSource mediaSource = new ExtractorMediaSource(videoUrl,defaultHttpDataSourceFactory,extractorsFactory,null,null);
        //initalise Concatenating Media source
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        //Ensure to populate the allArrayVideo url .
        for (int i = 0; i < arrayList.size(); i++) {
            String videoUrlString = arrayList.get(i).getVedioUrl();
            Uri videoUrl = Uri.parse(videoUrlString);
            //initalise  media source
            MediaSource mediaSource = new ExtractorMediaSource(videoUrl, defaultHttpDataSourceFactory, extractorsFactory, null, null);
            //adding the mediasource to the concatenatemedia source
            concatenatingMediaSource.addMediaSource(mediaSource);
        }

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        //simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.prepare(concatenatingMediaSource);// simpleExoPlayer.prepare(mediaSource);//if we want to play a single video
        //Play from the item selected on the playlist from previous activity/fragment
        simpleExoPlayer.seekTo(0, C.TIME_UNSET);
        //playVedio when ready
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                //Toast.makeText(MovieDetailActvity.this, "onTimelineChanged", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                //Toast.makeText(MovieDetailActvity.this, "onTracksChanged", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                // Toast.makeText(MovieDetailActvity.this, "onLoadingChanged", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Toast.makeText(MovieDetailActvity.this, "onPlayerStateChanged", Toast.LENGTH_SHORT).show();
                //check conditon
                if (playbackState == Player.STATE_BUFFERING) {
                    //when buffering show progressbar
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    //when video ready hide progressbar
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Toast.makeText(MovieDetailActvity.this, "onRepeatModeChanged", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                Toast.makeText(MovieDetailActvity.this, "onShuffleModeEnabledChanged", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Toast.makeText(MovieDetailActvity.this, "onPlayerError", Toast.LENGTH_SHORT).show();
                simpleExoPlayer.stop();

            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                //Toast.makeText(MovieDetailActvity.this, "onPositionDiscontinuity", Toast.LENGTH_SHORT).show();
                int latestWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
                if (latestWindowIndex != lastWindowIndex) {
                    // item selected in playlist has changed, handle here
                    lastWindowIndex = latestWindowIndex;
                }

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        playerView.setControllerHideOnTouch(true);
       /* playerView.setOnTouchListener(new OnSwipeTouchListner(MovieDetailActvity.this) {
            public void onSwipeTop() {
                Toast.makeText(MovieDetailActvity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(MovieDetailActvity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(MovieDetailActvity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(MovieDetailActvity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (simpleExoPlayer != null)
            simpleExoPlayer.release();
    }

    @Override
    public void onBackStackChanged() {
        if (simpleExoPlayer != null)
            simpleExoPlayer.stop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fullScreen:
                checkAndRotateScreen();
                if (currentlyOnLandscapeMode) {
                    goesToPortrait();
                    currentlyOnLandscapeMode = false;
                } else {
                    goesToLandScape();
                    currentlyOnLandscapeMode = true;
                }
                break;

            case R.id.btnWatchNow:

                break;

            case R.id.btnMyList:
                break;

            case R.id.btnBuyNow:
                break;

            case R.id.download:

                break;

            case R.id.lockExo:
                if (wantsToLockExoPlayer) {
                    controlsState = ControlsMode.LOCK;
                    root.setVisibility(View.GONE);
                    lockExo.setImageResource(R.drawable.ic_un_lock);
                    wantsToLockExoPlayer = false;

                } else {
                    controlsState = ControlsMode.FULLCONTORLS;
                    root.setVisibility(View.VISIBLE);
                    lockExo.setImageResource(R.drawable.ic_baseline_lock);
                    //lockExo.setBackgroundColor(getResources().getColor(R.color.light_grey_text));
                    wantsToLockExoPlayer = true;

                }
                break;


        }

    }

    private void goesToLandScape() {
        fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
        videoHeaderRelLayout.setVisibility(View.VISIBLE);

        collapsingToolbarlayout.getLayoutParams().width = getScreenWidth(MovieDetailActvity.this);
        collapsingToolbarlayout.getLayoutParams().height = getScreenHeight(MovieDetailActvity.this);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        uiImmersiveOptions = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        //MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
        MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(uiImmersiveOptions);

    }

    private void goesToPortrait() {
        fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        simpleExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        videoHeaderRelLayout.setVisibility(View.GONE);

        collapsingToolbarlayout.getLayoutParams().width = getScreenWidth(MovieDetailActvity.this);
        collapsingToolbarlayout.getLayoutParams().height = getScreenHeight(MovieDetailActvity.this) / 3;
        // MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(uiImmersiveOptions);

    }


    public List<CommentModule> getCommentList() {
        commentModuleList = new ArrayList<CommentModule>();
        for (int i = 0; i < includelistviewlayout.getChildCount(); i++) {
            View view = includelistviewlayout.getChildAt(i);
            TextView txtComment = view.findViewById(R.id.txtCommentText1);
            TextView txtCommentTime = view.findViewById(R.id.textCommentTime);
            CommentModule commentModule = new CommentModule(txtComment.getText().toString(), txtCommentTime.getText().toString());
            commentModuleList.add(commentModule);
        }
        return commentModuleList;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
        switch (compoundButton.getId()) {
            case R.id.txtShowMore:
                if (ischecked) {
                    txtVedioDesc.setMaxLines(10);
                    txtShowMore.setText("Show Less >");
                    ViewGroup.LayoutParams params = txtVedioDesc.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    txtVedioDesc.setLayoutParams(params);
                } else {
                    txtVedioDesc.setMaxLines(2);
                    txtShowMore.setText("Show More >");
                }
                animate();
                break;
        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            controlsState = ControlsMode.FULLCONTORLS;
            goesToLandScape();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            controlsState = ControlsMode.LOCK;
            goesToPortrait();
        }
    }

    private void initMovieHeaderView() {
        arrayList.clear();
        if (homePageMovieViewDetailData != null) {
            arrayList.add(homePageMovieViewDetailData);
        }
        arrayList.add(new HomePageMovieView("Panchayat", R.drawable.edu1, 40, "https://imgur.com/7bMqysJ.mp4"));
        arrayList.add(new HomePageMovieView("Bunny-Rabit", R.drawable.edu4, 60, "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"));
        arrayList.add(new HomePageMovieView("Chiness", R.drawable.edu2, 20, "https://media.w3.org/2010/05/sintel/trailer.mp4"));
        arrayList.add(new HomePageMovieView("Natural-view", R.drawable.edu3, 30, "http://video19.ifeng.com/video07/2013/11/11/281708-102-007-1138.mp4"));
        arrayList.add(new HomePageMovieView("Chota-Bheem", R.drawable.edu4, 10, "https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4"));
        arrayList.add(new HomePageMovieView("Motu-patlu", R.drawable.edu5, 0, "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"));
        arrayList.add(new HomePageMovieView("JL-50", R.drawable.edu6, 100, "https://media.w3.org/2010/05/sintel/trailer.mp4"));
        arrayList.add(new HomePageMovieView("Bhahuballi", R.drawable.edu1, 70, "http://video19.ifeng.com/video07/2013/11/11/281708-102-007-1138.mp4"));
        arrayList.add(new HomePageMovieView("Godzilla v/s Kong ", R.drawable.edu2, 40, "https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4"));

    }


    private void animate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (detaillayout != null)
                TransitionManager.beginDelayedTransition(detaillayout); // this line for expanding view
        }
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(MovieDetailActvity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MovieDetailActvity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(MovieDetailActvity.this, "Please allow storage permission", Toast.LENGTH_LONG).show();
            } else {
            }
        }
    }

    public void setCommentData(ArrayList<CommentModule> commentModuleList) {
        for (CommentModule commentModule : commentModuleList) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View commentView = layoutInflater.inflate(R.layout.comment_layout, null);
            TextView txtCommentText1 = commentView.findViewById(R.id.txtCommentText1);
            final TextClock textClock = commentView.findViewById(R.id.textCommentTime);
            txtCommentText1.setText(commentModule.getTxtComment());
            textClock.setText(commentModule.getTxtCommentTime());
            ImageView imgComment = commentView.findViewById(R.id.imgComment);
            imgComment.setImageResource(R.drawable.edu2);
            includelistviewlayout.addView(commentView, includelistviewlayout.getChildCount() - 1);
            txtCommentText.setText("");
        }
    }


    @Override
    public void onBackPressed() {
        int orientation = MovieDetailActvity.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        if (simpleExoPlayer != null && !wantsTOExit) {
            simpleExoPlayer.stop();
            wantsTOExit = true;
            return;
        }
        super.onBackPressed();

    }

    public void checkAndRotateScreen() {
        int orientation = MovieDetailActvity.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }, 3000);
        }
    }

    private void setRecentlyRecylerView() {
        recyclerRelatedVedio.setNestedScrollingEnabled(false);
        CommanMovieAdapter commanMovieAdapterVertical = new CommanMovieAdapter(this);
        //TypedArray typedArray = getContext().getResources().obtainTypedArray(R.array.imagesArray);
        commanMovieAdapterVertical.setData(arrayList, false);
        recyclerRelatedVedio.setAdapter(commanMovieAdapterVertical);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerRelatedVedio.setLayoutManager(linearLayoutManager);
        new HorizontalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(recyclerRelatedVedio));

        commanMovieAdapterVertical.setCallBack(R.layout.horzonatal_vedio_layout, new CommanMovieAdapter.ICallBack() {
            @Override
            public void init(View view, Object object) {

            }

            @Override
            public void execute(View view, Object object) {
                HomePageMovieView homePageMovieView = (HomePageMovieView) object;
                ImageView horizontalImageView = view.findViewById(R.id.horizontal_image_view);
                horizontalImageView.setImageResource(homePageMovieView.getThumbNailImages());

                ProgressBar howMuchVedioSeenProgresss = view.findViewById(R.id.howMuchVedioSeenProgresss);
                howMuchVedioSeenProgresss.setVisibility(View.GONE);
                //howMuchVedioSeenProgresss.setMax(100); // 100 maximum value for the progress value
                //howMuchVedioSeenProgresss.setProgress(homePageMovieView.getMovieProgress());

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    int ratio = getScreenWidth(MovieDetailActvity.this) / 3;
                    int widthratio = ratio - 50;
                    horizontalImageView.getLayoutParams().width = widthratio;
                    horizontalImageView.getLayoutParams().height = ratio + 20;
                    howMuchVedioSeenProgresss.getLayoutParams().width = widthratio;
                } else {
                    horizontalImageView.getLayoutParams().width = getScreenWidth(MovieDetailActvity.this) / 4;
                    horizontalImageView.getLayoutParams().height = (getScreenWidth(MovieDetailActvity.this) / 4) * (16 / 9);
                    howMuchVedioSeenProgresss.getLayoutParams().width = getScreenWidth(MovieDetailActvity.this) / 4;
                }

            }

            @Override
            public void onClick(View view, Object object, int position) {
                Toast.makeText(view.getContext(), "you clicked on: " + (position + 1), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        switch (textView.getId()) {
            case R.id.txtCommentText:
                if (actionID == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View commentView = layoutInflater.inflate(R.layout.comment_layout, null);
                    TextView txtCommentText1 = commentView.findViewById(R.id.txtCommentText1);
                    final TextClock textClock = commentView.findViewById(R.id.textCommentTime);
                    txtCommentText1.setText(txtCommentText.getText().toString().trim());
                    ImageView imgComment = commentView.findViewById(R.id.imgComment);
                    imgComment.setImageResource(R.drawable.edu2);
                    includelistviewlayout.addView(commentView, includelistviewlayout.getChildCount() - 1);
                    txtCommentText.setText("");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());

                   /*1st method
                    textClock.setFormat12Hour(currentDateandTime);
                   */
                    /*2nd method
                    final Handler someHandler = new Handler(getMainLooper());
                    someHandler.postDelayed(new Runnable() {
                        String timetext;
                        @Override
                        public void run() {
                            timetext =new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date());
                            textClock.setFormat12Hour(timetext);
                            someHandler.postDelayed(this, 1000);
                        }
                    }, 10);
                    */
                    // String timeAgo = DateUtils.getRelativeTimeSpanString()

                    String timeAgo = StaticUtil.covertTimeToText(currentDateandTime);
                    textClock.setText(timeAgo);
                    return true;

                }
                List<CommentModule> commentModuleList = getCommentList();
                Toast.makeText(MovieDetailActvity.this, "Total comments:" + commentModuleList.size(), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private void dummyAudioScrolling() {
        volumeSeekBar = findViewById(R.id.volumneSeekbar);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                try {
                    audioManager.setStreamVolume(simpleExoPlayer.getAudioStreamType(), i, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    //dummy volumen setting
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            try {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(simpleExoPlayer.getAudioStreamType()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
            try {
                volumeSeekBar.setProgress(audioManager.getStreamVolume(simpleExoPlayer.getAudioStreamType()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return super.onKeyUp(keyCode, event);
    }

    private void dummyBrightNessScrolling() {
        brightNessSeekbar = findViewById(R.id.brightNessSeekbar);
        int brightNesslevel = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        brightNessSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Context context = getApplicationContext();
                Boolean canWrite = Settings.System.canWrite(context);
                if (canWrite) {
                    int updatedBrightNess = i * 255 / 255;
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, updatedBrightNess);
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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