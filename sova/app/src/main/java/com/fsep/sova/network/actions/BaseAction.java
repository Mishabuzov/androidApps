package com.fsep.sova.network.actions;

import android.content.Context;

import com.fsep.sova.network.SOVARest;
import com.fsep.sova.network.SOVAStorageRest;
import com.fsep.sova.network.SessionRestManager;
import com.fsep.sova.network.events.ExpiredTokenEvent;
import com.fsep.sova.network.events.NetworkErrorEvent;
import com.fsep.sova.utils.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.Response;

public abstract class BaseAction<T> implements Action {

  protected Context context;

  @Override public void setContext(Context context) {
    this.context = context.getApplicationContext();
  }

  protected SOVARest getRest() {
    return SessionRestManager.getInstance().getRest();
  }

  protected SOVAStorageRest getStorageRest() {
    return SessionRestManager.getInstance().getStorageRest();
  }

  protected void processError(Response<?> response) {
    if (response.code() == 401) {
      EventBus.getDefault().post(new ExpiredTokenEvent());
    } else {
      onHttpError(response);
    }
  }

  protected void processNetworkError() {
    EventBus.getDefault().post(new NetworkErrorEvent());
  }

  protected abstract Response<T> makeRequest() throws IOException;

  protected abstract void onResponseSuccess(Response<T> response);

  protected abstract void onHttpError(Response<?> response);

  @Override public void execute() {
    if (context == null) {
      Logger.e("Context is not set!");
      return;
    }
    Response<T> response;
    try {
      response = makeRequest();
    } catch (IOException e) {
      processNetworkError();
      return;
    }
    if (response.isSuccessful()) {
      onResponseSuccess(response);
    } else {
      processError(response);
    }
  }
}
