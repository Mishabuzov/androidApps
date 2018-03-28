package com.example.tom.itistracker.screens.auth.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toolbar;

import com.arellomobile.mvp.MvpActivity;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.widgets.fonts.textviews.RobotoBoldTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends MvpActivity {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    RobotoBoldTextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        ButterKnife.bind(this);
        initComponents();
        doCreatingActions();
    }

    protected void doCreatingActions() {
    }

    protected void initComponents() {
        processFragment();
        setActionBar(mToolbar);
    }

    private void processFragment() {
        Fragment fragment = new MyFragment();
        setNewInstanceOfFragment(fragment);
    }

    protected void setNewInstanceOfFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentById(R.id.fragment_layout) != null) {
            ft.replace(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();
        } else {
            ft.add(R.id.fragment_layout, fragment, FRAGMENT_TAG);
            ft.commit();
        }
    }

    public void setToolbarTitle(@NonNull final String title) {
        if (!title.isEmpty()) {
            mToolbarTitle.setText(title);
        }
    }

    public void setToolbarTitle(@StringRes final int title) {
        if (title != 0) {
            mToolbarTitle.setText(title);
        }
    }
}
