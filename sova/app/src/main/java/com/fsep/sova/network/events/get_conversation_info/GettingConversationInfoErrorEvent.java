package com.fsep.sova.network.events.get_conversation_info;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingConversationInfoErrorEvent extends BaseErrorEvent {
    public GettingConversationInfoErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingConversationInfoErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
