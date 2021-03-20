package com.deepanshu.mymovieapp.ui.activity.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.FragmentChangeListener;
import com.deepanshu.mymovieapp.ui.activity.BaseActivity;
import com.deepanshu.mymovieapp.ui.fragment.BaseFragment;
import com.deepanshu.mymovieapp.ui.module.CommentModule;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.deepanshu.mymovieapp.util.StaticUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class MovieDetailActvityTest extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextView.OnEditorActionListener {
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
    private VideoView videoView;
    private SeekBar seekBar;
    private static String TAG = "MovieDetailFrag";
    //private String MEDIA_PATH = "android.resource://com.deepanshu.mymovieapp/"+R.raw.big_buck_bunny_480p_5mb;
    private String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20210228-WA0011.mp4";
    //private String  MEDIA_PATH= Environment.getExternalStorageDirectory()+"/storage/emulated/0/VidMate/download/Allu_Arjun.mp4";
    private Button btnWatchNow, download, btnMyList, btnBuyNow;
    private static final String STATE_COMMENT_LIST = "comments";
    private ArrayList<CommentModule> commentModuleList;
    private ProgressBar playPauseProgress;
    private ImageButton playPauseBtn, playNextBtn, playPrevBtn, playHorizontalVerical;
    private Boolean isVideolayoutBtnsVisible = false, isVideoPlaying = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//to remove autofill suggestion
            getWindow()
                    .getDecorView()
                    .setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }

    }

    public MovieDetailActvityTest() {
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
        return R.layout.moviedetail_activity_test;
    }

    @Override
    public void getViewById() {
        initMovieHeaderView();
        getBundleData();
        thumnailImage = findViewById(R.id.thumnailImage);
        txtVideoTitle = findViewById(R.id.txtVideoTitle);
        txtShowMore = findViewById(R.id.txtShowMore);
        txtVedioDesc =findViewById(R.id.txtVedioDesc);
        txtCommentText = findViewById(R.id.txtCommentText);
        recyclerRelatedVedio = findViewById(R.id.recycler_relatedVedio);
        includelistviewlayout = findViewById(R.id.includelistviewlayout);
        RRdescription = findViewById(R.id.RRdescription);
        detaillayout = findViewById(R.id.detaillayout);
        videoView = findViewById(R.id.videoView);
        btnWatchNow = findViewById(R.id.btnWatchNow);
        download = findViewById(R.id.download);
        btnMyList = findViewById(R.id.btnMyList);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        playPauseBtn = findViewById(R.id.playPauseVedio);
        playNextBtn = findViewById(R.id.playNext);
        playPrevBtn = findViewById(R.id.playPrevious);
        playHorizontalVerical = findViewById(R.id.playHorizontalVerical);
        seekBar = findViewById(R.id.seekBar);
        videolayoutBtns = findViewById(R.id.videolayoutBtns);
        chekcAndSetVideoLayoutBtnsVisibleOrNot(isVideolayoutBtnsVisible);
        thumnailImage.setImageResource(imageurl);
        txtVideoTitle.setText("" + movieProgress);
        playPauseProgress = findViewById(R.id.playPauseProgress);
        checkPermission();

        //videoView.setVideoPath(MEDIA_PATH);//for local path used for stored vedio

        Uri uri = Uri.parse(movieUrl);
        videoView.setVideoURI(uri);
//        MediaController mediaController = new MediaController(getContext());
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
        //videoView.start();
        Log.e(TAG, "mediaPath:" + movieUrl);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(MovieDetailActvityTest.this, "onprepared", Toast.LENGTH_SHORT).show();
                seekBar.setMax(videoView.getDuration());

            }

        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "setOnErrorListener ");
                return true;
            }
        });

        setonClickListner();

        initClickListner();


    }

    private void chekcAndSetVideoLayoutBtnsVisibleOrNot(Boolean btnVisibleOrNot) {

        if (btnVisibleOrNot) {
            videolayoutBtns.setVisibility(View.VISIBLE);
            isVideolayoutBtnsVisible = true;
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    videolayoutBtns.setVisibility(View.GONE);
                    isVideolayoutBtnsVisible = false;
                }
            };
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 3000);

        } else {
            videolayoutBtns.setVisibility(View.GONE);
            isVideolayoutBtnsVisible = false;
        }

    }

    public void playVideo(View view) {//if playing then stop or if stop then resume
        if (videoView.isPlaying()) {
            videoView.resume();
        } else
            videoView.start();
        new CountDownTimer(videoView.getDuration(), 1) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTick(long l) {
                seekBar.setProgress(videoView.getCurrentPosition(), true);
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    public void pauseVideo(View view) {
        videoView.pause();

    }

    public void stopVideo(View view) {
        videoView.resume();

    }

    public void restartVideo(View view) {//start from begining
        videoView.stopPlayback();
        videoView.setVideoPath(MEDIA_PATH);
        videoView.start();


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

    private void initClickListner() {
    }

    private void setonClickListner() {
        thumnailImage.setOnClickListener(this);
        txtShowMore.setOnCheckedChangeListener(this);
        txtCommentText.setOnEditorActionListener(this);
        btnBuyNow.setOnClickListener(this);
        btnMyList.setOnClickListener(this);
        btnWatchNow.setOnClickListener(this);
        download.setOnClickListener(this);
        playPauseProgress.setOnClickListener(this);
        playPauseBtn.setOnClickListener(this);
        playNextBtn.setOnClickListener(this);
        playPrevBtn.setOnClickListener(this);
        playHorizontalVerical.setOnClickListener(this);
        videolayoutBtns.setOnClickListener(this);
        videoView.setOnClickListener(this);


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
            case R.id.videolayoutBtns:
            case R.id.videoView:
                if (isVideolayoutBtnsVisible) {
                    videolayoutBtns.setVisibility(View.GONE);
                    isVideolayoutBtnsVisible = false;


                   /* Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            videolayoutBtns.setVisibility(View.GONE);
                            isVideolayoutBtnsVisible = false;

                        }
                    }, 3000);
*/
                } else {
                    isVideolayoutBtnsVisible = true;
                    videolayoutBtns.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.playPauseVedio:
                if (isVideoPlaying) {
                    pauseVideo(view);
                    playPauseBtn.setImageResource(R.drawable.ic_pause);
                    isVideoPlaying = false;


                } else {
                    videoView.setVisibility(View.VISIBLE);
                    thumnailImage.setVisibility(View.GONE);
                    playVideo(view);
                    playPauseBtn.setImageResource(R.drawable.ic_play);
                    isVideoPlaying = true;

                }
                break;
            case R.id.playHorizontalVerical:
                chekcAndRotateScreen();
                break;

            case R.id.playPrevious:

                break;
            case R.id.playNext:

                break;

            case R.id.playPauseProgress:

                break;
            case R.id.thumnailImage:
                chekcAndSetVideoLayoutBtnsVisibleOrNot(!isVideolayoutBtnsVisible);

                break;

            case R.id.btnWatchNow:

                break;

            case R.id.btnMyList:
                restartVideo(view);
                break;

            case R.id.btnBuyNow:
                stopVideo(view);
                break;

            case R.id.download:

                break;

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
                Toast.makeText(MovieDetailActvityTest.this, "Total comments:" + commentModuleList.size(), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private void animate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (detaillayout != null)
                TransitionManager.beginDelayedTransition(detaillayout); // this line for expanding view
        }
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(MovieDetailActvityTest.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MovieDetailActvityTest.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(MovieDetailActvityTest.this, "Please allow storage permission", Toast.LENGTH_LONG).show();
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

    public void chekcAndRotateScreen() {
        int orientation = MovieDetailActvityTest.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //showMediaDescription();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // hideMediaDescription();
        }
        if (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }, 4000);
        }
    }

}