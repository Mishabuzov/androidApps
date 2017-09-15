package com.kpfu.mikhail.gif.screen.base.feed;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.Gif;
import com.kpfu.mikhail.gif.content.LikeState;
import com.kpfu.mikhail.gif.screen.base.feed.GifAdapter.RecyclerCallback;
import com.kpfu.mikhail.gif.utils.PreferenceUtils;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

class GifHolder extends RecyclerView.ViewHolder {

    @BindDrawable(R.drawable.heart) Drawable mLikePict;

    @BindDrawable(R.drawable.redheart) Drawable mActiveLikePict;

    @BindDrawable(R.drawable.bookmark) Drawable mFavoritePict;

    @BindDrawable(R.drawable.bookmark_active) Drawable mActiveFavoritePict;

    @BindString(R.string.error_load_gif) String mErrorLoadGifMsg;

    @BindString(R.string.untitled) String mUntitledMsg;

    @BindView(R.id.title_tv) TextView mTitle;

    @BindView(R.id.gif_iv) ImageView mGifIv;

    @BindView(R.id.pr_bar) ProgressBar mPrBar;

    @BindView(R.id.error_tv) TextView mErrorGifLoadTv;

    @BindView(R.id.pr_bar_layout) LinearLayout mPrBarLayout;

    @BindView(R.id.btn_like) Button mLikeButton;

    @BindView(R.id.btn_favourite) Button mFavoriteButton;

    @BindView(R.id.btn_eye) Button mWatchButton;

    @BindView(R.id.tags_scroll_view) LinearLayout mTagsScrollView;

    @BindView(R.id.functional_layout) LinearLayout mLikeAndFavoriteLayout;

    private boolean mIsUserAuthorized;

    private RecyclerCallback mCallback;

    private Gif mGif;

    GifHolder(@NonNull View itemView,
              @NonNull RecyclerCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mIsUserAuthorized = PreferenceUtils.isCurrentUserAuthorized();
        mCallback = callback;
    }

    public void bind(@NonNull Gif gif) {
        mGif = gif;
        if (mGif.isPlaying()) {
            startPlayingGif();
        } else {
            showPoster();
        }
        addTags(gif.getTags(), mTagsScrollView);
        setWatchesButton();
        setTitle(gif.getTitle());
        configureLikesAndFavoritesLayout();
        mErrorGifLoadTv.setVisibility(View.GONE);
        onGifClickListener();
    }

