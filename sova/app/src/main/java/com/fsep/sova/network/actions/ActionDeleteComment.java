package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.network.events.delete_comment.DeletingCommentErrorEvent;
import com.fsep.sova.network.events.delete_comment.DeletingCommentIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionDeleteComment extends BaseAction {

    private long mCommentId;
    private long mTaskId;

    public ActionDeleteComment(long commentId, long taskId) {
        mCommentId = commentId;
        mTaskId = taskId;
    }

    protected ActionDeleteComment(Parcel in) {
        mCommentId = in.readLong();
        mTaskId = in.readLong();
    }

    @Override
    protected Response makeRequest() throws IOException {
        return getRest().deleteComment(mTaskId, mCommentId).execute();
    }

    @Override
    protected void onResponseSuccess(Response response) {
        EventBus.getDefault().post(new DeletingCommentIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response response) {
        EventBus.getDefault().post(new DeletingCommentErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionDeleteComment> CREATOR = new Creator<ActionDeleteComment>() {
        @Override
        public ActionDeleteComment createFromParcel(Parcel in) {
            return new ActionDeleteComment(in);
        }

        @Override
        public ActionDeleteComment[] newArray(int size) {
            return new ActionDeleteComment[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTaskId);
        parcel.writeLong(mCommentId);
    }
}
