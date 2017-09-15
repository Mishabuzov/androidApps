package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.network.events.get_feed.GettingFeedErrorEvent;
import com.fsep.sova.network.events.get_feed.GettingFeedIsEmptyEvent;
import com.fsep.sova.network.events.get_feed.GettingFeedIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetFeed extends BaseAction<BaseResponseModel<List<Note>>> {
    private int mCount;
    private int mFrom;

    public ActionGetFeed() {
    }

    public ActionGetFeed(Builder builder){
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetFeed(Parcel in){
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Note>>> makeRequest() throws IOException {
        return getRest().getFeed(createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap(){
        Map<String, Object> options = new HashMap<>();
        if(mCount!=0){
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if(mFrom!=0){
            options.put("from", mFrom);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Note>>> response) {
        List<Note> notes = response.body().getData();
        if (notes.size() > 0) {
            EventBus.getDefault().post(new GettingFeedIsSuccessEvent(notes));
        } else {
            EventBus.getDefault().post(new GettingFeedIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingFeedErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionGetFeed> CREATOR = new Creator<ActionGetFeed>() {
        @Override
        public ActionGetFeed createFromParcel(Parcel source) {
            return new ActionGetFeed(source);
        }

        @Override
        public ActionGetFeed[] newArray(int size) {
            return new ActionGetFeed[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
    }

    public static class Builder{
        private int count;
        private int from;

        public Builder() {
        }

        public Builder count(int val){
            count = val;
            return this;
        }

        public Builder from(int val){
            from = val;
            return this;
        }

        public ActionGetFeed build(){
            return new ActionGetFeed(this);
        }
    }
}
