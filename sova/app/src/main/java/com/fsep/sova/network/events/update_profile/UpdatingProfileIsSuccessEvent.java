package com.fsep.sova.network.events.update_profile;

import com.fsep.sova.models.Resume;

public class UpdatingProfileIsSuccessEvent {
    private Resume mProfile;

    public UpdatingProfileIsSuccessEvent(Resume profile) {
        mProfile = profile;
    }

    public Resume getProfile() {
        return mProfile;
    }
}
