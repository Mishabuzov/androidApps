package com.fsep.sova.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.fsep.sova.activities.CitySearchActivity;
import com.fsep.sova.activities.CountrySearchActivity;
import com.fsep.sova.activities.EducationChooseLvlActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.models.CountryCity;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.UiUtils;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fsep.sova.utils.Constants.COUNTRY_CITY;

public class EducationEditFragment extends BaseLoadableFragment {

    @BindColor(R.color.black_text) int mBlackTextColor;
    @BindColor(R.color.colorSecondText) int mSecondGrayColor;
    @BindString(R.string.education_level) String mEducationLvlBeginValue;
    @BindString(R.string.education_country) String mCountryBeginValue;
    @BindString(R.string.education_city) String mCityBeginValue;
    @BindString(R.string.education_center_name) String mEduCenterBeginValue;
    @BindString(R.string.edu_exit_dialog_description) String mExitDialogDescr;
    @BindString(R.string.edu_exit_dialog_positive) String mExitAlertDialogPositiveAnswer;
    @BindString(R.string.edu_exit_dialog_negative) String mExitAlertDialogNegativeAnswerAnswer;
    @BindString(R.string.edu_exit_dialog_neutral) String mExitAlertDialogNeutralAnswerAnswer;
    @Bind(R.id.edu_begin_year_edit) EditText mBeginYearEdit;
    @Bind(R.id.edu_end_year_edit) EditText mEndYearEdit;
    @Bind(R.id.job_end_year_layout) RelativeLayout mEndYearLayout;
    @Bind(R.id.current_job_switch) Switch mCurrentEduSwitch;
    @Bind(R.id.lvl_tv) TextView mLvlTv;
    @Bind(R.id.country_tv) TextView mCountryTv;
    @Bind(R.id.city_tv) TextView mCityTv;
    @Bind(R.id.company_name_tv) TextView mEduNameTv;
    @Bind(R.id.end_year_text_input_layout) TextInputLayout mEndYearInputLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    public static final int REQUEST_CHOOSING_EDU_LVL = 1;
    public static final int REQUEST_CHOOSING_EDU_COUNTRY = 2;
    public static final int REQUEST_CHOOSING_EDU_CITY = 3;
    public static final int REQUEST_CHOOSING_EDU_NAME = 4;
    private int mIntValueBeginYear = 0;
    private int mIntValueEndYear = 0;
    private int mEduLvlRes = 0;
    private CountryCity mCountry;
    private CountryCity mCity;

    public static final int REQUEST_CODE_SELECT_COUNTRY = 1;
    public static final int REQUEST_CODE_SELECT_CITY = 2;



    @OnClick(R.id.level_layout)
    public void onClickLevelLayout() {
        Intent intent = new Intent(getActivity(), EducationChooseLvlActivity.class);
        startActivityForResult(intent, REQUEST_CHOOSING_EDU_LVL);
    }

    @OnClick(R.id.country_layout)
    public void onClickCountryLayout() {
        Intent intent = new Intent(getActivity(), CountrySearchActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT_COUNTRY);
    }

    @OnClick(R.id.city_layout)
    public void onClickCityLayout() {
        if (mCountry == null) {
            UiUtils.showToast(R.string.edu_choose_city_before_country_error);
            return;
        }
        Intent intent = new Intent(getActivity(), CitySearchActivity.class);
        intent.putExtra(Constants.COUNTRY_ID, mCountry.getId());
        startActivityForResult(intent, REQUEST_CODE_SELECT_CITY);
    }

    @OnClick(R.id.education_layout)
    public void onClickEducationLayout() {
    }

    @OnClick(R.id.current_edu_layout)
    public void onCurrentEduSwitchClick() {
        if (mCurrentEduSwitch.isChecked()) {
            mCurrentEduSwitch.setChecked(false);
            mEndYearLayout.setVisibility(View.VISIBLE);
        } else {
            mCurrentEduSwitch.setChecked(true);
            mEndYearLayout.setVisibility(View.GONE);
            AndroidUtils.hideSoftKeyboard(getActivity());
        }
    }

    //TODO: вставить проверку полей перед принятем данных
    private boolean isAllDataHasInputtedCorrectly() {
        if (mLvlTv.getText().equals(mEducationLvlBeginValue)) {
            UiUtils.showToast(R.string.edu_lvl_empty_choice_toast);
            return false;
        }
        if (mCountryTv.getText().equals(mCountryBeginValue)) {
            UiUtils.showToast(R.string.country_empty_choice_toast);
            return false;
        }
        if (mCityTv.getText().equals(mCityBeginValue)) {
            UiUtils.showToast(R.string.edu_city_empty_choice_toast);
            return false;
        }
        if (mEduNameTv.getText().equals(mEduCenterBeginValue)) {
            UiUtils.showToast(R.string.edu_univer_name_empty_choice_toast);
            return false;
        }
        if (!isBeginYearHasCorrectValue()) {
            UiUtils.showToast(R.string.edu_begin_year_empty_choice_toast);
            return false;
        }
        if (!isEndYearHasCorrectValue()) {
            UiUtils.showToast(R.string.edu_end_year_empty_choice_toast);
            return false;
        } else {
            if (mIntValueEndYear - mIntValueBeginYear < 0) {
                UiUtils.showToast(R.string.edu_study_period_empty_choice_toast);
                return false;
            }
        }
        return true;
    }

    private boolean isEndYearHasCorrectValue() {
        if (!mCurrentEduSwitch.isChecked()) {
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
        mCurrentEduSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCurrentEduSwitch.isChecked()) {
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
        View view = inflater.inflate(R.layout.fragment_education_edit_empty, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        initToolbar();
        setSwitchListener();
        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSING_EDU_LVL:
                    mEduLvlRes = data.getIntExtra(Constants.EDU_LVL, 0);
                    if (mEduLvlRes != 0) {
                        mLvlTv.setTextColor(mBlackTextColor);
                        mLvlTv.setText(mEduLvlRes);
                    }
                    break;
                case REQUEST_CHOOSING_EDU_COUNTRY:
                    mCountry = data.getParcelableExtra(COUNTRY_CITY);
                    if (mCountry != null) {
                        mCountryTv.setTextColor(mBlackTextColor);
                        mCountryTv.setText(mCountry.getTitle());
                    }
                    if (!mCityTv.getText().equals(mCityBeginValue)) {
                        mCityTv.setTextColor(mSecondGrayColor);
                        mCityTv.setText(mCityBeginValue);
                    }
                    break;
                case REQUEST_CHOOSING_EDU_CITY:
                    mCity = data.getParcelableExtra(COUNTRY_CITY);
                    if (mCity != null) {
                        mCityTv.setTextColor(mBlackTextColor);
                        mCityTv.setText(mCity.getTitle());
                    }
                    break;
            }
        }
    }
}
