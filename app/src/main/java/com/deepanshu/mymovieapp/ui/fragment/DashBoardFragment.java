package com.deepanshu.mymovieapp.ui.fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.ui.module.ShowMovieView;
import com.deepanshu.mymovieapp.ui.module.ZoomOutPageTransformer;
import com.deepanshu.mymovieapp.viewpager.HomeScreenHeaderViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter;


public  class DashBoardFragment extends  BaseFragment {
    public LayoutInflater mInflater;
    private static final String TAG = "BaseFragment";
    private ViewPager viewpager;
    ArrayList<ShowMovieView> arrayList = new ArrayList<>();
    ArrayList<ShowMovieView> arrayList2 = new ArrayList<>();
    Boolean isScrooled = false;
    // use this array yo understand swipe left or right ?
    ArrayList<Float> pos = new ArrayList<Float>();
    private ImageView[] ivArrayDotsPager;
    private LinearLayout llPagerDots;



    public DashBoardFragment() {
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
        View view = inflater.inflate(R.layout.dashboard_fragment,container,false);
        initView(view);
        setOnCLickListner();
        initShowMovieView();
        viewpager.setPageMargin(10);//space between 2 slides
        viewpager.setClipToPadding(false);
        viewpager.setPadding(68, 0, 68, 0);//used for showing left and right images
        //viewpager.setPageTransformer(false, new ZoomOutPageTransformer());
        new HorizontalOverScrollBounceEffectDecorator(new ViewPagerOverScrollDecorAdapter(viewpager));

        viewpager.setAdapter(new HomeScreenHeaderViewPager(arrayList,getActivity()));

        TimerTask timerTask = new TimerTask() {//used to autorotransfer the images
            @Override
            public void run() {
                viewpager.post(new Runnable(){
                    @Override
                    public void run() {
                        viewpager.setCurrentItem((viewpager.getCurrentItem()+1)%arrayList.size());
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Log.v("onPageSelected", String.valueOf(arg0));
                pos.clear();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                try {
            /*        // while scrolling i add ever pos to array
                    pos.add(arg1);
                    // if pos.get(0) > pos.get(pos.size() - 1)
                    // <----- swipe <-----
                    // we should check isScroll because setCurrent item is not scroll ?

                    if (pos.size() > 0)
                        if (arg0 == arrayList.size() - 1
                                & pos.get(0) > pos.get(pos.size() - 1)
                                & isScrooled == true) {
                            try {
                                isScrooled = false;
                                viewpager.setCurrentItem(1, false);
                            } catch (Exception e) {
                                Log.v("hata",
                                        "<----- swipe <-----  " + e.toString());
                            }

                        }
                        // ----> swipe ---->
                        else if (arg0 == 0
                                & pos.get(0) < pos.get(pos.size() - 1)
                                & isScrooled == true) {
                            try {
                                isScrooled = false;
                                viewpager.setCurrentItem(arrayList.size() - 1,
                                        false);
                            } catch (Exception e) {
                                Log.v("hata",
                                        "----> swipe ---->  " + e.toString());
                            }

                        } else if (arg0 == 0 & pos.size() == 1
                                & isScrooled == true) {
                            try {
                                isScrooled = false;
                                viewpager.setCurrentItem(arrayList.size() - 1,
                                        false);
                            } catch (Exception e) {
                                Log.v("hata",
                                        "----> swipe ---->  " + e.toString());
                            }

                        }
*/
                } catch (Exception e) {
                    Log.v("hata", e.toString());
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.v("onPageScrollStateChanged", String.valueOf(arg0));
                // set is scrolling
                isScrooled = true;
            }
        });



        return  view;
    }

    private void setOnCLickListner() {


    }

    private void initView(View view) {
        setupPagerIndidcatorDots();

        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        llPagerDots = view.findViewById(R.id.pager_dots);
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_drawable);

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

    private void initShowMovieView() {
        arrayList.clear();
        arrayList.add(new ShowMovieView(R.drawable.edu1));
        arrayList.add(new ShowMovieView(R.drawable.edu2));
        arrayList.add(new ShowMovieView(R.drawable.edu3));
        arrayList.add(new ShowMovieView(R.drawable.edu4));
        arrayList.add(new ShowMovieView(R.drawable.edu5));
        arrayList.add(new ShowMovieView(R.drawable.edu6));
        arrayList.add(new ShowMovieView(R.drawable.edu1));
        arrayList.add(new ShowMovieView(R.drawable.edu2));
    }

 private class ViewPagerOverScrollDecorAdapter implements IOverScrollDecoratorAdapter {
        public ViewPagerOverScrollDecorAdapter(ViewPager viewpager) {
        }

        @Override
        public View getView() {
            return viewpager;
        }

        @Override
        public boolean isInAbsoluteStart() {
            return !viewpager.canScrollVertically(-1);
        }

        @Override
        public boolean isInAbsoluteEnd() {
            return !viewpager.canScrollVertically(1);
        }
    }
    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[arrayList.size()];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected_drawable);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }
    }


}