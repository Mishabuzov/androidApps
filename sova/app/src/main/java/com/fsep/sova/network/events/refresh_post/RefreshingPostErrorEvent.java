package com.fsep.sova.network.events.refresh_post;

import com.fsep.sova.network.events.BaseErrorEvent;

public class RefreshingPostErrorEvent extends BaseErrorEvent {
    public RefreshingPostErrorEvent(int responseCode) {
        super(responseCode);
    }

    public RefreshingPostErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
