package com.fsep.sova.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fsep.sova.App;
import com.fsep.sova.BuildConfig;
import com.fsep.sova.Config;
import com.fsep.sova.models.Event;
import com.fsep.sova.models.Note;
import com.fsep.sova.models.Task;

import java.util.ArrayList;
import java.util.List;

public final class AndroidUtils {

    private AndroidUtils() {
    }

    /**
     * Static method to hide Software Keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus() && null != activity.getCurrentFocus().getWindowToken())
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null)
            return;

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!imm.isActive())
            return;

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getRestEndpoint() {
        return BuildConfig.DEBUG ? Config.SOVA_ENDPOINT_DEBUG : Config.SOVA_ENDPOINT_RELEASE;
    }

    public static String getStorageRestEndpoint() {
        return BuildConfig.DEBUG ? Config.SOVA_STORAGE_ENDPOINT_DEBUG : Config.SOVA_STORAGE_ENDPOINT_RELEASE;
    }

    public static void stopService(Context context, Class<?> serviceClass) {
        if (context == null) {
            return;
        }
        if (isServiceRunning(context, serviceClass)) {
            Intent serviceIntent = new Intent(context, serviceClass);
            context.stopService(serviceIntent);
        }
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        if (context == null) {
            return false;
        }
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            App.handler.post(runnable);
        } else {
            App.handler.postDelayed(runnable, delay);
        }
    }

    public static List<Note> convertTasksIntoNotes(List<Task> tasks) {
        List<Note> notes = new ArrayList<>();
        for (Task task : tasks) {
            notes.add(task);
        }
        return notes;
    }

    public static List<Note> convertEventsIntoNotes(List<Event> tasks) {
        List<Note> notes = new ArrayList<>();
        for (Event event : tasks) {
            notes.add(event);
        }
        return notes;
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

}
