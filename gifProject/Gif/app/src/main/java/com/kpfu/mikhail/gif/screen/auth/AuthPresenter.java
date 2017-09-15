package com.kpfu.mikhail.gif.screen.auth;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.LoginForm;
import com.kpfu.mikhail.gif.repository.GifProvider;
import com.kpfu.mikhail.gif.utils.PreferenceUtils;

import ru.arturvasilov.rxloader.LifecycleHandler;

class AuthPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final AuthView mAuthView;

    AuthPresenter(@NonNull LifecycleHandler lifecycleHandler,
                  @NonNull AuthView authView) {
        mLifecycleHandler = lifecycleHandler;
        mAuthView = authView;

    }

    void sendAuthRequest(LoginForm loginForm) {
        GifProvider.provideGifRepository()
                .authorize(loginForm)
                .doOnSubscribe(mAuthView::showLoading)
                .doOnTerminate(mAuthView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.auth_request))
                .subscribe(token -> {
                            PreferenceUtils.saveUserName(loginForm.getUsername());
                            mAuthView.onSuccessAuth(token);
                        },
                        throwable -> mAuthView.onLoginError(throwable.getMessage()));
    }

}
