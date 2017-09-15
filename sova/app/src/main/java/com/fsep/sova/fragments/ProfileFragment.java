package com.fsep.sova.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fsep.sova.App;
import com.fsep.sova.R;
import com.fsep.sova.activities.EditProfileActivity;
import com.fsep.sova.fragments.base.BaseLoadableFragment;
import com.fsep.sova.local_events.RefreshingProfileEvent;
import com.fsep.sova.models.Job;
import com.fsep.sova.models.Resume;
import com.fsep.sova.models.School;
import com.fsep.sova.models.University;
import com.fsep.sova.network.ServiceHelper;
import com.fsep.sova.network.actions.Action;
import com.fsep.sova.network.actions.ActionGetUserProfile;
import com.fsep.sova.network.events.get_user_profile.GettingUserProfileIsSuccessEvent;
import com.fsep.sova.utils.PrefUtils;
import com.fsep.sova.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends BaseLoadableFragment {

    @BindDimen(R.dimen.profile_grid_horizontal_line_width) int mHorizontalLineWidth;
    @BindDimen(R.dimen.profile_line_separator_margins) int mLineSeparatorMargins;
    @BindDimen(R.dimen.profile_tags_right_margin) int mTagsMargin;
    @BindColor(R.color.black_text) int mBlackTextColor;
    @BindColor(R.color.secondary_text_color) int mSecondaryTextColor;
    @BindColor(R.color.colorSecondText) int mTagsColor;
    @Bind(R.id.tv_CV) TextView mCvTv;
    @Bind(R.id.avatar) RoundedImageView mAvatarIv;
    @Bind(R.id.tv_name) TextView mNameTv;
    @Bind(R.id.tv_email) TextView mEmailTv;
    @Bind(R.id.responders_count_tv) TextView mRespondersCountTv;
    @Bind(R.id.responses_count_tv) TextView mResponsesCountTv;
    @Bind(R.id.posts_count_tv) TextView mPostsCountTv;
    @Bind(R.id.user_birthday_tv) TextView mUserBirthdayTv;
    @Bind(R.id.user_country_tv) TextView mUserCountryTv;
    @Bind(R.id.user_city_tv) TextView mUserCityTv;
    @Bind(R.id.education_layout) LinearLayout mEducationLayout;
    @Bind(R.id.tags_skills_view) LinearLayout mSkillsLayout;
    @Bind(R.id.experience_layout) LinearLayout mWorkExperienceLayout;
    @Bind(R.id.ok_btn) ImageButton mOkBtn;
    @Bind(R.id.user_popularity_layout) GridLayout mUserPopularityGrid;
    @Bind(R.id.header_of_education_layout) LinearLayout mHeaderOfEducationLayout;
    @Bind(R.id.header_of_experience_layout) LinearLayout mHeaderOfWorkExperienceLayout;
    @Bind(R.id.header_of_skills) LinearLayout mHeaderOfSkills;
    @Bind(R.id.main_scroll) ScrollView mMainScroll;
    private Resume mUserProfile;

    @OnClick(R.id.ok_btn)
    public void onClickOkBtn() {
        //TODO:Обработчик нажатия на галку
    }

    @OnClick(R.id.responders_layout)
    public void onClickRespondersLayout() {
        //TODO:Открытие списка подписчиков пользователя
    }

    @OnClick(R.id.responses_layout)
    public void onClickResponsesLayout() {
        //TODO:Открытие списка подписок пользователя
    }

    @OnClick(R.id.posts_layout)
    public void onClickPostsLayout() {
        //TODO:Открытие списка постов пользователя
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        loadData();
        return view;
    }

    private void loadData() {
        showProgressBar();
        Action action = new ActionGetUserProfile(PrefUtils.getUserId(App.context));
        ServiceHelper.getInstance().startActionService(getActivity(), action);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GettingUserProfileIsSuccessEvent event) {
        mUserProfile = event.getUserProfile();
        setDataIntoFragment();
        mMainScroll.setVisibility(View.VISIBLE);
        hideProgressBar();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshTask(RefreshingProfileEvent event) {
        clearOldData();
        loadData();
        MenuRefresher refresher = (MenuRefresher) getActivity();
        refresher.refreshNavigationView();
        EventBus.getDefault().removeStickyEvent(event);
    }

    private void clearOldData() {
        mCvTv.setVisibility(View.GONE);
        mEducationLayout.removeAllViews();
        mWorkExperienceLayout.removeAllViews();
        mSkillsLayout.removeAllViews();
        mHeaderOfSkills.setVisibility(View.GONE);
        mHeaderOfEducationLayout.setVisibility(View.GONE);
        mHeaderOfWorkExperienceLayout.setVisibility(View.GONE);
    }

    private void fillWorkExperience() {
        List<Job> workExperienceList = mUserProfile.getExperience();
        if (!workExperienceList.isEmpty()) {
            mHeaderOfWorkExperienceLayout.setVisibility(View.VISIBLE);
            mWorkExperienceLayout.setVisibility(View.VISIBLE);
        }
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
    }

    private void addSeparatorLine(LinearLayout layout) {
        View view = new View(getActivity().getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHorizontalLineWidth);
        params.setMargins(0, mLineSeparatorMargins, 0, mLineSeparatorMargins);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.color.secondary_text_color);
        layout.addView(view);
    }

    private LinearLayout createLayoutForEducation(String univerName,
                                                  String beginYear,
                                                  String finishYear,
                                                  boolean isLastElement,
                                                  boolean isCurrentEducation) {
        LinearLayout educationLayout = new LinearLayout(getContext());
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


    private void setDataIntoFragment() {
        mNameTv.setText(String.format("%1$s %2$s", PrefUtils.getUserName(App.context), PrefUtils.getUserSurname(App.context)));
        setAvatar();
        mEmailTv.setText(PrefUtils.getUserEmail(App.context));
        mUserBirthdayTv.setText(UiUtils.getHumanReadableDate(mUserProfile.getBirthday()));
        mUserCountryTv.setText(mUserProfile.getCountry().getTitle());
        mUserCityTv.setText(mUserProfile.getCity().getTitle());
        fillCounts();
        setCv();
        mOkBtn.setVisibility(View.VISIBLE);
        mUserPopularityGrid.setVisibility(View.VISIBLE);
        fillEducation();
        fillWorkExperience();
        fillTags();
    }

    private void setAvatar() {
        String avatarUri = PrefUtils.getUserAvatar(App.context);
        if (avatarUri != null) {
            Picasso.with(App.context).load(PrefUtils.getUserAvatar(App.context)).into(mAvatarIv);
        } else {
            Picasso.with(App.context).load(R.drawable.human).into(mAvatarIv);
        }
    }

    private void setCv() {
        String cv = PrefUtils.getUserDescription(App.context);
        if (!cv.isEmpty()) {
            mCvTv.setVisibility(View.VISIBLE);
            mCvTv.setText(cv);
        }
    }


    private void fillCounts() {
        mUserPopularityGrid.setVisibility(View.VISIBLE);
        mRespondersCountTv.setText(PrefUtils.getUserCounts(App.context, PrefUtils.FOLLOWERS_COUNT));
        mResponsesCountTv.setText(PrefUtils.getUserCounts(App.context, PrefUtils.FOLLOWINGS_COUNT));
        mPostsCountTv.setText(PrefUtils.getUserCounts(App.context, PrefUtils.POSTS_COUNT));
    }

    private void fillTags() {
        List<String> tagsList = mUserProfile.getSkills();
        if (!tagsList.isEmpty()) {
            mHeaderOfSkills.setVisibility(View.VISIBLE);
            mSkillsLayout.setVisibility(View.VISIBLE);
        }
        for (String tag : tagsList) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.view_tag_layout, mSkillsLayout, false);
            TextView tv = (TextView) v.findViewById(R.id.tv_tag);
            tv.setText(tag);
            tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf"));
            tv.setTextColor(mTagsColor);
            LinearLayout.LayoutParams tagsParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tagsParam.setMargins(0, 0, mTagsMargin, 0);
            v.setLayoutParams(tagsParam);
            mSkillsLayout.addView(v);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_done:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra(EditProfileFragment.EXTRA_USER, mUserProfile);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface MenuRefresher {
        void refreshNavigationView();
    }
}
