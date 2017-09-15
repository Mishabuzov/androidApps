package com.fsep.sova.network.events.get_conversations;

import com.fsep.sova.models.Conversation;

import java.util.List;

public class GettingConversationsIsSuccessEvent {
    private List<Conversation> mConversations;

    public GettingConversationsIsSuccessEvent(List<Conversation> conversations) {
        mConversations = conversations;
    }

    public List<Conversation> getConversations() {
        return mConversations;
    }
}
