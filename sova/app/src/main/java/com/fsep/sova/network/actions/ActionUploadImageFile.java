package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.FileType;
import com.fsep.sova.models.Photo;
import com.fsep.sova.network.events.loadfile.ImageFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadSuccessEvent;
import com.fsep.sova.utils.RequestUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public class ActionUploadImageFile extends BaseAction<BaseResponseModel<Photo>>{

    private String ticket;
    private String filePath;
    private FileType fileType;

    public ActionUploadImageFile(String ticket, String filePath, FileType fileType) {
        this.ticket = ticket;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    private ActionUploadImageFile(Parcel in) {
        this.ticket = in.readString();
        this.filePath = in.readString();
        this.fileType = FileType.getEnum(in.readString());
    }

    @Override
    protected Response<BaseResponseModel<Photo>> makeRequest() throws IOException {
        return getStorageRest().uploadImageFile(ticket, RequestUtils.getMultipartBody(filePath)).execute();
    }

    @Override
    protected void onResponseSuccess(Response<BaseResponseModel<Photo>> response) {
        org.greenrobot.eventbus.EventBus.getDefault().post(new ImageFileUploadSuccessEvent(filePath, response.body().getData(), fileType));
    }

    @Override
    protected void onHttpError(Response<?> response) {
       EventBus.getDefault().post(new ImageFileUploadIsErrorEvent(0, filePath));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticket);
        dest.writeString(filePath);
        dest.writeString(fileType.toString());
    }

    public static final Creator<ActionUploadImageFile> CREATOR = new Creator<ActionUploadImageFile>() {
        @Override public ActionUploadImageFile createFromParcel(Parcel in) {
            return new ActionUploadImageFile(in);
        }

        @Override public ActionUploadImageFile[] newArray(int size) {
            return new ActionUploadImageFile[size];
        }
    };
}
