package com.fsep.sova.models;

import android.os.Parcel;

// TODO: 04.06.16 объединить вместе модели attacment
public class AttachmentDocument extends Attachment {

    private Document mDocument;

    public Document getDocument() {
        return mDocument;
    }

    public void setDocument(Document document) {
        mDocument = document;
    }
}
