package com.deepanshu.mymovieapp.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.adapter.CommanMovieAdapter;
import com.deepanshu.mymovieapp.ui.activity.BaseActivity;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.deepanshu.mymovieapp.ui.module.MovieViewBanner;
import com.deepanshu.mymovieapp.ui.module.RecyclerViewOverScrollDecorAdapter;
import com.deepanshu.mymovieapp.util.StaticUtil;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;

import static android.os.Looper.getMainLooper;
import static java.sql.Types.TIMESTAMP;


public class MovieDetailFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextView.OnEditorActionListener {

    private ImageView thumnailImage;
    private TextView txtVideoTitle, txtVedioDesc;
    private CheckBox txtShowMore;
    private RecyclerView recyclerRelatedVedio;
    private TextView txtCommentText;
    private ArrayList<HomePageMovieView> arrayList = new ArrayList<>();
    private LinearLayout includelistviewlayout;

    private int imageurl = 0, movieProgress = 0;

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

        return view;
    }

    private void getBundleData() {
        if (getArguments() != null) {
            movieProgress = getArguments().getInt("movieProgress");
            imageurl = getArguments().getInt("imageurl");
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
        includelistviewlayout=view.findViewById(R.id.includelistviewlayout);

        thumnailImage.setImageResource(imageurl);
        txtVideoTitle.setText("" + movieProgress);
        setRecentlyRecylerView();


    }

    private void setRecentlyRecylerView() {
        recyclerRelatedVedio.setNestedScrollingEnabled(false);
        CommanMovieAdapter commanMovieAdapterVertical = new CommanMovieAdapter(getContext());
        //TypedArray typedArray = getContext().getResources().obtainTypedArray(R.array.imagesArray);
        commanMovieAdapterVertical.setData(arrayList,false);
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
                horizontalImageView.setImageResource(homePageMovieView.getImages());

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
            case R.id.thumnailImage:
                Toast.makeText(getContext(), "Playing vedio...", Toast.LENGTH_SHORT).show();
                break;
        }

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

        } else {
            txtVedioDesc.setMaxLines(2);
            txtShowMore.setText("Show More >");

        }
    }


    private void initMovieHeaderView() {
        arrayList.clear();
        arrayList.add(new HomePageMovieView(R.drawable.edu1, 60));
        arrayList.add(new HomePageMovieView(R.drawable.edu2, 20));
        arrayList.add(new HomePageMovieView(R.drawable.edu3, 30));
        arrayList.add(new HomePageMovieView(R.drawable.edu4, 10));
        arrayList.add(new HomePageMovieView(R.drawable.edu5, 0));
        arrayList.add(new HomePageMovieView(R.drawable.edu6, 100));
        arrayList.add(new HomePageMovieView(R.drawable.edu1, 70));
        arrayList.add(new HomePageMovieView(R.drawable.edu2, 40));
    }


    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        switch (textView.getId()){
            case R.id.txtCommentText:
                if(actionID== EditorInfo.IME_ACTION_DONE){
                    InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow((getActivity()).getCurrentFocus().getWindowToken(),0);

                    LayoutInflater layoutInflater= (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View commentView =layoutInflater.inflate(R.layout.comment_layout,null);
                    TextView txtCommentText1 = commentView.findViewById(R.id.txtCommentText1);
                    final TextClock textClock=commentView.findViewById(R.id.textClock);
                    txtCommentText1.setText(txtCommentText.getText().toString().trim());
                    ImageView imgComment = commentView.findViewById(R.id.imgComment);
                    imgComment.setImageResource(R.drawable.edu2);
                    includelistviewlayout.addView(commentView,includelistviewlayout.getChildCount()-1);
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

                break;
        }
        return true;
    }
}