package com.kpfu.mikhail.vk.content.attachments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum  AttachmentType {

    PHOTO("photo"),
    VIDEO("video"),
    AUDIO("audio"),
    LINK("link"),
    DOC("doc"),
    NOTE("note"),
    POLL("poll"),
    PAGE("page"),
    ALBUM("album"),
    PHOTOS("photos_list"),
    MARKET("market"),
    MARKET_ALBUM("market_album"),
    STICKER("sticker");

    private String mDescription;

    AttachmentType(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


    @Override
    @JsonValue
    public String toString() {
        return mDescription;
    }

}
