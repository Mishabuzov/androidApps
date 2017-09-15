package com.fsep.sova.network.events.send_conversation;

import com.fsep.sova.network.events.BaseErrorEvent;

public class SendingConversationErrorEvent extends BaseErrorEvent {
    public SendingConversationErrorEvent(int responseCode) {
        super(responseCode);
    }

    public SendingConversationErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
