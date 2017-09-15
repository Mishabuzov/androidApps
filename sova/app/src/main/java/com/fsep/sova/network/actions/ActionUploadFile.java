package com.fsep.sova.network.actions;

import android.os.Parcel;

import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.Document;
import com.fsep.sova.models.FileType;
import com.fsep.sova.network.events.loadfile.FileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.FileUploadIsSuccessEvent;
import com.fsep.sova.utils.RequestUtils;

import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;

public class ActionUploadFile extends BaseAction<BaseResponseModel<Document>> {

  private String ticket;
  private String filePath;
  private FileType fileType;

  public ActionUploadFile(String ticket, String filePath, FileType fileType) {
    this.ticket = ticket;
    this.filePath = filePath;
    this.fileType = fileType;
  }

  private ActionUploadFile(Parcel in) {
    this.ticket = in.readString();
    this.filePath = in.readString();
    this.fileType = FileType.getEnum(in.readString());
  }

  @Override protected Response<BaseResponseModel<Document>> makeRequest() throws IOException {
    return getStorageRest().uploadFile(ticket, RequestUtils.getMultipartBody(filePath)).execute();
  }


  @Override protected void processNetworkError() {
    super.processNetworkError();
    EventBus.getDefault().post(new FileUploadIsErrorEvent(0, filePath));
  }

  @Override protected void onResponseSuccess(Response<BaseResponseModel<Document>> response) {
    EventBus.getDefault().post(new FileUploadIsSuccessEvent(filePath, response.body().getData(), fileType));
  }

  @Override protected void onHttpError(Response<?> response) {
    EventBus.getDefault().post(new FileUploadIsErrorEvent(response.code(), filePath));
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(ticket);
    dest.writeString(filePath);
    dest.writeString(fileType.toString());
  }

  public static final Creator<ActionUploadFile> CREATOR = new Creator<ActionUploadFile>() {
    @Override public ActionUploadFile createFromParcel(Parcel in) {
      return new ActionUploadFile(in);
    }

    @Override public ActionUploadFile[] newArray(int size) {
      return new ActionUploadFile[size];
    }
  };
}
