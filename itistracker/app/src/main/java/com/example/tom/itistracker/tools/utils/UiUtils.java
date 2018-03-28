package com.example.tom.itistracker.tools.utils;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class UiUtils {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus() && null != activity.getCurrentFocus().getWindowToken())
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showToast(@NonNull final String message, @NonNull Context context) {
        AndroidUtils.runOnUIThread(() ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

    public static void showToast(@StringRes final int message, @NonNull Context context) {
        AndroidUtils.runOnUIThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

    public static String convertMillisToHourReadableFormat(long millis) {
        long hoursLong = TimeUnit.MILLISECONDS.toHours(millis);
        long minutesLong = TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(hoursLong);
        long secondsLong = TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.HOURS.toSeconds(hoursLong) - TimeUnit.MINUTES.toSeconds(minutesLong);
        String hours = String.valueOf(hoursLong);
        String minutes = String.valueOf(minutesLong);
        String seconds = String.valueOf(secondsLong);
        if (hoursLong < 10) {
            hours = "0" + hours;
        }
        if (minutesLong < 10) {
            minutes = "0" + minutes;
        }
        if (secondsLong < 10) {
            seconds = "0" + seconds;
        }
        return String.format("%s:%s:%s",
                hours, minutes, seconds);
    }

}
