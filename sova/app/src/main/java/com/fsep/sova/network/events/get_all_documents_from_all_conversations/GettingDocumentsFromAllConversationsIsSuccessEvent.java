package com.fsep.sova.network.events.get_all_documents_from_all_conversations;

import com.fsep.sova.models.Document;

import java.util.List;

public class GettingDocumentsFromAllConversationsIsSuccessEvent {
    private List<Document> mDocumentList;

    public GettingDocumentsFromAllConversationsIsSuccessEvent(List<Document> documentList) {
        mDocumentList = documentList;
    }

    public List<Document> getDocumentList() {
        return mDocumentList;
    }
}
