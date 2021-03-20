package com.deepanshu.mymovieapp.ui.fragment;

import android.Manifest;
import android.content.Context;
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
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.deepanshu.mymovieapp.ui.activity.MainDashBoardActivity;
import com.deepanshu.mymovieapp.ui.module.CommentModule;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.deepanshu.mymovieapp.ui.module.RecyclerViewOverScrollDecorAdapter;
import com.deepanshu.mymovieapp.util.StaticUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;

import static android.content.Context.WINDOW_SERVICE;


public class MovieDetailFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextView.OnEditorActionListener {

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

    public MovieDetailFragment() {
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
        View view = inflater.inflate(R.layout.moviedetail_fragment, container, false);
        getBundleData();
        initView(view);
        setOnCLickListner();
        /*if(savedInstanceState == null || !savedInstanceState.containsKey(STATE_COMMENT_LIST)) {
            initMovieHeaderView();
        }
        else {
            Toast.makeText(getContext(), "onRestoreInstanceState:"+commentModuleList.size(), Toast.LENGTH_SHORT).show();
            commentModuleList = savedInstanceState.getParcelableArrayList(STATE_COMMENT_LIST);
            setCommentData(commentModuleList);
        }*/

        return view;
    }

    private void getBundleData() {
        if (getArguments() != null) {
            movieProgress = getArguments().getInt("movieProgress");
            imageurl = getArguments().getInt("imageurl");
            movieUrl = getArguments().getString("movieUrl");
        }
    }

    private void initView(View view) {
        initMovieHeaderView();
        thumnailImage = view.findViewById(R.id.thumnailImage);
        txtVideoTitle = view.findViewById(R.id.txtVideoTitle);
        txtShowMore = view.findViewById(R.id.txtShowMore);
        txtVedioDesc = view.findViewById(R.id.txtVedioDesc);
        txtCommentText = view.findViewById(R.id.txtCommentText);
        recyclerRelatedVedio = view.findViewById(R.id.recycler_relatedVedio);
        includelistviewlayout = view.findViewById(R.id.includelistviewlayout);
        RRdescription = view.findViewById(R.id.RRdescription);
        detaillayout = view.findViewById(R.id.detaillayout);
        videoView = view.findViewById(R.id.videoView);
        btnWatchNow = view.findViewById(R.id.btnWatchNow);
        download = view.findViewById(R.id.download);
        btnMyList = view.findViewById(R.id.btnMyList);
        btnBuyNow = view.findViewById(R.id.btnBuyNow);

        playPauseBtn = view.findViewById(R.id.playPauseVedio);
        playNextBtn = view.findViewById(R.id.playNext);
        playPrevBtn = view.findViewById(R.id.playPrevious);
        playHorizontalVerical = view.findViewById(R.id.playHorizontalVerical);
        seekBar = view.findViewById(R.id.seekBar);
        videolayoutBtns = view.findViewById(R.id.videolayoutBtns);
        chekcAndSetVideoLayoutBtnsVisibleOrNot(isVideolayoutBtnsVisible);


        thumnailImage.setImageResource(imageurl);
        txtVideoTitle.setText("" + movieProgress);
        playPauseProgress = view.findViewById(R.id.playPauseProgress);
        setRecentlyRecylerView();
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
                Toast.makeText(getContext(), "onprepared", Toast.LENGTH_SHORT).show();
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

    private void setRecentlyRecylerView() {
        recyclerRelatedVedio.setNestedScrollingEnabled(false);
        CommanMovieAdapter commanMovieAdapterVertical = new CommanMovieAdapter(getContext());
        //TypedArray typedArray = getContext().getResources().obtainTypedArray(R.array.imagesArray);
        commanMovieAdapterVertical.setData(arrayList, false);
        recyclerRelatedVedio.setAdapter(commanMovieAdapterVertical);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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

                if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    int ratio = getScreenWidth(getContext()) / 3;
                    int widthratio = ratio - 50;
                    horizontalImageView.getLayoutParams().width = widthratio;
                    horizontalImageView.getLayoutParams().height = ratio + 20;
                    howMuchVedioSeenProgresss.getLayoutParams().width = widthratio;
                } else {
                    horizontalImageView.getLayoutParams().width = getScreenWidth(getContext()) / 4;
                    horizontalImageView.getLayoutParams().height = (getScreenWidth(getContext()) / 4) * (16 / 9);
                    howMuchVedioSeenProgresss.getLayoutParams().width = getScreenWidth(getContext()) / 4;
                }

            }

            @Override
            public void onClick(View view, Object object, int position) {
                Toast.makeText(view.getContext(), "you clicked on: " + (position + 1), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setOnCLickListner() {
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
                ((MainDashBoardActivity) getActivity()).chekcAndRotateScreen();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(STATE_COMMENT_LIST, commentModuleList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*if(savedInstanceState != null || savedInstanceState.containsKey(STATE_COMMENT_LIST)) {
            Toast.makeText(getContext(), "onActivityCreated ", Toast.LENGTH_SHORT).show();
            commentModuleList = savedInstanceState.getParcelableArrayList(STATE_COMMENT_LIST);
            setCommentData(commentModuleList);
        }*/
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        /*if(savedInstanceState != null || savedInstanceState.containsKey(STATE_COMMENT_LIST)) {
            Toast.makeText(getContext(), "onViewStateRestored", Toast.LENGTH_SHORT).show();
            commentModuleList = savedInstanceState.getParcelableArrayList(STATE_COMMENT_LIST);
        }*/
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
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow((getActivity()).getCurrentFocus().getWindowToken(), 0);
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                Toast.makeText(getContext(), "Total comments:" + commentModuleList.size(), Toast.LENGTH_LONG).show();
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
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
            } else {
            }
        }
    }

    public void setCommentData(ArrayList<CommentModule> commentModuleList) {
        for (CommentModule commentModule : commentModuleList) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

}