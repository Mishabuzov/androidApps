package com.fsep.sova.network.events.get_all_images_from_all_conversations;

import com.fsep.sova.models.Photo;

import java.util.List;

public class GettingImagesFromAllConversationsIsSuccessEvent {
    private List<Photo> mImages;

    public GettingImagesFromAllConversationsIsSuccessEvent(List<Photo> images) {
        mImages = images;
    }

    public List<Photo> getImages() {
        return mImages;
    }
}
