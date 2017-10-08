package com.kpfu.mikhail.vk.screen.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.utils.Function;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkErrorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reload_button) Button mReloadButton;

    private final Function mReloadFunction;

    private final NetworkHolderCallback mCallback;

    public NetworkErrorViewHolder(@NonNull View itemView,
                                  @NonNull Function reloadFunction,
                                  @NonNull NetworkHolderCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mReloadFunction = reloadFunction;
        mCallback = callback;
    }

    void bind() {
        mReloadButton.setOnClickListener((v) -> {
            mCallback.enablePaginationView(true);
            mReloadFunction.action();
        });
    }

    interface NetworkHolderCallback {
        void enablePaginationView(boolean isPaginationEnabled);
    }

}
