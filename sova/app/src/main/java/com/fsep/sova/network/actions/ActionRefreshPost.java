package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Post;
import com.fsep.sova.network.events.refresh_post.RefreshingPostErrorEvent;
import com.fsep.sova.network.events.refresh_post.RefreshingPostIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionRefreshPost extends BaseAction<BaseResponseModel<Post>> {

    private long mPostId;
    private Post mPost;

    public ActionRefreshPost(long postId, Post post) {
        mPostId = postId;
        mPost = post;
    }

    @Override
    protected Response<BaseResponseModel<Post>> makeRequest() throws IOException {
        return getRest().refreshPost(mPostId, mPost).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Post>> response) {
        EventBus.getDefault().post(new RefreshingPostIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new RefreshingPostErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mPostId);
        dest.writeParcelable(this.mPost, flags);
    }

    protected ActionRefreshPost(Parcel in) {
        this.mPostId = in.readLong();
        this.mPost = in.readParcelable(Post.class.getClassLoader());
    }

    public static final Creator<ActionRefreshPost> CREATOR = new Creator<ActionRefreshPost>() {
        @Override
        public ActionRefreshPost createFromParcel(Parcel source) {
            return new ActionRefreshPost(source);
        }

        @Override
        public ActionRefreshPost[] newArray(int size) {
            return new ActionRefreshPost[size];
        }
    };
}
