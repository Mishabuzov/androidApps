package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.FavouriteSendingModel;
import com.fsep.sova.network.events.add_to_favourites.AddingToFavouritesErrorEvent;
import com.fsep.sova.network.events.add_to_favourites.AddingToFavouritesIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionAddToFavourites extends BaseAction<BaseResponseModel> {
    private FavouriteSendingModel mBody;

    public ActionAddToFavourites(FavouriteSendingModel body) {
        mBody = body;
    }

    protected ActionAddToFavourites(Parcel in){
        mBody = in.readParcelable(FavouriteSendingModel.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().addToFavourites(mBody).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new AddingToFavouritesIsSuccessEvent(mBody.getNoteId()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new AddingToFavouritesErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionAddToFavourites> CREATOR = new Creator<ActionAddToFavourites>() {
        @Override
        public ActionAddToFavourites createFromParcel(Parcel source) {
            return new ActionAddToFavourites(source);
        }

        @Override
        public ActionAddToFavourites[] newArray(int size) {
            return new ActionAddToFavourites[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mBody, flags);
    }
}
