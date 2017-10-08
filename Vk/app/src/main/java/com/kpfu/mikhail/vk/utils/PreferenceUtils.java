package com.kpfu.mikhail.vk.utils;

import android.support.annotation.NonNull;

import com.kpfu.mikhail.vk.content.Profile;
import com.orhanobut.hawk.Hawk;


public final class PreferenceUtils {

    private static final String TOKEN_KEY = "token";

    private static final String PROFILE_KEY = "profile";

    private static final String NEXT_FROM = "next_from";

    public static void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
    }

    @NonNull
    public static String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    @NonNull
    public static Profile getUserProfile() {
        return Hawk.get(PROFILE_KEY);
    }

    public static void saveUserProfile(@NonNull Profile profile) {
        Hawk.put(PROFILE_KEY, profile);
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

    static void clearPreference() {
        Hawk.remove(TOKEN_KEY, PROFILE_KEY, NEXT_FROM);
    }

}
