package com.fsep.sova.network.events.invite_user;

import com.fsep.sova.network.events.BaseErrorEvent;

public class InvitingUserErrorEvent extends BaseErrorEvent {
    public InvitingUserErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public InvitingUserErrorEvent(int responseCode) {
        super(responseCode);
    }
}
