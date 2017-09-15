package com.fsep.sova.network.events.registration.registration_user;

import com.fsep.sova.network.events.BaseErrorEvent;

public class FinishRegisterErrorEvent extends BaseErrorEvent {
    public FinishRegisterErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public FinishRegisterErrorEvent(int responseCode) {
        super(responseCode);
    }
}
