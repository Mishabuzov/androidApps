package com.fsep.sova.activities;

import android.os.Bundle;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;

public class RegistrationPhoneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_registration);
        setupWindow(R.color.colorAuthorizationDark);
    }
}
