package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.CountryCity;
import com.fsep.sova.models.Tag;
import com.fsep.sova.network.events.find_countries.FindingCountryIsErrorEvent;
import com.fsep.sova.network.events.find_countries.FindingCountryIsSuccessEvent;
import com.fsep.sova.network.events.find_tags.FindingTagsIsEmptyEvent;
import com.fsep.sova.network.events.find_tags.FindingTagsIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionFindCountry extends BaseAction<BaseResponseModel<List<CountryCity>>> {

    private int mCount;
    private String mFrom;
    private String mFind;

    public ActionFindCountry(Builder builder) {
        mCount = builder.count;
        mFrom = builder.from;
        mFind = builder.find;
    }

    protected ActionFindCountry(Parcel in) {
        mCount = in.readInt();
        mFrom = in.readString();
        mFind = in.readString();
    }

    @Override
    protected Response<BaseResponseModel<List<CountryCity>>> makeRequest() throws IOException {
        return getRest().findCountries(createQueryMap()).execute();
    }

    public static final Creator<ActionFindCountry> CREATOR = new Creator<ActionFindCountry>() {
        @Override
        public ActionFindCountry createFromParcel(Parcel source) {
            return new ActionFindCountry(source);
        }

        @Override
        public ActionFindCountry[] newArray(int size) {
            return new ActionFindCountry[size];
        }
    };

    private Map<String, Object> createQueryMap() {
        Map<String, Object> options = new HashMap<>();
        if (mCount != 0) {
            options.put("limit", mCount);
        } else {
            options.put("limit", Constants.DEFAULT_COUNT_VALUE);
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
    protected void onResponseSuccess(Response<BaseResponseModel<List<CountryCity>>> response) {
        List<CountryCity> foundedCountries = response.body().getData();
        if (!foundedCountries.isEmpty()) {
            EventBus.getDefault().post(new FindingCountryIsSuccessEvent(foundedCountries));
        } else {
            EventBus.getDefault().post(new FindingCountryIsErrorEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FindingCountryIsErrorEvent());
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

        public ActionFindCountry build() {
            return new ActionFindCountry(this);
        }
    }

}
