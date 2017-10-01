package com.kpfu.mikhail.vk.content.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {

    private int count;

    @JsonProperty("user_likes")
    private LikeStatus likeStatus;

    public Like() {
    }

    @JsonDeserialize(using = LikeStatusDeserializer.class)
    public void setLikeStatus(final LikeStatus status) {
        this.likeStatus = status;
    }
    public LikeStatus getLikeStatus() {
        return this.likeStatus;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
