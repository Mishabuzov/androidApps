package com.fsep.sova.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.activities.CitySearchActivity;
import com.fsep.sova.activities.CountrySearchActivity;
import com.fsep.sova.activities.EducationEditActivity;
import com.fsep.sova.activities.JobEditActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.local_events.RefreshingProfileEvent;
import com.fsep.sova.models.CountryCity;
import com.fsep.sova.models.Job;
import com.fsep.sova.models.Photo;
import com.fsep.sova.models.Resume;
import com.fsep.sova.models.School;
import com.fsep.sova.models.University;
import com.fsep.sova.models.UpdateUserInfoModel;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionUpdateProfile;
import com.fsep.sova.network.actions.ActionUpdateUserInfo;
import com.fsep.sova.network.events.get_ticket.GettingTicketErrorEvent;
import com.fsep.sova.network.events.get_ticket.GettingTicketIsSuccessEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadIsErrorEvent;
import com.fsep.sova.network.events.loadfile.ImageFileUploadSuccessEvent;
import com.fsep.sova.network.events.update_profile.UpdatingProfileErrorEvent;
import com.fsep.sova.network.events.update_profile.UpdatingProfileIsSuccessEvent;
import com.fsep.sova.network.events.update_user_info.UpdatingUserInfoErrorEvent;
import com.fsep.sova.network.events.update_user_info.UpdatingUserInfoIsSuccessEvent;
import com.fsep.sova.utils.AndroidUtils;
import com.fsep.sova.utils.AttachmentsHelper;
import com.fsep.sova.utils.AttachmentsLoaderHelper;
import com.fsep.sova.utils.Constants;
import com.fsep.sova.utils.PrefUtils;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.ButterKnife;

import static com.fsep.sova.utils.Constants.COUNTRY_CITY;

public class EditProfileFragment extends BaseLoadableFragment {

    public static final String EXTRA_USER = "com.fsep.sova.fragments.EXTRA_USER";
    public static final int REQUEST_CODE_SELECT_CITY = 1;
    public static final int REQUEST_CODE_SELECT_COUNTRY = 2;

