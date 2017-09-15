package com.fsep.sova.network.events.delete_comment;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeletingCommentErrorEvent extends BaseErrorEvent {
    public DeletingCommentErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeletingCommentErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
