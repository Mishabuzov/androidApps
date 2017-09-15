package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.FileType;
import com.fsep.sova.models.Video;
import com.fsep.sova.network.events.loadfile.VideoFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.VideoFileUploadSuccessEvent;
import com.fsep.sova.utils.RequestUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionUploadVideoFile extends BaseAction<BaseResponseModel<Video>> {

    private String ticket;
    private String filePath;
    private FileType fileType;

    public ActionUploadVideoFile(String ticket, String filePath, FileType fileType) {
        this.ticket = ticket;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    private ActionUploadVideoFile(Parcel in) {
        this.ticket = in.readString();
        this.filePath = in.readString();
        this.fileType = FileType.getEnum(in.readString());
    }

    @Override
    protected Response<BaseResponseModel<Video>> makeRequest() throws IOException {
        return getStorageRest().uploadVideoFile(ticket, RequestUtils.getMultipartBody(filePath)).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Video>> response) {
        EventBus.getDefault().post(new VideoFileUploadSuccessEvent(filePath, response.body().getData(), fileType));
    }

    @Override
    protected void onHttpError(Response<?> response) {
        EventBus.getDefault().post(new VideoFileUploadIsErrorEvent(0, filePath));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticket);
        dest.writeString(filePath);
        dest.writeString(fileType.toString());
    }

    public static final Creator<ActionUploadVideoFile> CREATOR = new Creator<ActionUploadVideoFile>() {
        @Override public ActionUploadVideoFile createFromParcel(Parcel in) {
            return new ActionUploadVideoFile(in);
        }

        @Override public ActionUploadVideoFile[] newArray(int size) {
            return new ActionUploadVideoFile[size];
        }
    };
}
