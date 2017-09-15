package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.kpfu.mikhail.gif.R;

import java.util.ArrayList;
import java.util.List;

public enum MenuItem {
    HEADER,
    SIGN_IN(R.string.menu_login_item),
    FEED(R.string.menu_feed_item),
    FAVORITES(R.string.menu_favorites_item),
    SIGN_OUT(R.string.menu_logout_item);

    private int mName;
    private static List<MenuItem> mMenuItemsAuthorized = setItemsForAuthorizedUser();
    private static List<MenuItem> mMenuItemsNotAuthorized = setItemsForUnauthorizedUser();

    MenuItem(@StringRes int name){
        mName = name;
    }

    MenuItem() {
    }

    public int getItemName(){
        return mName;
    }

    public static List<MenuItem> setItemsForAuthorizedUser() {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(HEADER);
        menuItemList.add(FEED);
        menuItemList.add(FAVORITES);
        menuItemList.add(SIGN_OUT);
        return menuItemList;
    }

    @NonNull
    public static List<MenuItem> getItemsForAuthorizedUser(){
        return mMenuItemsAuthorized;
    }

    public static List<MenuItem> setItemsForUnauthorizedUser() {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(HEADER);
        menuItemList.add(SIGN_IN);
        menuItemList.add(FEED);
        return menuItemList;
    }

    @NonNull
    public static List<MenuItem> getItemsForNotAuthorizedUser() {
        return mMenuItemsNotAuthorized;
    }
}
