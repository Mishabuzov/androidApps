package com.fsep.sova.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fsep.sova.R;
import com.fsep.sova.activities.base.BaseActivity;
import com.fsep.sova.fragments.CommentsFragment;

import butterknife.ButterKnife;

public class CommentsActivity extends BaseActivity {

    private long mTaskId;
    private CommentsFragment mCommentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        catchTaskId();
        installFragment();
    }

    private void catchTaskId() {
        Intent intent = getIntent();
        mTaskId = intent.getLongExtra(CommentsFragment.NOTE_ID, 0);
    }

    private void installFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mCommentsFragment = new CommentsFragment();
        mCommentsFragment.setArguments(getBundle());

        if (fm.findFragmentById(R.id.comments_fragment) != null) {
            ft.replace(R.id.comments_fragment, mCommentsFragment, "comments_fragment");
            ft.commit();
        } else {
            ft.add(R.id.comments_fragment, mCommentsFragment, "comments_fragment");
            ft.commit();
        }
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong(CommentsFragment.NOTE_ID, mTaskId);
        return bundle;
    }

}
