package com.fsep.sova;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.facebook.stetho.Stetho;
import com.karumi.dexter.Dexter;

public class App extends Application {

  public static Context context;
  public static Handler handler;

  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this);
    context = getApplicationContext();
    handler = new Handler(Looper.getMainLooper());
    Dexter.initialize(context);
  }
}
