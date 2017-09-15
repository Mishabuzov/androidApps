package com.kpfu.mikhail.gif.screen.base.feed;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.Gif;
import com.kpfu.mikhail.gif.content.GifInfo;
import com.kpfu.mikhail.gif.content.LikeState;
import com.kpfu.mikhail.gif.widget.BaseAdapter;

import java.util.ArrayList;

public class GifAdapter extends BaseAdapter<GifHolder, Gif> {

    private RecyclerCallback mCallback;

    GifAdapter(@NonNull RecyclerCallback callback) {
        super(new ArrayList<>());
        mCallback = callback;
    }

    @Override
    public GifHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GifHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_list_item, parent, false), mCallback);
    }

    @Override
    public void onBindViewHolder(GifHolder h, int position) {
        super.onBindViewHolder(h, position);
        Gif gif = getItem(position);
        h.bind(gif);
    }

    void updateGif(GifInfo gifInfo, int position) {
        Gif gif = getItem(position);
        if (gifInfo.getLikeState() ==
                LikeState.STATE_ACTIVE.getLikeStatus()) {
            gif.setLiked(true);
        } else {
            gif.setLiked(false);
        }
        if (gifInfo.getBookmarkState() ==
                LikeState.STATE_ACTIVE.getLikeStatus()) {
            gif.setFavorite(true);
        } else {
            gif.setFavorite(false);
        }
        if (gif.isPlaying()) {
            gif.setPlaying(false);
        } else {
            gif.setPlaying(true);
        }
        notifyItemChanged(position);
    }

    void getPreviousLikeState(int position) {
        Gif gif = getItem(position);
        if (gif.isLiked()) {
            gif.setLiked(false);
            gif.setLikesCount(String.valueOf(Integer.parseInt(gif.getLikesCount()) - 1));
        } else {
            gif.setLiked(true);
            gif.setLikesCount(String.valueOf(Integer.parseInt(gif.getLikesCount()) + 1));
        }
        notifyItemChanged(position);
    }

    void getPreviousFavoriteState(int position) {
        Gif gif = getItem(position);
        if (gif.isFavorite()) {
            gif.setFavorite(false);
        } else {
            gif.setFavorite(true);
        }
        notifyItemChanged(position);
    }

    interface RecyclerCallback {

        void showAuthorizeDialog();

        void onLikeButtonClicked(String likeStatus, String id, int adapterPosition);

        void onFavoriteButtonClicked(boolean isFavorite, String gifId, int adapterPosition);

        void startFullScreenActivity(Gif gif);

        void getGifInfo(String gifId, int adapterPosition);

        LayoutInflater getLayoutInflater();

    }
}