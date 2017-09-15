package com.fsep.sova.network.events.get_sent_invitations;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingSentInvitationsErrorEvent extends BaseErrorEvent {
    public GettingSentInvitationsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }

    public GettingSentInvitationsErrorEvent(int responseCode) {
        super(responseCode);
    }
}
