package com.fsep.sova.network.events.get_user_by_id;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingUserByIdErrorEvent extends BaseErrorEvent {
    public GettingUserByIdErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingUserByIdErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
