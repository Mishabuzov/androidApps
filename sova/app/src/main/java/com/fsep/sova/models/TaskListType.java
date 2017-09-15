package com.fsep.sova.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.fsep.sova.R;

import java.util.List;

public enum TaskListType {
    AVATAR(),
    FEED(R.string.nav_dashboard, R.drawable.dashboard, R.drawable.dashboard_active),
    MY_TASKS(R.string.nav_my_tasks, R.drawable.assignment, R.drawable.assignment_active),
    FAVOURITES(R.string.nav_favourites, R.drawable.filled_bookmark, R.drawable.filled_bookmark_active),
    MY_EVENTS(R.string.nav_my_events, R.drawable.event, R.drawable.event_active),
    NOTIFICATIONS(R.string.nav_notifications, R.drawable.ic_notifications, R.drawable.notification_active),
    SEARCH(R.string.nav_search, R.drawable.search, R.drawable.search_active);

    private int mIcon;
    private int mActiveIcon;
    private int mName;

    TaskListType(@StringRes int name, @DrawableRes int icon, @DrawableRes int activeIcon) {
        mName = name;
        mIcon = icon;
        mActiveIcon = activeIcon;
    }

    TaskListType() {
    }

    public int getName() {
        return mName;
    }

    public int getActiveIcon() {
        return mActiveIcon;
    }

    public int getIcon() {
        return mIcon;
    }

    public List<TaskListType> getAuthMenuList(){
        List<TaskListType>
    }
}
