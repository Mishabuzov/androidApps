package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fsep.sova.activities.base.SingleFragmentActivity;
import com.fsep.sova.fragments.EducationEditFragment;

public class EducationEditActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new EducationEditFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }
}
