package com.kpfu.mikhail.vk;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.facebook.stetho.Stetho;
import com.kpfu.mikhail.vk.repository.VkProvider;
import com.kpfu.mikhail.vk.screen.login.LoginActivity;
import com.kpfu.mikhail.vk.utils.AndroidUtils;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class App extends Application {

    public static Handler sHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        sHandler = new Handler(Looper.getMainLooper());
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE)
                .build();
        VKSdk.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        VkProvider.init();
        initAndStartTokenTracker();
    }

    private void initAndStartTokenTracker() {
        VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
                if (newToken == null) {
                    // VKAccessToken is invalid
                    AndroidUtils.clearAllSavedData();
                    startActivity(new Intent(App.this, LoginActivity.class));
                }
            }
        };
        vkAccessTokenTracker.startTracking();
    }

}
