package com.fsep.sova.network.events.get_received_invitations;

import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingReceivedInvitationsErrorEvent extends BaseErrorEvent {
    public GettingReceivedInvitationsErrorEvent(int responseCode) {
        super(responseCode);
    }

    public GettingReceivedInvitationsErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
