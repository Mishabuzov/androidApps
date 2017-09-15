package com.fsep.sova.network.events.registration.registration_send_mobile;

import com.fsep.sova.network.events.BaseErrorEvent;

public class RegisterSendingMobileErrorEvent extends BaseErrorEvent {
    public RegisterSendingMobileErrorEvent(int responseCode) {
        super(responseCode);
    }

    public RegisterSendingMobileErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
