package com.fsep.sova.network.events.get_conversations;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingConversationsErrorEvent extends BaseErrorEvent {

    public GettingConversationsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingConversationsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
