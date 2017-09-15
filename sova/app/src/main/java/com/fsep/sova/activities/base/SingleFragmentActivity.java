package com.fsep.sova.activities.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.fsep.sova.R;

public abstract class SingleFragmentActivity extends BaseActivity {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    protected abstract Fragment getFragment();

    protected abstract Bundle getFragmentArguments();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = getFragment();
        fragment.setArguments(getFragmentArguments());
        if (manager.findFragmentById(R.id.content_frame) == null) {
            manager.beginTransaction()
                    .add(R.id.content_frame, fragment, FRAGMENT_TAG)
                    .commit();
        } else {
            manager.beginTransaction()
                    .replace(R.id.content_frame, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
