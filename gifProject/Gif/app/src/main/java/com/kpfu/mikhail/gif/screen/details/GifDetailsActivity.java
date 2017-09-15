package com.kpfu.mikhail.gif.screen.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.Gif;
import com.kpfu.mikhail.gif.screen.general.LoadingDialog;
import com.kpfu.mikhail.gif.screen.general.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class GifDetailsActivity extends AppCompatActivity implements GifDetailsView {

    @BindView(R.id.btn_eye) Button mBtnEye;

    @BindView(R.id.btn_like) Button mBtnLike;

    @BindView(R.id.btn_favourite) Button mBtnFavorite;

    @BindView(R.id.title_tv) TextView mTitleTv;

    @BindView(R.id.gif_iv) ImageView mGifIv;

    @BindView(R.id.pr_bar_layout) LinearLayout mPrBar;
    @BindView(R.id.tags_scroll_view) LinearLayout mTagsView;

    @BindView(R.id.functional_layout) LinearLayout mFunctional;

    @BindView(R.id.descr_tv) TextView mDescriptionTv;

    public static final String GIF_KEY = "gif";

    private Gif mGif;

    private GifDetailsPresenter mPresenter;

    private LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_details);
        ButterKnife.bind(this);
        initActivityElements();
        mGif = getIntent().getParcelableExtra(GIF_KEY);
        mPresenter.setInfoIntoViews();
    }

    private void initActivityElements() {
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new GifDetailsPresenter(this);
    }

    public static void startDetailsActivity(@NonNull Activity activity, @NonNull Gif gif) {
        Intent intent = new Intent(activity, GifDetailsActivity.class);
        intent.putExtra(GIF_KEY, gif);
        activity.startActivity(intent);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void bindViews() {

    }
}
