package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.UserInfo;
import com.fsep.sova.network.events.find_users.FindingUsersErrorEvent;
import com.fsep.sova.network.events.find_users.FindingUsersIsEmptyEvent;
import com.fsep.sova.network.events.find_users.FindingUsersIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionFindUsers extends BaseAction<BaseResponseModel<List<UserInfo>>> {
    private int mCount;
    private int mFrom;
    private String mFind;
    private String mBy;

    public ActionFindUsers(Builder builder) {
        mCount = builder.count;
        mFind = builder.find;
        mFrom = builder.from;
        mBy = builder.by;
    }

    protected ActionFindUsers(Parcel in) {
        mCount = in.readInt();
        mFrom = in.readInt();
        mFind = in.readString();
        mBy = in.readString();
    }

    @Override
    protected Response<BaseResponseModel<List<UserInfo>>> makeRequest() throws IOException {
        return getRest().findUsers(createQueryMap()).execute();
    }

    private Map<String, Object> createQueryMap(){
        Map<String, Object> options = new HashMap<>();
        if(mCount!=0){
            options.put("count", mCount);
        } else {
            options.put("count", Constants.DEFAULT_COUNT_VALUE);
        }
        if (mFind != null && !mFind.isEmpty()) {
            options.put("find", mFind);
        }
        if(mFrom!=0){
            options.put("from", mFrom);
        }
        if (mBy != null && !mBy.isEmpty()) {
            options.put("by", mBy);
        }
        return options;
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<List<UserInfo>>> response) {
        List<UserInfo> users = response.body().getData();
        if(users.size()>0){
            EventBus.getDefault().post(new FindingUsersIsSuccessEvent(users));
        } else{
            EventBus.getDefault().post(new FindingUsersIsEmptyEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FindingUsersErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionFindUsers> CREATOR = new Creator<ActionFindUsers>() {
        @Override
        public ActionFindUsers createFromParcel(Parcel source) {
            return new ActionFindUsers(source);
        }

        @Override
        public ActionFindUsers[] newArray(int size) {
            return new ActionFindUsers[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeInt(mFrom);
        dest.writeString(mFind);
        dest.writeString(mBy);
    }

    public static class Builder{
        private String find;
        private int count;
        private int from;
        private String by;

        public Builder(){}

        public Builder find(String val){
            find = val;
            return this;
        }

        public Builder count(int val){
            count = val;
            return this;
        }

        public Builder from(int val){
            from = val;
            return this;
        }

        public Builder by(String val) {
            by = val;
            return this;
        }

        public ActionFindUsers build() {
            return new ActionFindUsers(this);
        }
    }
}
