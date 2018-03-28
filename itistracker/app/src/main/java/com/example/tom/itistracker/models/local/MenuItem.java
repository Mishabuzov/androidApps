package com.example.tom.itistracker.models.local;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.example.tom.itistracker.R;

import java.util.ArrayList;
import java.util.List;

public enum MenuItem {

    HEADER,
    LOG_OUT(R.string.menu_logout);

    private int mName;
    private static List<MenuItem> mMenuItems = setItems();

    MenuItem(@StringRes int name) {
        mName = name;
    }

    MenuItem() {}

    public int getItemName() {
        return mName;
    }

    public static List<MenuItem> setItems() {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(HEADER);
        menuItemList.add(LOG_OUT);
        return menuItemList;
    }

    @NonNull
    public static List<MenuItem> getmMenuItems() {
        return mMenuItems;
    }

}
