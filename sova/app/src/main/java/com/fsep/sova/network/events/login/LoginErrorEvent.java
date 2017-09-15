package com.fsep.sova.network.events.login;


import com.fsep.sova.network.events.BaseErrorEvent;

public class LoginErrorEvent extends BaseErrorEvent {

    public LoginErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
