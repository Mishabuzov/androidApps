package com.fsep.sova.network.events.find_users;

import com.fsep.sova.models.UserInfo;

import java.util.List;

public class FindingUsersIsSuccessEvent {
    private List<UserInfo> mUsers;

    public FindingUsersIsSuccessEvent(List<UserInfo> users) {
        mUsers = users;
    }

    public List<UserInfo> getUsers(){
        return mUsers;
    }
}
