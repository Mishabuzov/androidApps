package com.kpfu.mikhail.gif.screen.full_screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.Gif;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenActivity extends AppCompatActivity implements FullScreenView {

    @BindString(R.string.error_load_gif) String mErrorMsg;

    @BindView(R.id.full_screen_image) ImageView mFullScreenImage;

    @BindView(R.id.error_tv) TextView mErrorTv;

    @BindView(R.id.pr_bar_layout) LinearLayout mPrBarLayout;

    private Gif mGif;

    private FullScreenPresenter mPresenter;

    public static final String GIF_KEY = "gif";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        ButterKnife.bind(this);
        initActivityParams();
        mPresenter.startLoadingGif();
    }

    private void initActivityParams() {
        mGif = getIntent().getParcelableExtra(GIF_KEY);
        mPresenter = new FullScreenPresenter(this);
    }

    @Override
    public void loadGif() {
        Glide
                .with(this)
                .load(mGif.getHighUrl())
                .asGif()
                .listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                        mErrorTv.setVisibility(View.VISIBLE);
                        mErrorTv.setText(mErrorMsg);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(com.bumptech.glide.load.resource.gif.GifDrawable resource, String model, Target<com.bumptech.glide.load.resource.gif.GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        hideLoading();
                        return false;
                    }
                })
                .skipMemoryCache(true)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mFullScreenImage);
    }

    public static void startFullScreenActivity(@NonNull Activity activity, @NonNull Gif gif) {
        Intent intent = new Intent(activity, FullScreenActivity.class);
        intent.putExtra(GIF_KEY, gif);
        activity.startActivity(intent);
    }

    @Override
    public void showLoading() {
        mPrBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPrBarLayout.setVisibility(View.GONE);
    }

}
