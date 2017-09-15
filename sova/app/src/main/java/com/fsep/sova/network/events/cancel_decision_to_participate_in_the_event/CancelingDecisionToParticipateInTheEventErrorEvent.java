package com.fsep.sova.network.events.cancel_decision_to_participate_in_the_event;

import com.fsep.sova.network.events.BaseErrorEvent;

public class CancelingDecisionToParticipateInTheEventErrorEvent extends BaseErrorEvent {
    public CancelingDecisionToParticipateInTheEventErrorEvent(int responseCode) {
        super(responseCode);
    }

    public CancelingDecisionToParticipateInTheEventErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
