package com.deepanshu.mymovieapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.interfaces.FragmentChangeListener;
import com.deepanshu.mymovieapp.ui.fragment.BaseFragment;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.deepanshu.mymovieapp.ui.module.LocalVideoModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class LocalVideoPlayActvity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentChangeListener, FragmentManager.OnBackStackChangedListener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private LocalVideoModel localVideoModel;
    private ArrayList<LocalVideoModel> arrayList = new ArrayList<>();
    VideoView videoView;
    ImageView prev, next, pause;
    SeekBar seekBar;
    int video_index = 0;
    double current_pos, total_duration;
    TextView current, total;
    LinearLayout showProgress;
    Handler mHandler, handler;
    boolean isVisible = true;
    RelativeLayout relativeLayout;
    public static final int PERMISSION_READ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public LocalVideoPlayActvity() {
    }

    private void getBundleData() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            video_index = getIntent().getIntExtra("position", 0);
            localVideoModel = intent.getExtras().getParcelable("localVideoModel");
            arrayList = intent.getParcelableArrayListExtra("localPlayList");


        }
    }

    @Override
    public String getHeaderTitle() {
        return "Local MovieDetail";
    }

    @Override
    public int getLayoutByID() {
        return R.layout.local_videoplayer_activity;
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

    private void updateMovieDetailinfo(HomePageMovieView homePageMovieView) {
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
        if (checkPermission()) {
            setVideo();
        }


    }

    public void setVideo() {

        videoView = (VideoView) findViewById(R.id.videoview);
        prev = (ImageView) findViewById(R.id.prev);
        next = (ImageView) findViewById(R.id.next);
        pause = (ImageView) findViewById(R.id.pause);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        current = (TextView) findViewById(R.id.current);
        total = (TextView) findViewById(R.id.total);
        showProgress = (LinearLayout) findViewById(R.id.showProgress);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);

        mHandler = new Handler();
        handler = new Handler();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video_index++;
                if (video_index < (arrayList.size())) {
                    playVideo(video_index);
                } else {
                    video_index = 0;
                    playVideo(video_index);
                }

            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                 setVideoProgress();
            }
        });
        playVideo(video_index);
        prevVideo();
        nextVideo();
        setPause();
        hideLayout();

        //seekbar change listner
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                videoView.seekTo((int) current_pos);
            }
        });

    }

    // display video progress
    public void setVideoProgress() {
        //get the video duration
        current_pos = videoView.getCurrentPosition();
        total_duration = videoView.getDuration();

        //display video duration
        total.setText(timeConversion((long) total_duration));
        current.setText(timeConversion((long) current_pos));
        seekBar.setMax((int) total_duration);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = videoView.getCurrentPosition();
                    current.setText(timeConversion((long) current_pos));
                    seekBar.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    public void hideLayout() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showProgress.setVisibility(View.GONE);
                isVisible = false;
            }
        };
        handler.postDelayed(runnable, 5000);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                if (isVisible) {
                    showProgress.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    showProgress.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(runnable, 5000);
                    isVisible = true;
                }
            }
        });

    }

    //time conversion
    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    // play video file
    public void playVideo(int pos) {
        try {
            Toast.makeText(LocalVideoPlayActvity.this,""+arrayList.get(pos).getVideoUri(),Toast.LENGTH_LONG).show();
            videoView.setVideoURI(arrayList.get(pos).getVideoUri());
            videoView.start();
            pause.setImageResource(R.drawable.ic_pause);
            video_index = pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //play previous video
    public void prevVideo() {
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_index > 0) {
                    video_index--;
                    playVideo(video_index);
                } else {
                    video_index = arrayList.size() - 1;
                    playVideo(video_index);
                }
            }
        });
    }

    //play next video
    public void nextVideo() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video_index < (arrayList.size() - 1)) {
                    video_index++;
                    playVideo(video_index);
                } else {
                    video_index = 0;
                    playVideo(video_index);
                }
            }
        });
    }

    public void setPause() {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pause.setImageResource(R.drawable.ic_play);
                } else {
                    videoView.start();
                    pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });
    }


    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(LocalVideoPlayActvity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(LocalVideoPlayActvity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(LocalVideoPlayActvity.this, "Please allow storage permission", Toast.LENGTH_LONG).show();
            } else {
            }
        }
    }


    @Override
    public void onBackPressed() {
        int orientation = LocalVideoPlayActvity.this.getResources().getConfiguration().orientation;
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