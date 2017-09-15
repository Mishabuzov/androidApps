package com.fsep.sova.network.actions;

import android.os.Parcel;
import com.fsep.sova.models.BaseResponseModel;
import com.fsep.sova.models.FileType;
import com.fsep.sova.network.events.get_ticket.GettingTicketErrorEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketIsSuccessEvent;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Response;

public class ActionGetTicketForFileUploading extends BaseAction<BaseResponseModel> {
  private String mReason;
  private String mFilePath;
  private FileType mFileType;

  public ActionGetTicketForFileUploading(String reason) {
    mReason = reason;
  }

  public ActionGetTicketForFileUploading(String reason, String filePath, FileType fileType) {
    mReason = reason;
    mFilePath = filePath;
    mFileType = fileType;
  }

  protected ActionGetTicketForFileUploading(Parcel in) {
    mReason = in.readString();
    mFilePath = in.readString();
    mFileType = FileType.getEnum(in.readString());
  }

  @Override protected Response<BaseResponseModel> makeRequest() throws IOException {
    return getRest().getTicket(mReason).execute();
  }

  @Override protected void processNetworkError() {
    super.processNetworkError();
    EventBus.getDefault().post(new GettingTicketErrorEvent(0));
  }

  @Override protected void onResponseSuccess(Response<BaseResponseModel> response) {
    EventBus.getDefault().post(new GettingTicketIsSuccessEvent(response.headers().get("ticket"), mFilePath, mFileType));
  }

  @Override protected void onHttpError(Response<?> response) {
    EventBus.getDefault().post(new GettingTicketErrorEvent(response.code()));
  }

  public static final Creator<ActionGetTicketForFileUploading> CREATOR =
      new Creator<ActionGetTicketForFileUploading>() {
        @Override public ActionGetTicketForFileUploading createFromParcel(Parcel source) {
          return new ActionGetTicketForFileUploading(source);
        }

        @Override public ActionGetTicketForFileUploading[] newArray(int size) {
          return new ActionGetTicketForFileUploading[size];
        }
      };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mReason);
    dest.writeString(mFilePath);
    dest.writeString(mFileType.toString());
  }

  public enum Reason {
    TO_UPLOAD("to_upload"),
    TO_AVATAR_UPLOAD("to_avatar_upload");

    private String value;

    Reason(String value) {
      this.value = value;
    }

    @Override public String toString() {
      return value;
    }
  }
}
