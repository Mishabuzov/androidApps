package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.network.events.get_favourite_tasks.GettingFavouriteNotesIsSuccessEvent;
import com.fsep.sova.network.events.get_favourite_tasks.GettingFavouriteTasksErrorEvent;
import com.fsep.sova.network.events.get_favourite_tasks.GettingFavouriteTasksIsEmptyEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionGetFavouriteTasks extends BaseAction<BaseResponseModel<List<Note>>> {
    private int mCount;
    private int mFrom;

    public ActionGetFavouriteTasks() {
    }

    public ActionGetFavouriteTasks(Builder builder){
        mCount = builder.count;
        mFrom = builder.from;
    }

    protected ActionGetFavouriteTasks(Parcel in){
        mCount = in.readInt();
        mFrom = in.readInt();
    }

    @Override
    protected Response<BaseResponseModel<List<Note>>> makeRequest() throws IOException {
        return getRest().getFavouriteTasks(createQueryMap()).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<Note>>> response) {
        List<Note> notes = response.body().getData();
        if (notes.size() > 0) {
            EventBus.getDefault().post(new GettingFavouriteNotesIsSuccessEvent(notes));
        } else{
            EventBus.getDefault().post(new GettingFavouriteTasksIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new GettingFavouriteTasksErrorEvent(response.code(), response.message()));
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

    public static final Creator<ActionGetFavouriteTasks> CREATOR = new Creator<ActionGetFavouriteTasks>() {
        @Override
        public ActionGetFavouriteTasks createFromParcel(Parcel source) {
            return new ActionGetFavouriteTasks(source);
        }

        @Override
        public ActionGetFavouriteTasks[] newArray(int size) {
            return new ActionGetFavouriteTasks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

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

        public ActionGetFavouriteTasks build(){
            return new ActionGetFavouriteTasks(this);
        }
    }
}
