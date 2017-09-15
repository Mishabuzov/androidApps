package com.kpfu.mikhail.gif.screen.register;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.gif.R;
import com.kpfu.mikhail.gif.content.RegistrationForm;
import com.kpfu.mikhail.gif.content.Token;
import com.kpfu.mikhail.gif.repository.GifProvider;
import com.kpfu.mikhail.gif.utils.PreferenceUtils;

import ru.arturvasilov.rxloader.LifecycleHandler;

class RegistrationPresenter {

    private final LifecycleHandler mLifecycleHandler;

    private final RegisterView mRegisterView;

    RegistrationPresenter(@NonNull LifecycleHandler lifecycleHandler,
                          @NonNull RegisterView registerView) {
        mLifecycleHandler = lifecycleHandler;
        mRegisterView = registerView;
    }

    void sendRegisterRequest(RegistrationForm registrationForm, String tokenFormat, String tokenPrescription) {
        GifProvider.provideGifRepository()
                .getRegistrationKey()
                .doOnSubscribe(mRegisterView::showLoading)
                .compose(mLifecycleHandler.reload(R.id.reg_token_request))
                .subscribe(token -> registerUser(token, registrationForm, tokenFormat, tokenPrescription),
                        throwable -> mRegisterView.onError(throwable.getMessage()));
    }

    private void registerUser(Token token, RegistrationForm registrationForm, String tokenFormat, String tokenPrescription) {
        saveToken(String.format(tokenFormat, tokenPrescription, token.getStringToken()));
        GifProvider.provideGifRepository()
                .registerUser(registrationForm)
                .doOnTerminate(mRegisterView::hideLoading)
                .compose(mLifecycleHandler.reload(R.id.reg_user_request))
                .subscribe(validToken -> {
                    saveToken(validToken.getStringToken());
                    PreferenceUtils.saveUserName(registrationForm.getUsername());
                    mRegisterView.onSuccessRegistered();
                }, throwable -> mRegisterView.onError(throwable.getMessage()));
    }

    private void saveToken(String token) {
        PreferenceUtils.saveToken(token);
    }
}
