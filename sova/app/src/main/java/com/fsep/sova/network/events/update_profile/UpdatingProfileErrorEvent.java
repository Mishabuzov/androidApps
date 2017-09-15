package com.fsep.sova.network.events.update_profile;

import com.fsep.sova.network.events.BaseErrorEvent;

public class UpdatingProfileErrorEvent extends BaseErrorEvent {
    public UpdatingProfileErrorEvent(int responseCode) {
        super(responseCode);
    }

    public UpdatingProfileErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
