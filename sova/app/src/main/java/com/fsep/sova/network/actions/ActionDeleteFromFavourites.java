package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.network.events.delete_from_favourites.DeletingFromFavouritesErrorEvent;
import com.fsep.sova.network.events.delete_from_favourites.DeletingFromFavouritesIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeleteFromFavourites extends BaseAction<BaseResponseModel> {
    private long mNoteId;

    public ActionDeleteFromFavourites(long noteId) {
        mNoteId = noteId;
    }

    protected ActionDeleteFromFavourites(Parcel in){
        mNoteId = in.readLong();
    }

    @Override
    protected Response<BaseResponseModel> makeRequest() throws IOException {
        return getRest().deleteFromFavourites(mNoteId).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel> response) {
        EventBus.getDefault().post(new DeletingFromFavouritesIsSuccessEvent(mNoteId));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new DeletingFromFavouritesErrorEvent(response.code(), response.message()));
    }

    public static final Creator<ActionDeleteFromFavourites> CREATOR = new Creator<ActionDeleteFromFavourites>() {
        @Override
        public ActionDeleteFromFavourites createFromParcel(Parcel source) {
            return new ActionDeleteFromFavourites(source);
        }

        @Override
        public ActionDeleteFromFavourites[] newArray(int size) {
            return new ActionDeleteFromFavourites[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mNoteId);
    }
}
