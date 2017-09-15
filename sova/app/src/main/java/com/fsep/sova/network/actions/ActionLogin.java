package com.fsep.sova.network.actions;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.events.login.AuthIsSuccess;
import com.fsep.sova.network.events.login.LoginErrorEvent;
import com.fsep.sova.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionLogin extends BaseAction<BaseResponseModel<UserInfo>> {

    private String mLogin;
    private String mPassword;

    public ActionLogin(@NonNull String login, @NonNull String password) {
        mLogin = login;
        mPassword = password;
    }

    public ActionLogin(Parcel in) {
        mLogin = in.readString();
        mPassword = in.readString();
    }

    @Override
    protected Response<BaseResponseModel<UserInfo>> makeRequest() throws IOException {
        return getRest().login(mLogin, mPassword).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<UserInfo>> response) {
        UserInfo authorizedUser = response.body().getData();
        saveUserInfo(authorizedUser, response.headers().get("auth-token"));
        EventBus.getDefault().post(new AuthIsSuccess());
    }

    private void saveUserInfo(UserInfo authorizedUser, String authToken) {
        PrefUtils.saveAuthToken(context, authToken);
        PrefUtils.saveUserId(context, authorizedUser.getId());
        PrefUtils.saveUserName(context, authorizedUser.getFirstName());
        PrefUtils.saveUserSurname(context, authorizedUser.getLastName());
        PrefUtils.saveUserAvatar(context, authorizedUser.getAvatar().getOriginalUrl());
        PrefUtils.saveUserCounts(context, PrefUtils.FOLLOWERS_COUNT, authorizedUser.getFollowersCount());
        PrefUtils.saveUserCounts(context, PrefUtils.FOLLOWINGS_COUNT, authorizedUser.getFollowingsCount());
        PrefUtils.saveUserCounts(context, PrefUtils.POSTS_COUNT, authorizedUser.getPostsCount());
        PrefUtils.saveUserDescription(context, authorizedUser.getDescription());
        PrefUtils.saveUserEmail(context, authorizedUser.getNickName());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new LoginErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLogin);
        dest.writeString(mPassword);
    }

    public static final Parcelable.Creator<ActionLogin> CREATOR = new Parcelable.Creator<ActionLogin>() {
        @Override
        public ActionLogin createFromParcel(Parcel in) {
            return new ActionLogin(in);
        }

        @Override
        public ActionLogin[] newArray(int size) {
            return new ActionLogin[size];
        }
    };
}
