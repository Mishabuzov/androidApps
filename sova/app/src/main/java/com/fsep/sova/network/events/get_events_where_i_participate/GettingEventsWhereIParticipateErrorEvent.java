package com.fsep.sova.network.events.get_events_where_i_participate;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingEventsWhereIParticipateErrorEvent extends BaseErrorEvent {
    public GettingEventsWhereIParticipateErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingEventsWhereIParticipateErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
