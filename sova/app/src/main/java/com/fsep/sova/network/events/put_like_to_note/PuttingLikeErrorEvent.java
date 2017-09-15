package com.fsep.sova.network.events.put_like_to_note;

import com.fsep.sova.network.events.BaseErrorEvent;

public class PuttingLikeErrorEvent extends BaseErrorEvent {
    public PuttingLikeErrorEvent(int responseCode) {
        super(responseCode);
    }

    public PuttingLikeErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
