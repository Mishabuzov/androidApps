package com.fsep.sova.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fsep.sova.R;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EducationChooseLvlFragment extends BaseLoadableFragment {

    @Bind(R.id.edu_medium_ok) ImageView mMediumOk;
    @Bind(R.id.edu_hard_ok) ImageView mHardOk;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    private Intent mIntent = new Intent();

    @OnClick(R.id.edu_lvl_medium_layout)
    public void onClickMediumLayout() {
        mMediumOk.setVisibility(View.VISIBLE);
        mHardOk.setVisibility(View.GONE);
        mIntent.putExtra(Constants.EDU_LVL, R.string.education_lvl_medium);
    }

    @OnClick(R.id.edu_lvl_hard_layout)
    public void onClickHardLayout() {
        mHardOk.setVisibility(View.VISIBLE);
        mMediumOk.setVisibility(View.GONE);
        mIntent.putExtra(Constants.EDU_LVL, R.string.education_lvl_hard);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.add_task_done:
                getActivity().setResult(Activity.RESULT_OK, mIntent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.back);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_chose_lvl, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initToolbar();
        return view;
    }
}
