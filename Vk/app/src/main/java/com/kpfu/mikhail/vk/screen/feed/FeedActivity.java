package com.kpfu.mikhail.vk.screen.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kpfu.mikhail.vk.screen.base.activities.navigation_activity.NavigationActivity;
import com.kpfu.mikhail.vk.screen.base.activities.single_fragment_activity.SingleFragmentActivity;

public class FeedActivity extends NavigationActivity {

    @Override
    protected Fragment getFragment() {
        return new FeedFragment();
    }

    @Override
    protected Bundle getFragmentArguments() {
        return null;
    }

}
