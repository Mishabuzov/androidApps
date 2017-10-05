package com.kpfu.mikhail.vk.screen.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.utils.Function;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaginationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reload_button) Button mReloadButton;

    @BindView(R.id.network_error_view) LinearLayout mNetworkErrorView;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private final Function mReloadFunction;

    public PaginationViewHolder(@NonNull View itemView,
                                @NonNull Function reloadFunction) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mReloadFunction = reloadFunction;
    }

    void bind() {
        mReloadButton.setOnClickListener((v) -> {
            showFooterProgress();
            mReloadFunction.action();
        });
    }

    public void showFooterProgress() {
        mNetworkErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showReloadInterface() {
        mProgressBar.setVisibility(View.GONE);
        mNetworkErrorView.setVisibility(View.VISIBLE);
    }
}
