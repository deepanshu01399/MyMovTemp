package com.deepanshu.mymovieapp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.adapter.CommanMovieAdapter;
import com.deepanshu.mymovieapp.ui.activity.MainDashBoardActivity;
import com.deepanshu.mymovieapp.ui.module.HomePageGrpMovieView;
import com.deepanshu.mymovieapp.ui.module.HomePageMovieView;
import com.deepanshu.mymovieapp.ui.module.MovieViewBanner;
import com.deepanshu.mymovieapp.ui.module.RecyclerViewOverScrollDecorAdapter;
import com.deepanshu.mymovieapp.ui.module.ZoomOutPageTransformer;
import com.deepanshu.mymovieapp.viewpager.HomeScreenHeaderViewPager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter;


public class DashBoardFragment extends BaseFragment {
    public LayoutInflater mInflater;
    private static final String TAG = "BaseFragment";
    private ViewPager viewpager;
    ArrayList<HomePageMovieView> arrayList = new ArrayList<>();
    ArrayList<MovieViewBanner> arrayList2 = new ArrayList<>();
    ArrayList<HomePageGrpMovieView> arraylistHomePageGrp = new ArrayList<>();

    Boolean isScrooled = false;
    ArrayList<Float> pos = new ArrayList<Float>();    // use this array yo understand swipe left or right :)
    private ImageView[] ivArrayDotsPager;
    private LinearLayout llPagerDots;
    private RecyclerView  verticalRecyclerView,recylerCategories;
    private LinearLayoutManager linearLayoutManager, linearLayoutManagerVertical;
    CommanMovieAdapter commanMovieAdapterVertical = new CommanMovieAdapter(getContext());


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
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        initView(view);
        setOnCLickListner();
        viewpager.setPageMargin(10);//space between 2 slides
        viewpager.setClipToPadding(false);
        viewpager.setPadding(68, 0, 68, 0);//used for showing left and right images
        // viewpager.setPageTransformer(false, new ZoomOutPageTransformer());
        //new HorizontalOverScrollBounceEffectDecorator(new ViewPagerOverScrollDecorAdapter(viewpager));
        viewpager.setAdapter(new HomeScreenHeaderViewPager(arrayList2, getActivity()));

