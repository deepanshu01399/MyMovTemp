package com.deepanshu.mymovieapp.ui.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.ui.module.ShowMovieView;
import com.deepanshu.mymovieapp.viewpager.HomeScreenHeaderViewPager;

import java.util.ArrayList;


public  class DashBoardFragment extends  BaseFragment {
    public LayoutInflater mInflater;
    private static final String TAG = "BaseFragment";
    private ViewPager viewpager;
    ArrayList<ShowMovieView> arrayList = new ArrayList<>();
    ArrayList<ShowMovieView> arrayList2 = new ArrayList<>();



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
        viewpager.setAdapter(new HomeScreenHeaderViewPager(arrayList,getActivity()));
        viewpager.setPageMargin(20);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return  view;
    }

    private void setOnCLickListner() {
    }

    private void initView(View view) {
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);

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

}