package com.fsep.sova.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public final class PrefUtils {

    public static final String PREF_FIRST_LAUNCH = "pref_first_launch";
    public static final String PREF_AUTH_DATA = "pref_auth_data";
    public static final String TEMP_AUTH_TOKEN = "TEMP_AUTH_TOKEN";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_ID = "USER_ID";
    public static final String USER_DESCRIPTION = "USER_DESCRIPTION";
    public static final String USER_SURNAME = "USER_SURNAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_AVATAR = "USER_AVATAR";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String COUNTS = "COUNTS";
    public static final String FOLLOWERS_COUNT = "FOLLOWERS_COUNT";
    public static final String FOLLOWINGS_COUNT = "FOLLOWINGS_COUNT";
    public static final String POSTS_COUNT = "POSTS_COUNT";
    public static final String COUNTRY_ID = "COUNTRY_ID";

    private PrefUtils() {
    }

    public static boolean isFirstLaunch(@Nullable Context context) {
        if (context == null) return false;

        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        if (sp.contains(PREF_FIRST_LAUNCH)) {
            sp.edit().putBoolean(PREF_FIRST_LAUNCH, false).apply();
            return true;
        }
        return false;
    }

    public static boolean isSignedIn(@Nullable final Context context) {
        if (context == null) return false;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.contains(PREF_AUTH_DATA);
    }

    @Nullable
    public static String getAuthToken(@Nullable final Context context) {
        if (context == null) return null;

        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(PREF_AUTH_DATA, "");
    }

    public static void saveAuthToken(@Nullable final Context context, @Nullable String authToken) {
        if (context == null || authToken == null) return;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(PREF_AUTH_DATA, authToken).apply();
    }

    public static long getUserId(@Nullable final Context context) {
        if (context == null) return 0;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getLong(USER_ID, 0);
    }

    public static void saveUserId(@Nullable final Context context, long id) {
        if (context == null || id == 0) return;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putLong(USER_ID, id).apply();
    }

    @Nullable
    public static String getTempAuthToken(@Nullable final Context context) {
        if (context == null) return null;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(TEMP_AUTH_TOKEN, "");
    }

    public static void saveTempAuthToken(@Nullable final Context context,
                                         @Nullable String tempAuthToken) {
        if (context == null || tempAuthToken == null) return;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(TEMP_AUTH_TOKEN, tempAuthToken).apply();
    }

    public static boolean hasTempAuthToken(@Nullable final Context context) {
        if (context == null) return false;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.contains(TEMP_AUTH_TOKEN);
    }

    @Nullable
    public static String getUserPhone(@Nullable final Context context) {
        if (context == null) return null;

        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(USER_PHONE, "");
    }

    public static void saveUserPhone(@Nullable final Context context,
                                     @Nullable String tempAuthToken) {
        if (context == null || tempAuthToken == null) return;
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(USER_PHONE, tempAuthToken).apply();
    }

    @Nullable
    public static String getUserName(@Nullable final Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(USER_NAME, "");
    }

    public static void saveUserName(@Nullable final Context context,
                                    @Nullable String username) {
        if (context == null || username == null) {
            return;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(USER_NAME, username).apply();
    }

    @Nullable
    public static String getUserSurname(@Nullable final Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(USER_SURNAME, "");
    }

    public static void saveUserSurname(@Nullable final Context context,
                                       @Nullable String username) {
        if (context == null || username == null) {
            return;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(USER_SURNAME, username).apply();
    }

    @Nullable
    public static String getUserAvatar(@Nullable final Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(USER_AVATAR, "");
    }

    public static void saveUserAvatar(@Nullable final Context context,
                                      @Nullable String userAvatar) {
        if (context == null || userAvatar == null) {
            return;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(USER_AVATAR, userAvatar).apply();
    }

    public static String getUserDescription(@Nullable final Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(USER_DESCRIPTION, "");
    }

    public static void saveUserDescription(@Nullable final Context context,
                                           @Nullable String userDescription) {
        if (context == null || userDescription == null) {
            return;
        }
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences.edit().putString(USER_DESCRIPTION, userDescription).apply();
    }

    public static String getUserEmail(@Nullable final Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sp.getString(USER_EMAIL, "");
    }

    public static void saveUserEmail(@Nullable final Context context,
                                     @Nullable String userEmail) {
        if (context == null || userEmail == null || userEmail.isEmpty()) {
            return;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sp.edit().putString(USER_EMAIL, userEmail).apply();
    }

    public static String getUserCounts(@Nullable final Context context, String countType) {
        if (context == null || countType == null || countType.isEmpty()) {
            return null;
        }
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        switch (countType) {
            case FOLLOWERS_COUNT:
                return String.valueOf(sp.getInt(FOLLOWERS_COUNT, 0));
            case FOLLOWINGS_COUNT:
                return String.valueOf(sp.getInt(FOLLOWINGS_COUNT, 0));
            case POSTS_COUNT:
                return String.valueOf(sp.getInt(POSTS_COUNT, 0));
            default:
                return null;
        }
    }


    public static void saveUserCounts(@Nullable final Context context,
                                      String countType,
                                      int count) {
        if (context == null || countType == null || countType.isEmpty()) {
            return;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        switch (countType) {
            case FOLLOWERS_COUNT:
                sp.edit().putInt(FOLLOWERS_COUNT, count).apply();
                break;
            case FOLLOWINGS_COUNT:
                sp.edit().putInt(FOLLOWINGS_COUNT, count).apply();
                break;
            case POSTS_COUNT:
                sp.edit().putInt(POSTS_COUNT, count).apply();
                break;
        }
    }

    public static void clearPreferences(@Nullable Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().clear().apply();
    }
}
