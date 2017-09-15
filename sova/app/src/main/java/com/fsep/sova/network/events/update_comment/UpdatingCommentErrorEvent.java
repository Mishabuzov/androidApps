package com.fsep.sova.network.events.update_comment;

import com.fsep.sova.network.events.BaseErrorEvent;

public class UpdatingCommentErrorEvent extends BaseErrorEvent {
    public UpdatingCommentErrorEvent(int responseCode) {
        super(responseCode);
    }

    public UpdatingCommentErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
