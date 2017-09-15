package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Note;
import com.fsep.sova.network.events.put_like_to_note.PuttingLikeErrorEvent;
import com.fsep.sova.network.events.put_like_to_note.PuttingLikeIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionPutLike extends BaseAction<BaseResponseModel<Note>> {

    private long mNoteId;
    private String mAction;

    public ActionPutLike(long noteId) {
        mNoteId = noteId;
        mAction = "like";
    }

    @Override
    protected Response<BaseResponseModel<Note>> makeRequest() throws IOException {
        return getRest().putLike(mNoteId, mAction).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Note>> response) {
        EventBus.getDefault().post(new PuttingLikeIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new PuttingLikeErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mNoteId);
        dest.writeString(this.mAction);
    }

    protected ActionPutLike(Parcel in) {
        this.mNoteId = in.readLong();
        this.mAction = in.readString();
    }

    public static final Creator<ActionPutLike> CREATOR = new Creator<ActionPutLike>() {
        @Override
        public ActionPutLike createFromParcel(Parcel source) {
            return new ActionPutLike(source);
        }

        @Override
        public ActionPutLike[] newArray(int size) {
            return new ActionPutLike[size];
        }
    };
}
