package com.fsep.sova.network.events.get_post_by_id;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingPostByIdErrorEvent extends BaseErrorEvent {
    public GettingPostByIdErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingPostByIdErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
