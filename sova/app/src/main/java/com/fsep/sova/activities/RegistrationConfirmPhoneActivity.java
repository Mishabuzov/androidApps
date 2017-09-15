package com.fsep.sova.activities;

import android.os.Bundle;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.fragments.RegistrationConfirmPhoneFragment;

public class RegistrationConfirmPhoneActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegistrationConfirmPhoneFragment fragment = new RegistrationConfirmPhoneFragment();
        getSupportFragmentManager().beginTransaction().add(
                android.R.id.content, fragment).commit();
        setupWindow(R.color.colorAuthorizationDark);
    }
}
