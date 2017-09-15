package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fsep.sova.activities.base.SingleFragmentActivity;
import com.fsep.sova.fragments.NoteDetailsFragment;

public class TaskDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return new NoteDetailsFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return getIntent().getExtras();
    }
}
