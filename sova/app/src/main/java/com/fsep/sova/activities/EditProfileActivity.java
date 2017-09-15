package com.fsep.sova.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fsep.sova.activities.base.SingleFragmentActivity;
import com.fsep.sova.fragments.EditProfileFragment;

public class EditProfileActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return new EditProfileFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return getIntent().getExtras();
    }
}
