package com.fsep.sova.network.events.get_conversation_info;

import com.fsep.sova.models.ConversationInfo;

public class GettingConversationInfoIsSuccessEvent {
    private ConversationInfo mConversationInfo;

    public GettingConversationInfoIsSuccessEvent(ConversationInfo conversationInfo) {
        mConversationInfo = conversationInfo;
    }

    public ConversationInfo getConversationInfo() {
        return mConversationInfo;
    }
}