        TimerTask timerTask = new TimerTask() {//used to autorotransfer the images
            @Override
            public void run() {
                viewpager.post(new Runnable() {
                    @Override
                    public void run() {
                        viewpager.setCurrentItem((viewpager.getCurrentItem() + 1) % arrayList.size());
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.v("onPageSelected", String.valueOf(position));
                pos.clear();
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_drawable);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_drawable);

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


        return view;
    }

    private void initView(View view) {

        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        llPagerDots = view.findViewById(R.id.pager_dots);
        verticalRecyclerView = (RecyclerView) view.findViewById(R.id.my_verical_recycler_view);
        recylerCategories = (RecyclerView)view.findViewById(R.id.recylerCategories);
        initShowMovieView();
        initShowMovieViewVertically();
        initMovieHeaderView();
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_drawable);

    }

    private void setOnCLickListner() {
        setHorizontalRecylerView();
        setVerticalRecylerView();

    }


    private void setHorizontalRecylerView() {
        recylerCategories.setNestedScrollingEnabled(false);
        commanMovieAdapterVertical = new CommanMovieAdapter(getContext());
        commanMovieAdapterVertical.setData(arrayList,true);
        recylerCategories.setAdapter(commanMovieAdapterVertical);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recylerCategories.setLayoutManager(linearLayoutManager);
        new HorizontalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(recylerCategories));

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
                    int ratio = getScreenWidth() / 6;
                    int widthratio = ratio ;
                    horizontalImageView.getLayoutParams().width = widthratio+100;
                    horizontalImageView.getLayoutParams().height = ratio - 20;
                    howMuchVedioSeenProgresss.getLayoutParams().width = widthratio;
                } else {
                    horizontalImageView.getLayoutParams().width = getScreenWidth() / 5;
                    horizontalImageView.getLayoutParams().height = (getScreenWidth() / 9) * (12 / 9);
                    howMuchVedioSeenProgresss.getLayoutParams().width = getScreenWidth() / 5;
                }

            }

            @Override
            public void onClick(View view, Object object, int position) {
                Toast.makeText(view.getContext(), "you clicked on: " + (position + 1), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setVerticalRecylerView() {
        verticalRecyclerView.setNestedScrollingEnabled(false);
        CommanMovieAdapter commanMovieAdapter = new CommanMovieAdapter(getContext());
        commanMovieAdapter.setData(arraylistHomePageGrp, false);
        verticalRecyclerView.setAdapter(commanMovieAdapter);

        linearLayoutManagerVertical = new LinearLayoutManager(getContext());
        linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        verticalRecyclerView.setLayoutManager(linearLayoutManagerVertical);

        new VerticalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(verticalRecyclerView));

        commanMovieAdapter.setCallBack(R.layout.home_page_grp_movie_adapter, new CommanMovieAdapter.ICallBack() {
            @Override
            public void init(View view, Object object) {

            }

            @Override
            public void execute(View view, Object object) {
                HomePageGrpMovieView homePageGrpMovieView = (HomePageGrpMovieView) object;
                TextView txtHorizontalViewTitle = view.findViewById(R.id.txtHorizontalViewTitle);
                txtHorizontalViewTitle.setText(homePageGrpMovieView.getHomePageMovieHeaderTitle());

                RecyclerView horizontalRecylerView = view.findViewById(R.id.horizontalRcyview);
                CommanMovieAdapter commanMovieHorizontalAdapter = new CommanMovieAdapter(getContext());
                commanMovieHorizontalAdapter.setData(homePageGrpMovieView.getHomePageMovieViews(), false);
                horizontalRecylerView.setNestedScrollingEnabled(false);
                horizontalRecylerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return super.canScrollVertically();
                    }

                    @Override
                    public boolean canScrollHorizontally() {
                        return super.canScrollHorizontally();
                    }
                });
                new HorizontalOverScrollBounceEffectDecorator(new RecyclerViewOverScrollDecorAdapter(horizontalRecylerView));
                //this horizontaloverscrollbounce effect is used for making bounce effect on horizontal recyclerview
                horizontalRecylerView.setAdapter(commanMovieHorizontalAdapter);
                horizontalRecylerView.setNestedScrollingEnabled(false);

                commanMovieHorizontalAdapter.setCallBack(R.layout.horzonatal_vedio_layout, new CommanMovieAdapter.ICallBack() {
                    @Override
                    public void init(View view, Object object) {

                    }

                    @Override
                    public void execute(View view, Object object) {
                        HomePageMovieView homePageMovieView = (HomePageMovieView) object;

                        ImageView horizontalImageView = view.findViewById(R.id.horizontal_image_view);
                        horizontalImageView.setImageResource(homePageMovieView.getImages());

                        ProgressBar howMuchVedioSeenProgresss = view.findViewById(R.id.howMuchVedioSeenProgresss);
                        //howMuchVedioSeenProgresss.setMax(100); // 100 maximum value for the progress value
                        howMuchVedioSeenProgresss.setProgress(homePageMovieView.getMovieProgress());

                        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            int ratio = getScreenWidth() / 3;
                            int widthratio = ratio - 50;
                            horizontalImageView.getLayoutParams().width = widthratio;
                            horizontalImageView.getLayoutParams().height = ratio + 20;
                            howMuchVedioSeenProgresss.getLayoutParams().width = widthratio;
                        } else {
                            horizontalImageView.getLayoutParams().width = getScreenWidth() / 4;
                            horizontalImageView.getLayoutParams().height = (getScreenWidth() / 4) * (16 / 9);
                            howMuchVedioSeenProgresss.getLayoutParams().width = getScreenWidth() / 4;
                        }

                    }

                    @Override
                    public void onClick(View view, Object object, int position) {
                        HomePageMovieView homePageMovieView = (HomePageMovieView) object;

                        Toast.makeText(view.getContext(), "you clicked on: " + (position + 1), Toast.LENGTH_SHORT).show();

                        BaseFragment movieDetailFragment = new MovieDetailFragment();
                        Bundle bundle=new Bundle();
                        bundle.putInt("imageurl",homePageMovieView.getImages());
                        bundle.putInt("movieProgress",homePageMovieView.getMovieProgress());
                        movieDetailFragment.setArguments(bundle);
                        ((MainDashBoardActivity) getActivity()).replaceFragment(movieDetailFragment, getChildFragmentManager(), true);

                    }
                });

            }

            @Override
            public void onClick(View view, Object object, int position) {
                Toast.makeText(view.getContext(), "you clicked on: " + (position + 1), Toast.LENGTH_SHORT).show();

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

    private void initShowMovieView() {
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

    private void initShowMovieViewVertically() {
        arraylistHomePageGrp.clear();
        arraylistHomePageGrp.add(new HomePageGrpMovieView(arrayList, "Countinue Watching", "See ALl"));
        arraylistHomePageGrp.add(new HomePageGrpMovieView(arrayList, "Latest Movies", "See ALl"));
        arraylistHomePageGrp.add(new HomePageGrpMovieView(arrayList, "UpComing Watching", "See ALl"));
        arraylistHomePageGrp.add(new HomePageGrpMovieView(arrayList, "Latest Web Series", "See ALl"));

    }

    private void initMovieHeaderView() {
        arrayList2.clear();
        arrayList2.add(new MovieViewBanner(R.drawable.edu1));
        arrayList2.add(new MovieViewBanner(R.drawable.edu2));
        arrayList2.add(new MovieViewBanner(R.drawable.edu3));
        arrayList2.add(new MovieViewBanner(R.drawable.edu4));
        arrayList2.add(new MovieViewBanner(R.drawable.edu5));
        arrayList2.add(new MovieViewBanner(R.drawable.edu6));
        arrayList2.add(new MovieViewBanner(R.drawable.edu1));
        arrayList2.add(new MovieViewBanner(R.drawable.edu2));
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

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }


}