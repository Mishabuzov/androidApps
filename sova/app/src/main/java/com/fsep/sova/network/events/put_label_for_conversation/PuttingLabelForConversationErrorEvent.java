package com.fsep.sova.network.events.put_label_for_conversation;

import com.fsep.sova.network.events.BaseErrorEvent;

public class PuttingLabelForConversationErrorEvent extends BaseErrorEvent {
    public PuttingLabelForConversationErrorEvent(int responseCode) {
        super(responseCode);
    }

    public PuttingLabelForConversationErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
