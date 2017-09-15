package com.fsep.sova.network.events.registration.registration_check_user;

import com.fsep.sova.network.events.BaseErrorEvent;

public class RegisterCheckUserErrorEvent extends BaseErrorEvent {
    public RegisterCheckUserErrorEvent(int responseCode) {
        super(responseCode);
    }

    public RegisterCheckUserErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
