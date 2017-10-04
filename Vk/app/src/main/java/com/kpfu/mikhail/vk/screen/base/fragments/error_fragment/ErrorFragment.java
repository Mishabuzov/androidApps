package com.kpfu.mikhail.vk.screen.base.fragments.error_fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.NetworkErrorType;
import com.kpfu.mikhail.vk.screen.base.activities.base_fragment_activity.BaseFragmentActivity;
import com.kpfu.mikhail.vk.screen.login.LoginActivity;
import com.kpfu.mikhail.vk.utils.AndroidUtils;
import com.kpfu.mikhail.vk.utils.Function;
import com.kpfu.mikhail.vk.utils.UiUtils;
import com.kpfu.mikhail.vk.utils.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import retrofit2.HttpException;

import static com.kpfu.mikhail.vk.content.NetworkErrorType.HTTP_EXCEPTION;
import static com.kpfu.mikhail.vk.content.NetworkErrorType.LOGIN_EXCEPTION;
import static com.kpfu.mikhail.vk.content.NetworkErrorType.SERVER_EXCEPTION;
import static com.kpfu.mikhail.vk.content.NetworkErrorType.UNEXPECTED_ERROR;

public abstract class ErrorFragment extends Fragment implements ErrorView {

    private final List<Class<?>> mNetworkExceptions =
            Arrays.asList(UnknownHostException.class, SocketTimeoutException.class,
                    ConnectException.class);

    private void processError(@NonNull NetworkErrorType networkErrorType,
                              @NonNull Throwable error,
                              @NonNull Function reloadFunction) {
        Logger.w(networkErrorType.getLogMessage() + error.getMessage());
        setErrorScreenWithRestartButton(reloadFunction, networkErrorType.getExceptionMessage());
    }

    @Override
    public void showNetworkErrorMessage() {
        showToastMessage(R.string.network_error_message);
    }

    @Override
    public void showToastMessage(@NonNull String message) {
        UiUtils.showToast(message, getActivity());
    }

    @Override
    public void showToastMessage(@StringRes int message) {
        UiUtils.showToast(message, getActivity());
    }

    private void setErrorScreenWithRestartButton(Function reloadFunction,
                                                 @StringRes int errorText) {
        ((BaseFragmentActivity) getActivity())
                .setErrorScreenWithReloadButton(reloadFunction, errorText);
    }

    protected void handleNetworkError(Function reloadFunction) {
        setErrorScreenWithRestartButton(reloadFunction, R.string.network_error_message);
    }

    @Override
    public void handleError(Throwable e, Function reloadFunction) {
        if (e instanceof HttpException) {
            handleHttpException((HttpException) e, reloadFunction);
        } else if (mNetworkExceptions.contains(e.getClass())) {
            handleNetworkError(reloadFunction);
        } else {
            processError(UNEXPECTED_ERROR, e, reloadFunction);
        }
    }

    private void processLoginError(@NonNull HttpException httpException) {
        Logger.w(LOGIN_EXCEPTION.getLogMessage() + httpException.getMessage());
        cleanDataAndExit();
        showToastMessage(LOGIN_EXCEPTION.getExceptionMessage());
    }

    private void handleHttpException(@NonNull HttpException httpException,
                                     @Nullable Function reloadFunction) {
        if (httpException.code() == LOGIN_EXCEPTION.getExceptionCode()) {
            processLoginError(httpException);
            return;
        }
        AndroidUtils.checkNotNull(reloadFunction);
        if (httpException.code() == HTTP_EXCEPTION.getExceptionCode()) {
            processError(HTTP_EXCEPTION, httpException, reloadFunction);
        } else if (httpException.code() == SERVER_EXCEPTION.getExceptionCode()) {
            processError(SERVER_EXCEPTION, httpException, reloadFunction);
        } else {
            processError(UNEXPECTED_ERROR, httpException, reloadFunction);
        }
    }

    @Override
    public void cleanDataAndExit() {
        AndroidUtils.clearAllSavedData();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

}
