package com.fsep.sova.network.events.update_user_info;

import com.fsep.sova.models.UserInfo;

public class UpdatingUserInfoIsSuccessEvent {
    private UserInfo mUpdatedUser;

    public UpdatingUserInfoIsSuccessEvent(UserInfo updatedUser) {
        mUpdatedUser = updatedUser;
    }

    public UserInfo getUpdatedUser() {
        return mUpdatedUser;
    }
}
