package com.fsep.sova.network.events.add_response;

import com.fsep.sova.models.ResponseOnTask;

public class AddingResponseIsSuccessEvent {
    private ResponseOnTask mResponseOnTask;

    public AddingResponseIsSuccessEvent(ResponseOnTask responseOnTask) {
        mResponseOnTask = responseOnTask;
    }

    public ResponseOnTask getResponseOnTask() {
        return mResponseOnTask;
    }
}
