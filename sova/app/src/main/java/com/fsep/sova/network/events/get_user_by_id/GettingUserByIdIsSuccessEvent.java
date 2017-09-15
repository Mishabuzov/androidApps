package com.fsep.sova.network.events.get_user_by_id;

import com.fsep.sova.models.UserInfo;

public class GettingUserByIdIsSuccessEvent {

    private UserInfo mUserInfo;

    public GettingUserByIdIsSuccessEvent(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }
}
