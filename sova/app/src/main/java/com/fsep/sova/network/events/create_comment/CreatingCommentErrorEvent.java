package com.fsep.sova.network.events.create_comment;

import com.fsep.sova.network.events.BaseErrorEvent;

public class CreatingCommentErrorEvent extends BaseErrorEvent {
    public CreatingCommentErrorEvent(int responseCode) {
        super(responseCode);
    }

    public CreatingCommentErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
