package com.fsep.sova.network.events.take_participation_in_the_event;

import com.fsep.sova.network.events.BaseErrorEvent;

public class TakingParticipationInTheEventErrorEvent extends BaseErrorEvent {
    public TakingParticipationInTheEventErrorEvent(int responseCode) {
        super(responseCode);
    }

    public TakingParticipationInTheEventErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
