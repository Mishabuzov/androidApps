package com.fsep.sova.network.events.create_post;

import com.fsep.sova.network.events.BaseErrorEvent;

public class CreatingPostErrorEvent extends BaseErrorEvent {
    public CreatingPostErrorEvent(int responseCode) {
        super(responseCode);
    }

    public CreatingPostErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
