package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.CountryCity;
import com.fsep.sova.network.events.find_cities.FindingCitiesIsErrorEvent;
import com.fsep.sova.network.events.find_cities.FindingCitiesIsSuccessEvent;
import com.fsep.sova.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class ActionFindCity extends BaseAction<BaseResponseModel<List<CountryCity>>>{

    private long mCountryId;
    private int mCount;
    private String mFrom;
    private String mFind;

    public ActionFindCity(long countryId) {
        mCountryId = countryId;
    }

    public ActionFindCity(Builder builder) {
        mCount = builder.count;
        mFrom = builder.from;
        mFind = builder.find;
        mCountryId = builder.countryId;
    }

    protected ActionFindCity(Parcel in) {
        mCountryId = in.readLong();
        mCount = in.readInt();
        mFrom = in.readString();
        mFind = in.readString();
    }

    @Override
    protected Response<BaseResponseModel<List<CountryCity>>> makeRequest() throws IOException {
        return getRest().findCities(mCountryId, createQueryMap()).execute();
    }

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
            EventBus.getDefault().post(new FindingCitiesIsSuccessEvent(foundedCountries));
        } else {
            EventBus.getDefault().post(new FindingCitiesIsErrorEvent());
        }
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new FindingCitiesIsErrorEvent());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mCountryId);
        dest.writeInt(mCount);
        dest.writeString(mFrom);
        dest.writeString(mFind);
    }

    public static final Creator<ActionFindCity> CREATOR = new Creator<ActionFindCity>() {
        @Override
        public ActionFindCity createFromParcel(Parcel source) {
            return new ActionFindCity(source);
        }

        @Override
        public ActionFindCity[] newArray(int size) {
            return new ActionFindCity[size];
        }
    };

    public static class Builder {
        private int count;
        private String from;
        private String find;
        private long countryId;

        public Builder(long countryId) {
            this.countryId = countryId;
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

        public ActionFindCity build() {
            return new ActionFindCity(this);
        }
    }
}
