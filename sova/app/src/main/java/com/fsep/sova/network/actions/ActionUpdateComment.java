package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.CommentSendingModel;
import com.fsep.sova.network.events.update_comment.UpdatingCommentErrorEvent;
import com.fsep.sova.network.events.update_comment.UpdatingCommentIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionUpdateComment extends BaseAction {

    private long mTaskId;
    private long mCommentId;
    private CommentSendingModel mBody;

    public ActionUpdateComment(long taskId, long commentId, CommentSendingModel body) {
        mTaskId = taskId;
        mCommentId = commentId;
        mBody = body;
    }

    protected ActionUpdateComment(Parcel in) {
        mTaskId = in.readLong();
        mCommentId = in.readLong();
        mBody = in.readParcelable(CommentSendingModel.class.getClassLoader());
    }

    @Override
    protected Response makeRequest() throws IOException {
        return getRest().updateComment(mTaskId, mCommentId, mBody).execute();
    }

    @Override
    protected void onResponseSuccess(Response response) {
        EventBus.getDefault().post(new UpdatingCommentIsSuccessEvent());
    }

    @Override
    protected void onHttpError(Response response) {
        EventBus.getDefault().post(new UpdatingCommentErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionUpdateComment> CREATOR = new Creator<ActionUpdateComment>() {
        @Override
        public ActionUpdateComment createFromParcel(Parcel in) {
            return new ActionUpdateComment(in);
        }

        @Override
        public ActionUpdateComment[] newArray(int size) {
            return new ActionUpdateComment[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mCommentId);
        parcel.writeLong(mTaskId);
        parcel.writeParcelable(mBody, i);
    }
}
