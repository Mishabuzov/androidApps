package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fsep.sova.activities.base.SingleFragmentActivity;
import com.fsep.sova.fragments.JobEditFragment;

public class JobEditActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new JobEditFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }
}
