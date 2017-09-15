package com.kpfu.mikhail.gif;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.karumi.dexter.Dexter;
import com.kpfu.mikhail.gif.api.ApiFactory;
import com.kpfu.mikhail.gif.repository.GifProvider;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        context = getApplicationContext();
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();
        ApiFactory.recreate();
        GifProvider.init();
        Dexter.initialize(context);
    }
}
