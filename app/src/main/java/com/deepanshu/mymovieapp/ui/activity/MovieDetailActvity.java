package com.deepanshu.mymovieapp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.adapter.CommanMovieAdapter;
import com.deepanshu.mymovieapp.interfaces.FragmentChangeListener;
import com.deepanshu.mymovieapp.ui.custom.ColoredSnackbar;
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

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;

import static com.deepanshu.mymovieapp.util.StaticUtil.getScreenHeight;
import static com.deepanshu.mymovieapp.util.StaticUtil.getScreenWidth;

public  class MovieDetailActvity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextView.OnEditorActionListener {
    private ImageView thumnailImage;
    private TextView txtVideoTitle, txtVedioDesc;
    private CheckBox txtShowMore;
    private RecyclerView recyclerRelatedVedio;
    private TextView txtCommentText;
    private ArrayList<HomePageMovieView> arrayList = new ArrayList<>();
    private LinearLayout includelistviewlayout;
    private RelativeLayout RRdescription, detaillayout, videolayoutBtns;
    private int imageurl = 0, movieProgress = 0;
    private String movieUrl;
    private SeekBar seekBar;
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
    private Boolean flag = false;
    private CollapsingToolbarLayout collapsingToolbarlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public MovieDetailActvity() {
    }

    private void getBundleData() {
        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            imageurl = intent.getExtras().getInt("imageurl");
            movieUrl = intent.getStringExtra("movieUrl");
            movieProgress = intent.getExtras().getInt("movieProgress");
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
        initMovieHeaderView();
        getBundleData();
        playerView = findViewById(R.id.player_view);
        progressBar =findViewById(R.id.progress_bar);
        fullScreen = findViewById(R.id.bt_fullScreen);
        fullScreen.setOnClickListener(this);
        thumnailImage = findViewById(R.id.thumnailImage);
        txtVideoTitle = findViewById(R.id.txtVideoTitle);
        txtShowMore = findViewById(R.id.txtShowMore);
        txtVedioDesc =findViewById(R.id.txtVedioDesc);
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

        thumnailImage.setImageResource(imageurl);
        txtVideoTitle.setText("" + movieProgress);

        checkPermission();
        setonClickListner();
        setRecentlyRecylerView();

    }


    private void setonClickListner() {
        thumnailImage.setOnClickListener(this);
        txtShowMore.setOnCheckedChangeListener(this);
        txtCommentText.setOnEditorActionListener(this);
        btnBuyNow.setOnClickListener(this);
        btnMyList.setOnClickListener(this);
        btnWatchNow.setOnClickListener(this);
        download.setOnClickListener(this);
        //todo make window full screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //video url
        Uri  videoUrl = Uri.parse("https://imgur.com/7bMqysJ.mp4");
        //initalise load control
        LoadControl loadControl = new DefaultLoadControl();
        //initialise Band width
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //intialise the track selector
        TrackSelector  trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        //initialsie simple exo player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(MovieDetailActvity.this,trackSelector,loadControl);
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
    public void onBackStackChanged() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fullScreen:
                //check conditons:
                checkAndRotateScreen();

                if(flag){
                    //when flag is true --> set orientation or enter to full screen else in half screen
                    fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    simpleExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    handleVideoViewLayout(flag);
                    flag = false;
                }
                else{
                    fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    handleVideoViewLayout(flag);

                    flag = true;
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

        }

    }

    private void handleVideoViewLayout(Boolean flag) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            collapsingToolbarlayout.getLayoutParams().width = getScreenHeight(MovieDetailActvity.this);
            collapsingToolbarlayout.getLayoutParams().height = getScreenWidth(MovieDetailActvity.this);

        } else {
            collapsingToolbarlayout.getLayoutParams().width = getScreenHeight(MovieDetailActvity.this);
            collapsingToolbarlayout.getLayoutParams().height = getScreenWidth(MovieDetailActvity.this)/3;

        }
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
        if (ischecked) {
            txtVedioDesc.setMaxLines(10);
            txtShowMore.setText("Show Less >");
            ViewGroup.LayoutParams params = txtVedioDesc.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            txtVedioDesc.setLayoutParams(params);
            animate();

        } else {
            txtVedioDesc.setMaxLines(2);
            txtShowMore.setText("Show More >");
            animate();

        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //unhide your objects here.
            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            MovieDetailActvity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }

    }

    private void initMovieHeaderView() {
        arrayList.clear();
        arrayList.add(new HomePageMovieView(R.drawable.edu1, 60, "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu2, 20, "https://media.w3.org/2010/05/sintel/trailer.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu3, 30, "http://video19.ifeng.com/video07/2013/11/11/281708-102-007-1138.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu4, 10, "https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu5, 0, "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu6, 100, "https://media.w3.org/2010/05/sintel/trailer.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu1, 70, "http://video19.ifeng.com/video07/2013/11/11/281708-102-007-1138.mp4"));
        arrayList.add(new HomePageMovieView(R.drawable.edu2, 40, "https://videolinks.com/pub/media/videolinks/video/dji.osmo.action.mp4"));
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
            LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        super.onBackPressed();

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


}