package com.fsep.sova.network.events.get_all_documents_from_all_conversations;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingDocumentsFromAllConversationsErrorEvent extends BaseErrorEvent {
    public GettingDocumentsFromAllConversationsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingDocumentsFromAllConversationsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
