package com.kpfu.mikhail.vk.screen.base.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kpfu.mikhail.vk.screen.feed.FeedActivity;
import com.kpfu.mikhail.vk.screen.login.LoginActivity;
import com.kpfu.mikhail.vk.utils.PreferenceUtils;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceUtils.isSignedIn()) {
            startActivity(new Intent(this, FeedActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

}
