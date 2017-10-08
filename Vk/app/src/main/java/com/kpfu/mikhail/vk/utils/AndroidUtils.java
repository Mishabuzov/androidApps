package com.kpfu.mikhail.vk.utils;


import com.kpfu.mikhail.vk.App;
import com.vk.sdk.VKSdk;

import java.util.TimeZone;

public class AndroidUtils {

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            App.sHandler.post(runnable);
        } else {
            App.sHandler.postDelayed(runnable, delay);
        }
    }

    public static void logOut(){
        PreferenceUtils.clearPreference();
        VKSdk.logout();
    }

    public static void clearAllSavedData() {
        PreferenceUtils.clearPreference();
/*        Realm.getDefaultInstance().executeTransaction(
                realm -> realm.deleteAll());*/
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static long getCurrentTimeInUnixFormat() {
        return (System.currentTimeMillis()
                + TimeZone.getTimeZone("GMT+3").getOffset(System.currentTimeMillis())) / 1000;
    }

}
