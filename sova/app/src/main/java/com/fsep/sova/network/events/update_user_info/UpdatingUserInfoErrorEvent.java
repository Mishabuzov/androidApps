package com.fsep.sova.network.events.update_user_info;

import com.fsep.sova.network.events.BaseErrorEvent;

public class UpdatingUserInfoErrorEvent extends BaseErrorEvent {
    public UpdatingUserInfoErrorEvent(int responseCode) {
        super(responseCode);
    }

    public UpdatingUserInfoErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