    private void showPoster() {
        onLoadStart();
        Glide
                .with(itemView.getContext())
                .load(mGif.getPoster())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mErrorGifLoadTv.setVisibility(View.VISIBLE);
                        mErrorGifLoadTv.setText(mErrorLoadGifMsg);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        onLoadFinished();
                        return false;
                    }
                })
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) // against long loading
                .into(mGifIv);
    }

    private void startPlayingGif() {
        onLoadStart();
        Glide
                .with(mGifIv.getContext())
                .load(mGif.getLowUrl())
                .asGif()
                .listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GifDrawable> target, boolean isFirstResource) {
                        mErrorGifLoadTv.setVisibility(View.VISIBLE);
                        mErrorGifLoadTv.setText(mErrorLoadGifMsg);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, String model,
                                                   Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        onLoadFinished();
                        return false;
                    }
                })
                .skipMemoryCache(true)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mGifIv);
    }

    protected void setTitle(String title) {
        mTitle.setVisibility(View.GONE);
        if (title != null && !title.equals(mUntitledMsg)) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    private void setLikeBtnForUnauthorizedUser(Gif gif) {
        mLikeButton.setText(gif.getLikesCount());
        mLikeButton.setCompoundDrawablesWithIntrinsicBounds(mLikePict, null, null, null);
        mLikeButton.setOnClickListener(view -> {
            mCallback.showAuthorizeDialog();
        });
    }

    private void setLikeBtnForAuthorizedUser(Gif gif) {
        mLikeButton.setText(gif.getLikesCount());
        if (gif.isLiked()) {
            mLikeButton.setCompoundDrawablesWithIntrinsicBounds(mActiveLikePict, null, null, null);
        } else {
            mLikeButton.setCompoundDrawablesWithIntrinsicBounds(mLikePict, null, null, null);
        }
        mLikeButton.setOnClickListener(view -> {
            if (gif.isLiked()) {
                likeChanges(false, Integer.parseInt(gif.getLikesCount()) - 1, mLikePict, gif);
            } else {
                likeChanges(true, Integer.parseInt(gif.getLikesCount()) + 1, mActiveLikePict, gif);
            }
        });
    }

    private void likeChanges(boolean isLikeSet, int likesCount, Drawable likePict, Gif gif) {
        mLikeButton.setCompoundDrawablesWithIntrinsicBounds(likePict, null, null, null);
        gif.setLiked(isLikeSet);
        gif.setLikesCount(String.valueOf(likesCount));
        mLikeButton.setText(gif.getLikesCount());
        int likeStatus;
        if (isLikeSet) {
            likeStatus = LikeState.STATE_ACTIVE.getLikeStatus();
        } else {
            likeStatus = LikeState.STATE_NON_ACTIVE.getLikeStatus();
        }
        mCallback.onLikeButtonClicked(String.valueOf(likeStatus), gif.getId(), getAdapterPosition());
    }

    private void setFavouriteBtn(Gif gif) {
        if (gif.isFavorite()) {
            mFavoriteButton.setBackground(mActiveFavoritePict);
        } else {
            mFavoriteButton.setBackground(mFavoritePict);
        }
        mFavoriteButton.setOnClickListener(view -> {
            if (gif.isFavorite()) {
                favoriteChanges(gif, false, mFavoritePict);
            } else {
                favoriteChanges(gif, true, mActiveFavoritePict);
            }
        });
        mFavoriteButton.setVisibility(View.VISIBLE);
    }

    private void favoriteChanges(Gif gif, boolean isFavorite, Drawable favoritePict) {
        gif.setFavorite(isFavorite);
        mFavoriteButton.setBackground(favoritePict);
        mCallback.onFavoriteButtonClicked(isFavorite, gif.getId(), getAdapterPosition());
    }

    private void setWatchesButton() {
        mWatchButton.setText(String.valueOf(mGif.getViews()));
        mWatchButton.setOnClickListener(view ->
                mCallback.startFullScreenActivity(mGif));
    }

    private void onGifClickListener() {
        mGifIv.setOnClickListener(view -> {
            if (mGif.isPlaying()) {
                showPoster();
                mGif.setPlaying(false);
            } else {
                tryingSendDetailsRequest();
            }
        });
    }

    private void tryingSendDetailsRequest() {
        if (mIsUserAuthorized && !mGif.isWatched()) {
            mGif.setWatched(true);
            onLoadStart();
            mCallback.getGifInfo(mGif.getId(), getAdapterPosition());
        } else {
            mGif.setWatched(true);
            mGif.setPlaying(true);
            startPlayingGif();
            configureLikesAndFavoritesLayout();
        }
    }

    private void configureLikesAndFavoritesLayout() {
        mFavoriteButton.setVisibility(View.GONE);
        if (mIsUserAuthorized && mGif.isWatched()) {
            setLikeBtnForAuthorizedUser(mGif);
            setFavouriteBtn(mGif);
            mLikeAndFavoriteLayout.setVisibility(View.VISIBLE);
        } else if (!mIsUserAuthorized && mGif.isWatched()) {
            setLikeBtnForUnauthorizedUser(mGif);
            mLikeAndFavoriteLayout.setVisibility(View.VISIBLE);
        } else if (!mGif.isWatched()) {
            mLikeAndFavoriteLayout.setVisibility(View.GONE);
        }
    }

    private void onLoadStart() {
        mPrBarLayout.setVisibility(View.VISIBLE);
    }

    private void onLoadFinished() {
        mPrBarLayout.setVisibility(View.GONE);
        mGifIv.setVisibility(View.VISIBLE);
    }

    private void addTags(List<String> tagsList, @NonNull LinearLayout tagsScrollView) {
        if (tagsScrollView.getChildCount() != 0) {
            tagsScrollView.removeAllViews();
            tagsScrollView.setVisibility(View.GONE);
        }
        if (tagsList != null && !tagsList.isEmpty()) {
            String[] tags = tagsList.toArray(new String[tagsList.size()]);
            LayoutInflater inflater = mCallback.getLayoutInflater();
            for (int i = 0; i < tags.length; i++) {
                View v = inflater.inflate(R.layout.view_tag_layout, tagsScrollView, false);
                LinearLayout tagLayout = (LinearLayout) v.findViewById(R.id.tag_layout);
                TextView tv = (TextView) v.findViewById(R.id.tv_tag);
                tv.setText(tags[i]);
                tagsScrollView.addView(tagLayout, i);
            }
            tagsScrollView.setVisibility(View.VISIBLE);
        }
    }

}
