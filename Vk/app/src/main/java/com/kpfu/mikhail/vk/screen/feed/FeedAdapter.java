package com.kpfu.mikhail.vk.screen.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.NewsLocal;
import com.kpfu.mikhail.vk.screen.base.BaseAdapter;

import java.util.ArrayList;

public class FeedAdapter extends BaseAdapter<NewsLocal> {

    private final Context mContext;

    private FeedCallback mFeedCallback;

    FeedAdapter(@NonNull Context context,
                @NonNull AdapterCallback callback) {
        super(new ArrayList<>(), callback);
        mContext = context;
    }

    @Override
    protected ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        FeedHolder feedHolder = new FeedHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.feed_list_item, parent, false), mContext);
        mFeedCallback = feedHolder;
        return feedHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            FeedHolder feedHolder = (FeedHolder) holder;
            NewsLocal newsLocal = getItem(position);
            feedHolder.bind(newsLocal);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        // Bug workaround for losing text selection ability, see:
        // https://code.google.com/p/android/issues/detail?id=208169
        mFeedCallback.enableTextViewHighLighting();
    }

    interface FeedCallback {

        void enableTextViewHighLighting();

    }

}
