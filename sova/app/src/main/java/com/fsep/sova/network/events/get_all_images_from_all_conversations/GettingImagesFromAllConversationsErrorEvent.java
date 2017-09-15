package com.fsep.sova.network.events.get_all_images_from_all_conversations;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingImagesFromAllConversationsErrorEvent extends BaseErrorEvent {
    public GettingImagesFromAllConversationsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingImagesFromAllConversationsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
