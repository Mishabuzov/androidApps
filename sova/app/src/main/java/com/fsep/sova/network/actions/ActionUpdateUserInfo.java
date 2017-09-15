package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.UpdateUserInfoModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.events.update_user_info.UpdatingUserInfoErrorEvent;
import com.fsep.sova.network.events.update_user_info.UpdatingUserInfoIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionUpdateUserInfo extends BaseAction<BaseResponseModel<UserInfo>> {

    private long mUserId;
    private UpdateUserInfoModel mUserForUpdating;

    public ActionUpdateUserInfo(long userId, UpdateUserInfoModel userForUpdating) {
        mUserId = userId;
        mUserForUpdating = userForUpdating;
    }

    @Override
    protected Response<BaseResponseModel<UserInfo>> makeRequest() throws IOException {
        return getRest().updateUserInfo(mUserId, mUserForUpdating).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<UserInfo>> response) {
        EventBus.getDefault().post(new UpdatingUserInfoIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new UpdatingUserInfoErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mUserId);
        dest.writeParcelable(this.mUserForUpdating, flags);
    }

    public ActionUpdateUserInfo() {
    }

    protected ActionUpdateUserInfo(Parcel in) {
        this.mUserId = in.readLong();
        this.mUserForUpdating = in.readParcelable(UpdateUserInfoModel.class.getClassLoader());
    }

    public static final Creator<ActionUpdateUserInfo> CREATOR = new Creator<ActionUpdateUserInfo>() {
        @Override
        public ActionUpdateUserInfo createFromParcel(Parcel source) {
            return new ActionUpdateUserInfo(source);
        }

        @Override
        public ActionUpdateUserInfo[] newArray(int size) {
            return new ActionUpdateUserInfo[size];
        }
    };
}
