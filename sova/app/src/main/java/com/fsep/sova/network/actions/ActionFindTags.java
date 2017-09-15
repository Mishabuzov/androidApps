package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Tag;
import com.fsep.sova.network.events.find_tags.FindingTagsErrorEvent;
import com.fsep.sova.network.events.find_tags.FindingTagsIsEmptyEvent;
import com.fsep.sova.network.events.find_tags.FindingTagsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionFindTags extends BaseAction<BaseResponseModel<List<Tag>>> {

    private int mCount;
    private String mFrom;
    private String mFind;

    public ActionFindTags(Builder builder) {
        mCount = builder.count;
        mFrom = builder.from;
        mFind = builder.find;
    }

    protected ActionFindTags(Parcel in) {
        mCount = in.readInt();
        mFrom = in.readString();
        mFind = in.readString();
    }

    @Override
    protected Response<BaseResponseModel<List<Tag>>> makeRequest() throws IOException {
        return getRest().findTags(createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        if (mCount != 0) {
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if (mFrom != null && !mFrom.isEmpty()) {
            options.put("from", mFrom);
        }
        if (mFind != null && !mFind.isEmpty()) {
            options.put("find", mFind);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Tag>>> response) {
        List<Tag> foundedTags = response.body().getData();
        if (!foundedTags.isEmpty()) {
            EventBus.getDefault().post(new FindingTagsIsSuccessEvent(foundedTags));
        } else {
            EventBus.getDefault().post(new FindingTagsIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FindingTagsErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeString(mFrom);
        dest.writeString(mFind);
    }

    public static final Creator<ActionFindTags> CREATOR = new Creator<ActionFindTags>() {
        @Override
        public ActionFindTags createFromParcel(Parcel source) {
            return new ActionFindTags(source);
        }

        @Override
        public ActionFindTags[] newArray(int size) {
            return new ActionFindTags[size];
        }
    };


    public static class Builder {
        private int count;
        private String from;
        private String find;

        public Builder() {
        }

        public Builder count(int val) {
            count = val;
            return this;
        }

        public Builder from(String val) {
            from = val;
            return this;
        }

        public Builder find(String val) {
            find = val;
            return this;
        }

        public ActionFindTags build() {
            return new ActionFindTags(this);
        }

    }
}
