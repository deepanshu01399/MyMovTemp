package com.deepanshu.mymovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommanMovieAdapter<T> extends RecyclerView.Adapter<CommanMovieAdapter<T>.MyViewHolder> {
    private int layout;
    private ICallBack iCallBack;
    private Context context;
    private List<T> mItems;
    private boolean isAnimationAllowed;

    public CommanMovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<T> items, boolean isAnimationAllowed) {
        mItems = items;
        this.isAnimationAllowed = isAnimationAllowed;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mItems;
    }

    public void clearList() {
        if (mItems != null) {
            mItems.clear();
            notifyDataSetChanged();
        }
    }

    public void removeItems(List<T> removeItems) {
        mItems.removeAll(removeItems);
        notifyDataSetChanged();
    }

    public void removeItemAt(T removeItem) {
        mItems.remove(removeItem);
        notifyDataSetChanged();
    }

    //these 2 methods are useful for remove multselect issue
    @Override//1
    public long getItemId(int position) {
        return position;
    }

    @Override//2
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        iCallBack.init(view, mItems.get(position));
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        iCallBack.execute(holder.itemView, mItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCallBack.onClick(holder.itemView, mItems.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void clearAnimation() {
            itemView.clearAnimation();
        }

    }

    public interface ICallBack<T> {
        void init(View view, T object);

        void execute(View view, T object);

        void onClick(View view, T object, int position);
    }


    public void setCallBack(int layout, ICallBack iCallBack) {
        this.layout = layout;
        this.iCallBack = iCallBack;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        /*if (position > lastPosition)
        {*/
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
        //lastPosition = position;
        /*}*/
    }

}
