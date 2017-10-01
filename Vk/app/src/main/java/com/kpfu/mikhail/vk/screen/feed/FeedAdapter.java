package com.kpfu.mikhail.vk.screen.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.widget.BaseAdapter;
import com.kpfu.mikhail.vk.widget.textviews.RobotoMediumTextView;
import com.kpfu.mikhail.vk.widget.textviews.RobotoRegularTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedAdapter extends BaseAdapter<FeedHolder, NewsLocal> {

    private final Context mContext;

    FeedAdapter(@NonNull Context context) {
        super(new ArrayList<>());
        mContext = context;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.feed_list_item, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NewsLocal newsLocal = getItem(position);
        holder.bind(newsLocal);
    }

}
