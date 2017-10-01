package com.kpfu.mikhail.vk.screen.login;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kpfu.mikhail.vk.content.Profile;
import com.kpfu.mikhail.vk.repository.VkProvider;
import com.kpfu.mikhail.vk.screen.base.BasePresenter;
import com.kpfu.mikhail.vk.utils.PreferenceUtils;
import com.kpfu.mikhail.vk.utils.logger.Logger;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKList;

import java.io.IOException;
import java.util.List;

import static com.kpfu.mikhail.vk.content.NetworkErrorType.LOGIN_EXCEPTION;

public class LoginPresenter extends BasePresenter<LoginView, Profile> {

    private final LoginView mLoginView;

    private final String mLoginErrorMessage;

    LoginPresenter(@NonNull LoginView loginView,
                   @NonNull String loginErrorMessage) {
        super(loginView);
        mLoginView = loginView;
        mLoginErrorMessage = loginErrorMessage;
    }

    @Override
    public void connectData() {
        processRequest(VkProvider.provideVkRepository().getCurrentUserInfo());
    }

    @Override
    protected void showData(List data) {
    }

    @Override
    protected void onRequestSuccess(VKResponse response) {
        PreferenceUtils.saveUserProfile(parseUserProfile(((VKList) response.parsedModel).get(0)));
        mLoginView.openFeedScreen();
    }

    private Profile parseUserProfile(VKApiModel vkProfile) {
        ObjectMapper mapper = new ObjectMapper();
        Profile userProfile = null;
        try {
            userProfile = mapper.readValue(vkProfile.fields.toString(), Profile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userProfile;
    }

    @Override
    protected void onRequestError(VKError error) {
        onLoginError(error);
    }

    void onLoginSuccess(String token) {
        PreferenceUtils.saveToken(token);
        connectData();
    }

    void onLoginError(VKError error) {
        mLoginView.showToastMessage(mLoginErrorMessage);
        Logger.w(LOGIN_EXCEPTION.getLogMessage() + error.errorMessage);
        mLoginView.finish();
    }

}
