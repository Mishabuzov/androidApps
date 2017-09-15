package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Post;
import com.fsep.sova.network.events.get_post_by_id.GettingPostByIdErrorEvent;
import com.fsep.sova.network.events.get_post_by_id.GettingPostByIdIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionGetPostById extends BaseAction<BaseResponseModel<Post>> {

    private long mPostId;

    public ActionGetPostById(long postId) {
        mPostId = postId;
    }

    @Override
    protected Response<BaseResponseModel<Post>> makeRequest() throws IOException {
        return getRest().getPostInDetailsById(mPostId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Post>> response) {
        EventBus.getDefault().post(new GettingPostByIdIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingPostByIdErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mPostId);
    }

    protected ActionGetPostById(Parcel in) {
        this.mPostId = in.readLong();
    }

    public static final Creator<ActionGetPostById> CREATOR = new Creator<ActionGetPostById>() {
        @Override
        public ActionGetPostById createFromParcel(Parcel source) {
            return new ActionGetPostById(source);
        }

        @Override
        public ActionGetPostById[] newArray(int size) {
            return new ActionGetPostById[size];
        }
    };
}
