package com.kpfu.mikhail.vk.screen.base.activities.base_activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.utils.UiUtils;
import com.kpfu.mikhail.vk.widget.progressbar.LoadingDialog;
import com.kpfu.mikhail.vk.widget.progressbar.LoadingView;

public abstract class BaseActivity extends AppCompatActivity implements BaseActivityView {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    private LoadingView mLoadingView;

    private Window mWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        setupWindow(R.color.window_color);
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
    public void showNetworkErrorMessage() {
        showToastMessage(R.string.network_error_message);
    }

    @Override
    public void showToastMessage(@NonNull String message) {
        UiUtils.showToast(message, this);
    }

    @Override
    public void showToastMessage(@StringRes int message) {
        UiUtils.showToast(message, this);
    }

    private void setupWindow(@ColorRes int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindow();
            setStatusBarColor(statusBarColor);
        }
    }

    private void setupWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWindow = getWindow();
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setStatusBarColor(@ColorRes int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWindow.setStatusBarColor(ContextCompat.getColor(BaseActivity.this, statusBarColor));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
