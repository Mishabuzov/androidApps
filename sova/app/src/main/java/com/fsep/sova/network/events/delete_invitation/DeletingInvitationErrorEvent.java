package com.fsep.sova.network.events.delete_invitation;

import com.fsep.sova.network.events.BaseErrorEvent;

public class DeletingInvitationErrorEvent extends BaseErrorEvent {
    public DeletingInvitationErrorEvent(int responseCode) {
        super(responseCode);
    }

    public DeletingInvitationErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
