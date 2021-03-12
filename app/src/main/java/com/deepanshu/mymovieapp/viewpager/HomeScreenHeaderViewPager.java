package com.deepanshu.mymovieapp.viewpager;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.ui.module.ShowMovieView;

import java.util.ArrayList;

public class HomeScreenHeaderViewPager extends PagerAdapter {

    /* <p>When you implement a PagerAdapter, you must override the following methods
     * at minimum:</p>
     * <ul>
     * <li>{@link #insta    ntiateItem(ViewGroup, int)}</li>
     * <li>{@link #destroyItem(ViewGroup, int, Object)}</li>
     * <li>{@link #getCount()}</li>
     * <li>{@link #isViewFromObject(View, Object)}</li>
     * </ul>
     *
     */ ArrayList<ShowMovieView> arrayList;
    Context context;
    LayoutInflater layoutinflater;

    public HomeScreenHeaderViewPager(ArrayList<ShowMovieView> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutinflater.inflate(R.layout.headerbanner, container, false);
        ImageView imageView = itemView.findViewById(R.id.view_image);
        imageView.setImageResource(arrayList.get(position).getImages());

        if (container.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageView.getLayoutParams().width = getScreenWidth();
            imageView.getLayoutParams().height = getScreenHeight() / 3 + 100;
        } else {
            imageView.getLayoutParams().width = getScreenWidth();
            imageView.getLayoutParams().height = getScreenHeight() / 2 + 80;
        }
        if(itemView.getParent()!=null){
            ((ViewGroup)itemView.getParent()).removeView(itemView); // <- used to remove old view
        }
        container.addView(itemView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();

            }
        });
        return itemView;


    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (object);
    }


    private int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }

}
