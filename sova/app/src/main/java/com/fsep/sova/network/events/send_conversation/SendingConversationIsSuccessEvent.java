package com.fsep.sova.network.events.send_conversation;

import com.fsep.sova.models.Conversation;

public class SendingConversationIsSuccessEvent {
    private Conversation mNewMessage;

    public SendingConversationIsSuccessEvent(Conversation newMessage) {
        mNewMessage = newMessage;
    }

    public Conversation getNewMessage() {
        return mNewMessage;
    }
}
