package com.fsep.sova.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.SingleFragmentActivity;
import com.fsep.sova.fragments.ChatFragment;

public class ChatActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        setupWindow(R.color.transparent_black_color_for_top_window_bar);
        return new ChatFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return getIntent().getExtras();
    }
}
