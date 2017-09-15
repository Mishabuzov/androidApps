package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.delete_post.DeletingPostErrorEvent;
import com.fsep.sova.network.events.delete_post.DeletingPostIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeletePost extends BaseAction<BaseResponseModel> {

    private long mPostId;

    public ActionDeletePost(long postId) {
        mPostId = postId;
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().deletePost(mPostId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new DeletingPostIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new DeletingPostErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mPostId);
    }

    protected ActionDeletePost(Parcel in) {
        this.mPostId = in.readLong();
    }

    public static final Creator<ActionDeletePost> CREATOR = new Creator<ActionDeletePost>() {
        @Override
        public ActionDeletePost createFromParcel(Parcel source) {
            return new ActionDeletePost(source);
        }

        @Override
        public ActionDeletePost[] newArray(int size) {
            return new ActionDeletePost[size];
        }
    };
}
