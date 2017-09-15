package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Post;
import com.fsep.sova.network.events.get_posts_of_another_user_by_id.GettingAnotherUserPostsByIdEmptyEvent;
import com.fsep.sova.network.events.get_posts_of_another_user_by_id.GettingAnotherUserPostsByIdErrorEvent;
import com.fsep.sova.network.events.get_posts_of_another_user_by_id.GettingAnotherUserPostsByIdIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGettingPostsCreatedByAnotherUser extends BaseAction<BaseResponseModel<List<Post>>> {

    private long mUserId;
    private int mCount;
    private int mFrom;

    public ActionGettingPostsCreatedByAnotherUser(Builder builder) {
        mUserId = builder.userId;
        mCount = builder.count;
        mFrom = builder.from;
    }

    @Override
    protected Response<BaseResponseModel<List<Post>>> makeRequest() throws IOException {
        return getRest().getAnotherUserPosts(mUserId, createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        if (mCount != 0) {
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if (mFrom != 0) {
            options.put("from", mFrom);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Post>>> response) {
        List<Post> postList = response.body().getData();
        if (postList.isEmpty()) {
            EventBus.getDefault().post(new GettingAnotherUserPostsByIdEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingAnotherUserPostsByIdIsSuccessEvent(postList));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingAnotherUserPostsByIdErrorEvent(response.code(), response.message()));
    }

    public static class Builder {
        private long userId;
        private int count;
        private int from;

        public Builder(long userId) {
            this.userId = userId;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGettingPostsCreatedByAnotherUser build() {
            return new ActionGettingPostsCreatedByAnotherUser(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mUserId);
        dest.writeInt(this.mCount);
        dest.writeInt(this.mFrom);
    }

    protected ActionGettingPostsCreatedByAnotherUser(Parcel in) {
        this.mUserId = in.readLong();
        this.mCount = in.readInt();
        this.mFrom = in.readInt();
    }

    public static final Creator<ActionGettingPostsCreatedByAnotherUser> CREATOR = new Creator<ActionGettingPostsCreatedByAnotherUser>() {
        @Override
        public ActionGettingPostsCreatedByAnotherUser createFromParcel(Parcel source) {
            return new ActionGettingPostsCreatedByAnotherUser(source);
        }

        @Override
        public ActionGettingPostsCreatedByAnotherUser[] newArray(int size) {
            return new ActionGettingPostsCreatedByAnotherUser[size];
        }
    };
}
