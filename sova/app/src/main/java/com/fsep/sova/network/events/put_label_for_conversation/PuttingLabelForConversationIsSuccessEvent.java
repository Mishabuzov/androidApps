package com.fsep.sova.network.events.put_label_for_conversation;

import com.fsep.sova.models.Conversation;

public class PuttingLabelForConversationIsSuccessEvent {
    private Conversation mConversation;

    public PuttingLabelForConversationIsSuccessEvent(Conversation conversation) {
        mConversation = conversation;
    }

    public Conversation getConversation() {
        return mConversation;
    }
}
