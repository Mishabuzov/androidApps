package com.kpfu.mikhail.vk.utils;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
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

    public static View getUpgradeView(@NonNull Context context,
                                      @LayoutRes int upgradeRes) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(upgradeRes, null);
    }

}
