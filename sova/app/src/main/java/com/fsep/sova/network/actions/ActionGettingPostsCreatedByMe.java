package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Post;
import com.fsep.sova.network.events.get_posts_created_by_me.GettingPostsCreatedByMeEmptyEvent;
import com.fsep.sova.network.events.get_posts_created_by_me.GettingPostsCreatedByMeErrorEvent;
import com.fsep.sova.network.events.get_posts_created_by_me.GettingPostsCreatedByMeIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGettingPostsCreatedByMe extends BaseAction<BaseResponseModel<List<Post>>> {

    private boolean mPublished;
    private int mCount;
    private int mFrom;

    ActionGettingPostsCreatedByMe(Builder builder) {
        mPublished = builder.published;
        mCount = builder.count;
        mFrom = builder.from;
    }

    @Override
    protected Response<BaseResponseModel<List<Post>>> makeRequest() throws IOException {
        return getRest().getPostsCreatedByMe(createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        options.put("published", mPublished);
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
            EventBus.getDefault().post(new GettingPostsCreatedByMeEmptyEvent());
        } else {
            EventBus.getDefault().post(new GettingPostsCreatedByMeIsSuccessEvent(postList));
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingPostsCreatedByMeErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.mPublished ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mCount);
        dest.writeInt(this.mFrom);
    }

    protected ActionGettingPostsCreatedByMe(Parcel in) {
        this.mPublished = in.readByte() != 0;
        this.mCount = in.readInt();
        this.mFrom = in.readInt();
    }

    public static final Creator<ActionGettingPostsCreatedByMe> CREATOR = new Creator<ActionGettingPostsCreatedByMe>() {
        @Override
        public ActionGettingPostsCreatedByMe createFromParcel(Parcel source) {
            return new ActionGettingPostsCreatedByMe(source);
        }

        @Override
        public ActionGettingPostsCreatedByMe[] newArray(int size) {
            return new ActionGettingPostsCreatedByMe[size];
        }
    };

    public static class Builder {
        private boolean published;
        private int count;
        private int from;

        public Builder(boolean published) {
            this.published = published;
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(int val) {
            from = val;
            return this;
        }

        public ActionGettingPostsCreatedByMe build() {
            return new ActionGettingPostsCreatedByMe(this);
        }
    }
}
