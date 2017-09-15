package com.fsep.sova.network.events.get_responses;

import com.fsep.sova.models.ResponseOnTask;

import java.util.List;

public class GettingResponsesIsSuccessEvent {
    private List<ResponseOnTask> mResponses;

    public GettingResponsesIsSuccessEvent(List<ResponseOnTask> responses) {
        mResponses = responses;
    }

    public List<ResponseOnTask> getResponses() {
        return mResponses;
    }
}
