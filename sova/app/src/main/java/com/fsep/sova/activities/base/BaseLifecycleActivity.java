package com.fsep.sova.activities.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fsep.sova.utils.logger.Logger;

public class BaseLifecycleActivity extends AppCompatActivity {
    protected final String TAG;
    private boolean mLifeCycleLogsEnabled = true;

    {
        TAG = getClass().getSimpleName();
    }

    protected void enableLifeCycleLogs(boolean enable) {
        mLifeCycleLogsEnabled = enable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            log("onCreate(): activity re-created from savedInstanceState");
        } else {
            log("onCreate(): activity created anew");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy()");
    }

    private void log(String log) {
        if (mLifeCycleLogsEnabled) {
            Logger.d(TAG + " - " + log);
        }
    }
}
