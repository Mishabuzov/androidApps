package com.kpfu.mikhail.vk.content.likes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum LikeStatus {

    LIKED(1, true),
    NOT_LIKED(0, false);

    private int likeStatus;

    private boolean isLiked;

    LikeStatus(int likeStatus, boolean isLiked) {
        this.likeStatus = likeStatus;
        this.isLiked = isLiked;
    }

    @JsonValue
    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public static LikeStatus fromTypeCode(final int typeCode) {
        switch (typeCode) {
            case 0:
                return NOT_LIKED;
            case 1:
                return LIKED;
        }
        throw new IllegalArgumentException("Invalid Status type code: " + typeCode);
    }
}

class LikeStatusDeserializer extends JsonDeserializer<LikeStatus> {
    @Override
    public LikeStatus deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        return LikeStatus.fromTypeCode(parser.getValueAsInt());
    }


}
