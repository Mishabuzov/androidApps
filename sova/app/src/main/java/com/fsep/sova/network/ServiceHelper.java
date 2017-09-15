package com.fsep.sova.network;

import android.app.Activity;
import android.content.Intent;

import com.fsep.sova.activities.CitySearchActivity;
import com.fsep.sova.activities.SearchActivity;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionFindUsers;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.Constants;

public class ServiceHelper {

    private static volatile ServiceHelper instance;

    public static ServiceHelper getInstance() {
        if (instance == null)
            synchronized (ServiceHelper.class) {
                if (instance == null)
                    instance = new ServiceHelper();
            }
        return instance;
    }

    private ServiceHelper() {

    }

    public void startActionService(Activity activity, Action action) {
        if (!action.getClass().equals(ActionFindUsers.class)
                && !activity.getClass().equals(SearchActivity.class)
                && !activity.getClass().equals(CitySearchActivity.class)) {
            AndroidUtils.hideSoftKeyboard(activity);
        }
        Intent intent = new Intent(activity, ActionService.class);
        intent.putExtra(Constants.ACTION_EXTRA, action);
        activity.startService(intent);
    }

}