    @BindDimen(R.dimen.edit_profile_divider_width) int mHorizontalLineWidth;
    @BindDimen(R.dimen.profile_line_separator_margins) int mLineSeparatorMargins;
    @BindDimen(R.dimen.profile_tags_right_margin) int mTagsMargin;
    @BindString(R.string.exit_dialog_text) String mExitAlertDialogText;
    @BindString(R.string.exit_dialog_positive_item) String mExitAlertDialogPositiveAnswer;
    @BindString(R.string.exit_dialog_negative_item) String mExitAlertDialogNegativeAnswer;
    @BindString(R.string.exit_dialog_neutral_item) String mExitAlertDialogCancelAnswer;
    @BindColor(R.color.colorSecondText) int mTagsColor;
    @BindColor(R.color.black_text) int mBlackTextColor;
    @Bind(R.id.avatar) RoundedImageView mIvAvatar;
    @Bind(R.id.edit_name) EditText mEditName;
    @Bind(R.id.edit_surname) EditText mEditSurname;
    @Bind(R.id.edit_nickname) TextView mEditNick;
    @Bind(R.id.et_CV) EditText mEtDescription;
    @Bind(R.id.user_birthday_tv) TextView mTvBirthday;
    @Bind(R.id.ll_profile_birthday) LinearLayout llProfileBirthday;
    @Bind(R.id.user_country_tv) TextView mUserCountryTv;
    @Bind(R.id.user_city_tv) TextView mUserCityTv;
    @Bind(R.id.header_of_education_layout) LinearLayout mHeaderOfEducationLayout;
    @Bind(R.id.education_layout) LinearLayout mEducationLayout;
    @Bind(R.id.header_of_skills) LinearLayout mHeaderOfSkills;
    @Bind(R.id.tags_skills_view) LinearLayout mSkillsLayout;
    @Bind(R.id.header_of_experience_layout) LinearLayout mHeaderOfWorkExperienceLayout;
    @Bind(R.id.experience_layout) LinearLayout mWorkExperienceLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.et_add_tag) EditText mAddTagEdit;

    private Resume mUserProfile;
    private AttachmentsHelper mAttachmentsHelper;
    private AttachmentsLoaderHelper mAttachmentsLoaderHelper;
    private Photo mNewAvatar;
    private List<String> mTags;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, v);
        mUserProfile = getArguments().getParcelable(EXTRA_USER);
        mAttachmentsHelper = new AttachmentsHelper(this, getActivity(), mIvAvatar);
        mAttachmentsLoaderHelper = new AttachmentsLoaderHelper(getActivity(), mAttachmentsHelper);
        initView();
        configureToolbar();
        llProfileBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentBirthdayDate = mTvBirthday.getText().toString();
                showDatePickerDialog(toDateTime(currentBirthdayDate));
            }
        });
        return v;
    }

    private void configureToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.back);
    }

    private void showExitDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(R.string.exit_dialog_title);
        alertDialog.setMessage(mExitAlertDialogText);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mExitAlertDialogPositiveAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateUserInfo();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mExitAlertDialogNegativeAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, mExitAlertDialogCancelAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.closeOptionsMenu();
            }
        });
        alertDialog.show();
    }

    private void saveUpdatedUserInfo() {
        PrefUtils.saveUserName(App.context, String.valueOf(mEditName.getText()));
        PrefUtils.saveUserSurname(App.context, String.valueOf(mEditSurname.getText()));
        PrefUtils.saveUserEmail(App.context, String.valueOf(mEditNick.getText()));
        PrefUtils.saveUserDescription(App.context, String.valueOf(mEtDescription.getText()));
        if (mNewAvatar != null) {
            PrefUtils.saveUserAvatar(App.context, String.valueOf(mNewAvatar.getOriginalUrl()));
        }
    }

    private void updateUserInfo() {
        if (isThereEmptyFields()) {
            return;
        }
        UpdateUserInfoModel userInfoModel = new UpdateUserInfoModel();
        userInfoModel.setFirstName(String.valueOf(mEditName.getText()).trim());
        userInfoModel.setLastName(String.valueOf(mEditSurname.getText()).trim());
        userInfoModel.setDescription(String.valueOf(mEtDescription.getText()).trim());
        if (!String.valueOf(mEditNick.getText()).trim().equals(PrefUtils.getUserEmail(App.context))) {
            userInfoModel.setNickName(String.valueOf(mEditNick.getText()).trim());
        }
        if (mNewAvatar != null) {
            userInfoModel.setAvatar(mNewAvatar);
        }
        Action action = new ActionUpdateUserInfo(PrefUtils.getUserId(App.context), userInfoModel);
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    private boolean isThereEmptyFields() {
        if (String.valueOf(mEditName.getText()).trim().isEmpty()
                || String.valueOf(mEditSurname.getText()).trim().isEmpty()
                || String.valueOf(mEditNick.getText()).trim().isEmpty()) {
            UiUtils.showToast(R.string.profile_edit_empty_names);
            return true;
        } else {
            return false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdatingUserInfoIsSuccessEvent event) {
        saveUpdatedUserInfo();
        sendUpdateUserProfileRequest();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdatingUserInfoErrorEvent errorEvent) {
        showProfileUpdateErrorToast();
    }

    private void sendUpdateUserProfileRequest() {
        mUserProfile.setSkills((ArrayList<String>) mTags);
        Action action = new ActionUpdateProfile(mUserProfile);
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdatingProfileIsSuccessEvent event) {
        RefreshingProfileEvent refreshingEvent = new RefreshingProfileEvent(mUserProfile);
        EventBus.getDefault().postSticky(refreshingEvent);
        getActivity().finish();
        UiUtils.showToast(R.string.profile_successfully_updated);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdatingProfileErrorEvent event) {
        showProfileUpdateErrorToast();
    }

    public void showProfileUpdateErrorToast() {
        UiUtils.showToast(R.string.profile_update_error);
    }

    private void initView() {
        setAvatar();
        mEditName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf"));
        mEditName.setText(PrefUtils.getUserName(App.context));
        mEditSurname.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf"));
        mEditSurname.setText(PrefUtils.getUserSurname(App.context));
        mEditNick.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
        mEditNick.setText(PrefUtils.getUserEmail(App.context));
        setCv();
        mTvBirthday.setText(UiUtils.getHumanReadableDate(mUserProfile.getBirthday()));
        mUserCountryTv.setText(mUserProfile.getCountry().getTitle());
        mUserCountryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CountrySearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_COUNTRY);
            }
        });
        setUserCityInfo();
        fillEducation();
        fillTags();
        fillWorkExperience();
    }

    private void setUserCityInfo() {
        if (mUserProfile.getCountry().getId() == 0) {
            mUserCityTv.setVisibility(View.GONE);
        } else {
            mUserCityTv.setText(mUserProfile.getCity().getTitle());
            mUserCityTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                    intent.putExtra(Constants.COUNTRY_ID, mUserProfile.getCountry().getId());
                    startActivityForResult(intent, REQUEST_CODE_SELECT_CITY);
                    mUserCityTv.setTextColor(mBlackTextColor);
                }
            });
        }
    }

    public void showDatePickerDialog(DateTime dateTime) {
        new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    DateTime mDateTime = new DateTime()
                            .withYear(year)
                            .withMonthOfYear(monthOfYear)
                            .withDayOfMonth(dayOfMonth);
                    long birthdayUnixTime = mDateTime.getMillis();
                    mUserProfile.setBirthday(birthdayUnixTime);
                    mTvBirthday.setText(UiUtils.getHumanReadableDate(birthdayUnixTime / 1000));
                },
                dateTime.getYear(),
                dateTime.getMonthOfYear(),
                dateTime.getDayOfMonth()).show();
    }

    public DateTime toDateTime(String dateTime) {
        return DateTime.parse(dateTime, DateTimeFormat.forPattern("dd.MM.yyyy"));
    }

    private void fillWorkExperience() {
        List<Job> workExperienceList = mUserProfile.getExperience();
        for (int i = 0; i < workExperienceList.size(); i++) {
            Job job = workExperienceList.get(i);
            mWorkExperienceLayout.addView(
                    createLayoutForWorkExperience(
                            job.getCompanyTitle(),
                            job.getPost(),
                            UiUtils.getHumanReadableDate(job.getBeginingDate()),
                            UiUtils.getHumanReadableDate(job.getEndingDate()),
                            String.format(".%1$s, %2$s", job.getCountry().getTitle(), job.getCity().getTitle()),
                            i + 1 == workExperienceList.size()));
        }
        mWorkExperienceLayout.addView(createAddTextViewLayout(mWorkExperienceLayout, getString(R.string.edit_profile_fragment_tv_add_work_experience), workExperienceList.isEmpty(), false));
    }

    private LinearLayout createLayoutForWorkExperience(String orgName, String workPost, String beginTime, String finishTime, String place, boolean isLastElement) {
        LinearLayout workLayout = new LinearLayout(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.work_experience_layout, workLayout, false);
        TextView orgNameTv = (TextView) v.findViewById(R.id.orgNameTv);
        orgNameTv.setText(orgName);
        TextView periodTv = (TextView) v.findViewById(R.id.periodTv);
        periodTv.setText(String.format("%1$s - %2$s", beginTime, finishTime));
        TextView workPostTv = (TextView) v.findViewById(R.id.workPostTv);
        workPostTv.setText(workPost);
        TextView placeTv = (TextView) v.findViewById(R.id.workPlaceTv);
        placeTv.setText(place);
        if (!isLastElement) {
            addSeparatorLine(v);
        }
        return v;
    }

    private void setAvatar() {
        String avatarUri = PrefUtils.getUserAvatar(App.context);
        if (avatarUri != null) {
            Picasso.with(App.context).load(avatarUri).into(mIvAvatar);
        } else {
            Picasso.with(App.context).load(R.drawable.human).into(mIvAvatar);
        }
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttachmentsHelper.showAddPhotoDialog(false);
            }
        });
    }

    private void setCv() {
        String cv = PrefUtils.getUserDescription(App.context);
        if (!cv.isEmpty()) {
            mEtDescription.setVisibility(View.VISIBLE);
            mEtDescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
            mEtDescription.setText(cv);
        }
    }

    private void fillEducation() {
        List<School> schoolList = mUserProfile.getSecondaryEducation();
        List<University> universityList = mUserProfile.getHigherEducation();
        if (!schoolList.isEmpty() || !universityList.isEmpty()) {
            mHeaderOfEducationLayout.setVisibility(View.VISIBLE);
            mEducationLayout.setVisibility(View.VISIBLE);
        }
        //fill secondary education
        for (int i = 0; i < schoolList.size(); i++) {
            School school = schoolList.get(i);
            boolean isCurrentEducation = school.isCurrentSchool();
            mEducationLayout.addView(createLayoutForEducation(
                    school.getSchool().getTitle(),
                    UiUtils.getHumanReadableDate(school.getBeginingDate()),
                    UiUtils.getHumanReadableDate(school.getEndingDate()),
                    i + 1 == schoolList.size() && universityList.isEmpty(),
                    isCurrentEducation));
        }
        //fill higher education
        for (int i = 0; i < universityList.size(); i++) {
            University university = universityList.get(i);
            boolean isCurrentEducation = university.isCurrentUniversity();
            mEducationLayout.addView(createLayoutForEducation(
                    university.getUniversity().getTitle(),
                    UiUtils.getHumanReadableDate(university.getBeginingDate()),
                    UiUtils.getHumanReadableDate(university.getEndingDate()),
                    i + 1 == universityList.size(),
                    isCurrentEducation));
        }
        mEducationLayout.addView(createAddTextViewLayout(mEducationLayout, getActivity().getString(R.string.edit_profile_fragment_tv_add_education), universityList.isEmpty() && schoolList.isEmpty(), true));
    }

    private LinearLayout createLayoutForEducation(
            String univerName,
            String beginYear,
            String finishYear,
            boolean isLastElement,
            boolean isCurrentEducation) {
        LinearLayout educationLayout = new LinearLayout(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.education_layout, educationLayout, false);
        TextView univerTv = (TextView) v.findViewById(R.id.univerNameTv);
        univerTv.setText(univerName);
        if (isCurrentEducation) {
            univerTv.setText(univerTv.getText() + " - Текущее место учёбы");
        }
        TextView yearsTv = (TextView) v.findViewById(R.id.studyYearsTv);
        yearsTv.setText(String.format("%1$s - %2$s", beginYear, finishYear));
        if (!isLastElement) {
            addSeparatorLine(v);
        }
        return v;
    }

    private View createAddTextViewLayout(ViewGroup container, String text, boolean isEmptyLayout, boolean isLayoutForEducationAdding) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.view_tv_add_layout, container, false);
        if (isEmptyLayout) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 0);
            v.setLayoutParams(params);
        }
        TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
        tvAdd.setText(text);
        if (isLayoutForEducationAdding) {
            Intent intent = new Intent(getActivity(), EducationEditActivity.class);
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), JobEditActivity.class);
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
        return v;
    }

    private void addSeparatorLine(LinearLayout layout) {
        View view = new View(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHorizontalLineWidth);
        params.setMargins(0, mLineSeparatorMargins, 0, mLineSeparatorMargins);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.color.edit_profile_divider_color);
        layout.addView(view);
    }

    public void fillTags() {
        mTags = mUserProfile.getSkills();
        if (!mTags.isEmpty()) {
            mHeaderOfSkills.setVisibility(View.VISIBLE);
            mSkillsLayout.setVisibility(View.VISIBLE);
        }
        for (String tag : mTags) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.view_tag_layout, mSkillsLayout, false);
            TextView tv = (TextView) v.findViewById(R.id.tv_tag);
            tv.setText(tag);
            tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
            tv.setTextColor(mTagsColor);
            LinearLayout.LayoutParams tagsParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagsParam.setMargins(0, 0, mTagsMargin, 0);
            v.setLayoutParams(tagsParam);
            mSkillsLayout.addView(v, 0);
        }
        initEditTextForAddingTags();
    }

    private void initEditTextForAddingTags() {
        mAddTagEdit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
        mAddTagEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        && !mAddTagEdit.getText().toString().trim().equals("")) {
                    if (mTags == null) {
                        mTags = new ArrayList<>();
                    }
                    addTag(mAddTagEdit.getText().toString().trim());
                    mAddTagEdit.setText("");
                    return true;
                }
                return false;
            }
        });
        mAddTagEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //filter that excepts calling onKey twice
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL
                        && mAddTagEdit.getText().toString().equals("")
                        && mTags.size() != 0) {
                    deleteLastTag();
                    return true;
                }
                return false;
            }
        });
    }

    private void addTag(String text) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.view_tag_layout, mSkillsLayout, false);
        LinearLayout tagLayout = (LinearLayout) v.findViewById(R.id.tag_layout);
        TextView tv = (TextView) v.findViewById(R.id.tv_tag);
        tv.setText(text);
        mTags.add(text);
        mSkillsLayout.addView(tagLayout, mTags.size() - 1);
    }

    private void deleteLastTag() {
        mSkillsLayout.removeViewAt(mTags.size() - 1);
        mTags.remove(mTags.size() - 1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTicketIsSuccessEvent event) {
        mAttachmentsLoaderHelper.onSuccessGettingTicket(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingTicketErrorEvent event) {
        hideProgressBar();
        UiUtils.showToast(R.string.content_dialog_error_add_task);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageFileUploadSuccessEvent event) {
        mNewAvatar = event.getPhoto();
        mAttachmentsLoaderHelper.showOnUploadingFileToast();
//        mAttachmentsLoaderHelper.onSuccessImageFileUploading(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageFileUploadIsErrorEvent event) {
        UiUtils.showToast("Error upload file");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_done:
                updateUserInfo();
                break;
            case android.R.id.home:
                showExitDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_CITY) {
                CountryCity city = data.getParcelableExtra(COUNTRY_CITY);
                mUserProfile.getCity().setTitle(city.getTitle());
                mUserProfile.getCity().setId(city.getId());
                mUserCityTv.setText(city.getTitle());
            } else if (requestCode == REQUEST_CODE_SELECT_COUNTRY) {
                CountryCity country = data.getParcelableExtra(COUNTRY_CITY);
                mUserProfile.getCountry().setId(country.getId());
                mUserProfile.getCountry().setTitle(country.getTitle());
                mUserCityTv.setTextColor(mTagsColor);
                mUserCityTv.setText("ВЫБЕРИТЕ ГОРОД");
                mUserCountryTv.setText(country.getTitle());
            } else {
                mAttachmentsHelper.processMediaResult(requestCode, data);
            }
            AndroidUtils.hideSoftKeyboard(getActivity());
        }
    }
}
