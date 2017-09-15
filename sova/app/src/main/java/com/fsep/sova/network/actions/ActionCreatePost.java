package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Post;
import com.fsep.sova.network.events.create_post.CreatingPostErrorEvent;
import com.fsep.sova.network.events.create_post.CreatingPostIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionCreatePost extends BaseAction<BaseResponseModel<Post>> {

    private Post mPost;

    public ActionCreatePost(Post post) {
        mPost = post;
    }

    @Override
    protected Response<BaseResponseModel<Post>> makeRequest() throws IOException {
        return getRest().createPost(mPost).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Post>> response) {
        EventBus.getDefault().post(new CreatingPostIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new CreatingPostErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPost, flags);
    }

    protected ActionCreatePost(Parcel in) {
        this.mPost = in.readParcelable(Post.class.getClassLoader());
    }

    public static final Creator<ActionCreatePost> CREATOR = new Creator<ActionCreatePost>() {
        @Override
        public ActionCreatePost createFromParcel(Parcel source) {
            return new ActionCreatePost(source);
        }

        @Override
        public ActionCreatePost[] newArray(int size) {
            return new ActionCreatePost[size];
        }
    };
}
