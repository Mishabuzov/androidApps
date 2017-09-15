package com.fsep.sova.network.events.kick_user;

import com.fsep.sova.network.events.BaseErrorEvent;

public class KickingUserErrorEvent extends BaseErrorEvent {
    public KickingUserErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public KickingUserErrorEvent(int responseCode) {
        super(responseCode);
    }
}
