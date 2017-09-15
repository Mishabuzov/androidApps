package com.fsep.sova.models;

import android.os.Parcel;

public class AttachmentImage extends Attachment {

    private Photo mPhoto;

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto = photo;
    }
}
