package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.SingleFragmentActivity;
import com.fsep.sova.fragments.GalleryFragment;

public class GalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        setupWindow(R.color.colorBlack);
        return new GalleryFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return getIntent().getExtras();
    }
}
