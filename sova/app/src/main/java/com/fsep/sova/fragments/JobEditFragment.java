package com.fsep.sova.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.fsep.sova.R;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.UiUtils;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobEditFragment extends BaseLoadableFragment {

    @BindColor(R.color.black_text) int mBlackTextColor;
    @BindString(R.string.education_country) String mCountryBeginValue;
    @BindString(R.string.education_city) String mCityBeginValue;
    @BindString(R.string.job_company_name) String mJobCompanyNameBeginValue;
    @BindString(R.string.edu_exit_dialog_description) String mExitDialogDescr;
    @BindString(R.string.edu_exit_dialog_positive) String mExitAlertDialogPositiveAnswer;
    @BindString(R.string.edu_exit_dialog_negative) String mExitAlertDialogNegativeAnswerAnswer;
    @BindString(R.string.edu_exit_dialog_neutral) String mExitAlertDialogNeutralAnswerAnswer;
    @Bind(R.id.job_begin_year_edit) EditText mBeginYearEdit;
    @Bind(R.id.job_end_year_edit) EditText mEndYearEdit;
    @Bind(R.id.job_post_edit) EditText mJobPostEdit;
    @Bind(R.id.job_end_year_layout) RelativeLayout mEndYearLayout;
    @Bind(R.id.current_job_switch) Switch mCurrentJobSwitch;
    @Bind(R.id.country_tv) TextView mCountryTv;
    @Bind(R.id.city_tv) TextView mCityTv;
    @Bind(R.id.company_name_tv) TextView mCompanyNameTv;
    @Bind(R.id.end_year_text_input_layout) TextInputLayout mEndYearInputLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    private int mIntValueBeginYear = 0;
    private int mIntValueEndYear = 0;

    @OnClick(R.id.country_layout)
    public void onClickCountryLayout() {
    }

    @OnClick(R.id.city_layout)
    public void onClickCityLayout() {
    }

    @OnClick(R.id.company_layout)
    public void onClickEducationLayout() {
    }

    @OnClick(R.id.current_edu_layout)
    public void onCurrentEduSwitchClick() {
        if (mCurrentJobSwitch.isChecked()) {
            mCurrentJobSwitch.setChecked(false);
            mEndYearLayout.setVisibility(View.VISIBLE);
        } else {
            mCurrentJobSwitch.setChecked(true);
            mEndYearLayout.setVisibility(View.GONE);
            AndroidUtils.hideSoftKeyboard(getActivity());
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showExitDialog();
                break;
            case R.id.add_task_done:
                if (isAllDataHasInputtedCorrectly()) {
                    getActivity().finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showExitDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(R.string.edu_exit_dialog_title);
        alertDialog.setMessage(mExitDialogDescr);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mExitAlertDialogPositiveAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mExitAlertDialogNegativeAnswerAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.closeOptionsMenu();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, mExitAlertDialogNeutralAnswerAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.closeOptionsMenu();
            }
        });
        alertDialog.show();
    }

    //TODO: вставить проверку полей перед принятем данных
    private boolean isAllDataHasInputtedCorrectly() {
        if (mCountryTv.getText().equals(mCountryBeginValue)) {
            UiUtils.showToast(R.string.job_country_empty_choice_toast);
            return false;
        }
        if (mCityTv.getText().equals(mCityBeginValue)) {
            UiUtils.showToast(R.string.job_city_empty_choice_toast);
            return false;
        }
        if (mCompanyNameTv.getText().equals(mJobCompanyNameBeginValue)) {
            UiUtils.showToast(R.string.job_company_empty_choice_toast);
            return false;
        }
        if (mJobPostEdit.getText().toString().trim().isEmpty()) {
            UiUtils.showToast(R.string.job_job_empty_choice_toast);
            return false;
        }
        if (!isBeginYearHasCorrectValue()) {
            UiUtils.showToast(R.string.job_begin_year_empty_choice_toast);
            return false;
        }
        if (!isEndYearHasCorrectValue()) {
            UiUtils.showToast(R.string.job_end_year_empty_choice_toast);
            return false;
        } else {
            if (mIntValueEndYear - mIntValueBeginYear < 0) {
                UiUtils.showToast(R.string.job_work_period_empty_choice_toast);
                return false;
            }
        }
        return true;
    }

    private boolean isEndYearHasCorrectValue() {
        if (!mCurrentJobSwitch.isChecked()) {
            String endYear = mEndYearEdit.getText().toString().trim();
            if (endYear.isEmpty()) {
                return false;
            } else {
                try {
                    mIntValueEndYear = Integer.parseInt(endYear);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                return true;
            }
        } else {
            return true;
        }
    }

    private boolean isBeginYearHasCorrectValue() {
        String beginYear = mBeginYearEdit.getText().toString().trim();
        if (beginYear.isEmpty()) {
            return false;
        } else {
            try {
                mIntValueBeginYear = Integer.parseInt(beginYear);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }
    }

    private void setSwitchListener() {
        mCurrentJobSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCurrentJobSwitch.isChecked()) {
                    mEndYearInputLayout.setVisibility(View.GONE);
                    AndroidUtils.hideSoftKeyboard(getActivity());
                } else {
                    mEndYearInputLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_edit_empty, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initToolbar();
        setSwitchListener();
        return view;
    }
}
