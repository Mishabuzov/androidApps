package com.fsep.sova.models;

import android.os.Parcel;

public class AttachmentVideo extends Attachment {

    private Video mVideo;

    public Video getVideo() {
        return mVideo;
    }

    public void setVideo(Video video) {
        mVideo = video;
    }
}
