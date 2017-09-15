package com.fsep.sova.network.events.get_user_profile;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingUserProfileErrorEvent extends BaseErrorEvent {
    public GettingUserProfileErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingUserProfileErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
