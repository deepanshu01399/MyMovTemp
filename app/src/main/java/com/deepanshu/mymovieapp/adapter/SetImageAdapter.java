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

public class SetImageAdapter<T> extends RecyclerView.Adapter<SetImageAdapter<T>.ViewHolder> {

    private List<T> mItems;
    private Context context;
    private int mLayout;
    IClickable iClickable;
    private boolean isAnimationAllowed;

    public SetImageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<T> items, boolean isAnimationAllowed) {

        mItems = items;

        this.isAnimationAllowed = isAnimationAllowed;
        notifyDataSetChanged();

    }

    public void setCallback(int layout,/*float itemHeight, */IClickable iClickable) {
        this.mLayout = layout;
        this.iClickable = iClickable;
        // this.itemViewHeightDividerFloatValue = itemHeight;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mLayout, viewGroup, false);
        iClickable.init(view, mItems.get(i));
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        iClickable.execute(holder.itemView, mItems.get(position),position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickable.onClick(holder.itemView, mItems.get(position));
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public List<T> getData() {
        return mItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View v) {
            super(v);
        }

        public void clearAnimation() {
            itemView.clearAnimation();
        }
    }

    public interface IClickable<T> {
        void init(View view, T object);

        /* public void execute(View view, T object);*/
        void execute(View view, T object,int position);

        void onClick(View view, T Object);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
      }

}