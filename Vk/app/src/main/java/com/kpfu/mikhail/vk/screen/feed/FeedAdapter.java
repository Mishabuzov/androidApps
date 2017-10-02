package com.kpfu.mikhail.vk.screen.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.widget.BaseAdapter;

import java.util.ArrayList;

public class FeedAdapter extends BaseAdapter<FeedHolder, NewsLocal> {

    private final Context mContext;

    private FeedCallback mFeedCallback;

    FeedAdapter(@NonNull Context context) {
        super(new ArrayList<>());
        mContext = context;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FeedHolder feedHolder = new FeedHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.feed_list_item, parent, false), mContext);
        mFeedCallback = feedHolder;
        return feedHolder;
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NewsLocal newsLocal = getItem(position);
        holder.bind(newsLocal);
    }

    @Override
    public void onViewAttachedToWindow(FeedHolder holder) {
        super.onViewAttachedToWindow(holder);

        // Bug workaround for losing text selection ability, see:
        // https://code.google.com/p/android/issues/detail?id=208169
        mFeedCallback.enableTextViewHighLighting();
    }

    interface FeedCallback {

        void enableTextViewHighLighting();

    }

}
