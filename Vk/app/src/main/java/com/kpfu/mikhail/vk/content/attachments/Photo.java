package com.kpfu.mikhail.vk.content.attachments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {

    @JsonProperty("photo_130")
    private String lowQualityPhoto;

    @JsonProperty("photo_604")
    private String mediumQualityPhoto;

    public Photo() {
    }

    public String getLowQualityPhoto() {
        return lowQualityPhoto;
    }

    public void setLowQualityPhoto(String lowQualityPhoto) {
        this.lowQualityPhoto = lowQualityPhoto;
    }

    public String getMediumQualityPhoto() {
        return mediumQualityPhoto;
    }

    public void setMediumQualityPhoto(String mediumQualityPhoto) {
        this.mediumQualityPhoto = mediumQualityPhoto;
    }
}
