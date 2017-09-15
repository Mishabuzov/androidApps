package com.fsep.sova.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.fsep.sova.App;
import com.fsep.sova.utils.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public final class UiUtils {

    public static void showToast(@StringRes final int message) {
        AndroidUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast(@NonNull final String message) {
        AndroidUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showSnackbar(@NonNull Activity activity, @NonNull final CharSequence message) {
        AndroidUtils.hideSoftKeyboard(activity.getCurrentFocus());

        final View contentView = activity.findViewById(android.R.id.content);

        AndroidUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (contentView == null) {
                    Logger.d("Showing Snackbar fail. ContentView is null");
                    return;
                }
                try {
                    Snackbar.make(contentView, message, Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Logger.e(e.getMessage());
                }
            }
        });
    }

    public static String getHumanReadableDate(long unixSeconds) {
        Date date = new Date(unixSeconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);
    }

}
