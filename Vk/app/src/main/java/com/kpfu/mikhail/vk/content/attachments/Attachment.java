package com.kpfu.mikhail.vk.content.attachments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.model.VKApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {

    private AttachmentType type;

    private Photo photo;

    private Video video;

    public Attachment() {
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

}
