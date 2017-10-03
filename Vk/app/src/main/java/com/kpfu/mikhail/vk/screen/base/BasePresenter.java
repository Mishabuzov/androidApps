package com.kpfu.mikhail.vk.screen.base;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.List;

public abstract class BasePresenter<View extends BaseView, P extends Parcelable> {

    private final View mView;

    public BasePresenter(@NonNull View view) {
        mView = view;
    }

    public void processData(List<P> data) {
        if (data == null) {
            connectData();
        } else {
            showData(data);
        }
    }

    public abstract void connectData();

    protected abstract void showData(List<P> data);

    protected void processRequest(VKRequest request) {
        mView.showLoading();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                onRequestSuccess(response);
            }

            @Override
            public void onError(VKError error) {
                onRequestError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {

            }
        });
    }

    protected abstract void onRequestSuccess(VKResponse response);

    protected abstract void onRequestError(VKError error);

}
