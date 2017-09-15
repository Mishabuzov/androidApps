package com.fsep.sova.network;

import android.app.IntentService;
import android.content.Intent;

import com.fsep.sova.network.actions.Action;
import com.fsep.sova.utils.Constants;

public class ActionService extends IntentService {

    private static final String TAG = ActionService.class.getSimpleName();

    public ActionService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Action action = intent.getParcelableExtra(Constants.ACTION_EXTRA);
        action.setContext(getApplicationContext());
        action.execute();
    }
}
