package com.fsep.sova.network.events.getcomments;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingCommentsErrorEvent extends BaseErrorEvent {
    public GettingCommentsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingCommentsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
