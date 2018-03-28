package com.example.tom.itistracker.tools.utils;

import android.support.annotation.NonNull;

import com.example.tom.itistracker.models.network.User;
import com.orhanobut.hawk.Hawk;


public final class PreferenceUtils {

    private static final String TOKEN_KEY = "token";

    private static final String USER_PROFILE_KEY = "profile";

    private static final String NEXT_FROM = "next_from";

    public static void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
    }

    @NonNull
    public static String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    @NonNull
    public static User getUserProfile() {
        return Hawk.get(USER_PROFILE_KEY);
    }

    public static void saveUserProfile(@NonNull User user) {
        Hawk.put(USER_PROFILE_KEY, user);
    }

    public static void saveNextFromValue(@NonNull String nextFrom) {
        Hawk.put(NEXT_FROM, nextFrom);
    }

    @NonNull
    public static String getStartFromValue() {
        return Hawk.get(NEXT_FROM, "");
    }

    public static void clearNextFromValue() {
        Hawk.remove(NEXT_FROM);
    }

    public static boolean isSignedIn() {
        return !getToken().isEmpty();
    }

    public static void clearPreference() {
        Hawk.remove(TOKEN_KEY, USER_PROFILE_KEY, NEXT_FROM);
    }

}
