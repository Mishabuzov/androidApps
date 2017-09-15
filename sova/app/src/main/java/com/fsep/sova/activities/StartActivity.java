package com.fsep.sova.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fsep.sova.utils.PrefUtils;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PrefUtils.isSignedIn(this)) {
           /* PrefUtils.clearPreferences(App.context);
            startActivity(new Intent(this, AuthorizationActivity.class));*/
            startActivity(new Intent(this, NotesActivity.class));
        } else {
            startActivity(new Intent(this, AuthorizationActivity.class));
        }
        finish();
    }
}
