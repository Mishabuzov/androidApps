package com.kpfu.mikhail.gif.utils;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;


public final class PreferenceUtils {

    private static final String TOKEN_KEY = "token";
    private static final String TIME_GETTING_TOKEN_KEY = "time";
    private static final String USER_NAME_KEY = "user_name";

    private PreferenceUtils() {
    }

    public static void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
        Hawk.put(TIME_GETTING_TOKEN_KEY, getCurrentUnixTime());
    }

    @NonNull
    public static String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    private static long getTokenGettingTime() {
        return Hawk.get(TIME_GETTING_TOKEN_KEY, 0L);
    }

    public static void saveUserName(@NonNull String userName) {
        Hawk.put(USER_NAME_KEY, userName);
    }

    @NonNull
    public static String getUserName() {
        return Hawk.get(USER_NAME_KEY, "");
    }

    public static boolean isCurrentUserAuthorized(){
        return !getToken().isEmpty() && (getCurrentUnixTime() - getTokenGettingTime() < 3590L);
    }

    private static long getCurrentUnixTime(){
        return System.currentTimeMillis() / 1000L;
    }

    public static void clearPreference(){
        Hawk.remove(TOKEN_KEY, USER_NAME_KEY, TIME_GETTING_TOKEN_KEY);
    }

}
