package com.fsep.sova.network.events.get_user_profile;

import com.fsep.sova.models.Resume;

public class GettingUserProfileIsSuccessEvent {

    private Resume mUserProfile;

    public GettingUserProfileIsSuccessEvent(Resume userProfile) {
        mUserProfile = userProfile;
    }

    public Resume getUserProfile() {
        return mUserProfile;
    }

    public void setUserProfile(Resume userProfile) {
        mUserProfile = userProfile;
    }
}
