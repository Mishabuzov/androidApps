package com.fsep.sova.network.events.get_all_videos_from_all_converstions;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingVideosFromAllConversationsErrorEvent extends BaseErrorEvent {
    public GettingVideosFromAllConversationsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingVideosFromAllConversationsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
