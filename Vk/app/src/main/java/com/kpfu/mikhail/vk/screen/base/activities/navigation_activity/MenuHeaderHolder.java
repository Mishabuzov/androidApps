package com.kpfu.mikhail.vk.screen.base.activities.navigation_activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpfu.mikhail.vk.R;
import com.kpfu.mikhail.vk.content.Profile;
import com.kpfu.mikhail.vk.utils.PreferenceUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

class MenuHeaderHolder extends RecyclerView.ViewHolder {

    @BindString(R.string.name_format) String mNameFormat;

    @BindView(R.id.main_header_layout) ViewGroup mMainLayout;

    @BindView(R.id.username_tv) TextView mUsernameTv;

    @BindView(R.id.status_tv) TextView mStatusTv;

    MenuHeaderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind() {
        Profile profile = PreferenceUtils.getUserProfile();
        mUsernameTv.setText(String.format(mNameFormat, profile.getFirstName(), profile.getLastName()));
        mStatusTv.setText(profile.getStatus());
        mMainLayout.setVisibility(View.VISIBLE);
    }
}
