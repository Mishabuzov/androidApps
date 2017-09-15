package com.fsep.sova.network.events.registration.registration_confirm_code;

import com.fsep.sova.network.events.BaseErrorEvent;

public class RegisterConfirmingMobileCodeErrorEvent extends BaseErrorEvent {
    public RegisterConfirmingMobileCodeErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public RegisterConfirmingMobileCodeErrorEvent(int responseCode) {
        super(responseCode);
    }
}
