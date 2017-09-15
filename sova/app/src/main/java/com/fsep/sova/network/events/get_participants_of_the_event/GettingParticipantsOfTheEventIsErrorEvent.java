package com.fsep.sova.network.events.get_participants_of_the_event;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingParticipantsOfTheEventIsErrorEvent extends BaseErrorEvent {
    public GettingParticipantsOfTheEventIsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingParticipantsOfTheEventIsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
