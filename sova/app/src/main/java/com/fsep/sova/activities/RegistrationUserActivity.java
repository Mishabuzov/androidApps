package com.fsep.sova.activities;

import android.os.Bundle;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.fragments.RegistrationUserFragment;

public class RegistrationUserActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegistrationUserFragment fragment = new RegistrationUserFragment();
        getSupportFragmentManager().beginTransaction().add(
                android.R.id.content, fragment).commit();
        setupWindow(R.color.colorAuthorizationDark);
    }
}
