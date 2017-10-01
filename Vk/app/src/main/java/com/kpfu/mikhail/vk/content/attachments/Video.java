package com.kpfu.mikhail.vk.content.attachments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    @JsonProperty("photo_320")
    private String thumbnail;

    @JsonProperty("access_key")
    private String accessKey;

    public Video(String thumbnail, String accessKey) {
        this.thumbnail = thumbnail;
        this.accessKey = accessKey;
    }

    public Video() {
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

}
