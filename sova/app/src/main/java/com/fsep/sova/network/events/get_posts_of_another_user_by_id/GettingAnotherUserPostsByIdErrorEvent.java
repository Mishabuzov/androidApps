package com.fsep.sova.network.events.get_posts_of_another_user_by_id;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingAnotherUserPostsByIdErrorEvent extends BaseErrorEvent {
    public GettingAnotherUserPostsByIdErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingAnotherUserPostsByIdErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
