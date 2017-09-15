package com.fsep.sova.network.events.delete_post;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeletingPostErrorEvent extends BaseErrorEvent {
    public DeletingPostErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeletingPostErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
