package com.fsep.sova.fragments.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fsep.sova.utils.logger.Logger;

public class BaseLifecycleLoggingFragment extends Fragment {
    private final String TAG;
    private boolean mLifeCycleLogsEnabled = true;

    {
        TAG = getClass().getSimpleName();
    }

    protected void enableLifeCycleLogs(boolean enable) {
        mLifeCycleLogsEnabled = enable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            log("onCreate(): fragment re-created from savedInstanceState");
        } else {
            log("onCreate(): fragment created anew");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("onViewCreated()");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        log("onViewStateRestored()");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy()");
    }

    private void log(String log) {
        if (mLifeCycleLogsEnabled) {
            Logger.d(TAG + " - " + log);
        }
    }
}
