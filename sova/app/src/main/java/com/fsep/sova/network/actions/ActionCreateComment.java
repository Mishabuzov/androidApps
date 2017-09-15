package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Comment;
import com.fsep.sova.models.CommentSendingModel;
import com.fsep.sova.network.events.create_comment.CreatingCommentErrorEvent;
import com.fsep.sova.network.events.create_comment.CreatingCommentIsSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionCreateComment extends BaseAction<BaseResponseModel<Comment>> {

    private long mTaskId;
    private CommentSendingModel mBody;

    public ActionCreateComment(long taskId, CommentSendingModel body) {
        mTaskId = taskId;
        mBody = body;
    }

    protected ActionCreateComment(Parcel in) {
        mTaskId = in.readLong();
        mBody = in.readParcelable(CommentSendingModel.class.getClassLoader());
    }

    @Override
    protected Response<BaseResponseModel<Comment>> makeRequest() throws IOException {
        return getRest().createNewComment(mTaskId, mBody).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Comment>> response) {
        EventBus.getDefault().post(new CreatingCommentIsSuccessEvent(response.body().getData()));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new CreatingCommentErrorEvent(response.code(), response.message()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionCreateComment> CREATOR = new Creator<ActionCreateComment>() {
        @Override
        public ActionCreateComment createFromParcel(Parcel in) {
            return new ActionCreateComment(in);
        }

        @Override
        public ActionCreateComment[] newArray(int size) {
            return new ActionCreateComment[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTaskId);
        parcel.writeParcelable(mBody, i);
    }
}
