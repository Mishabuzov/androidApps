package com.fsep.sova.network.events.get_posts_created_by_me;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingPostsCreatedByMeErrorEvent extends BaseErrorEvent {
    public GettingPostsCreatedByMeErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingPostsCreatedByMeErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
