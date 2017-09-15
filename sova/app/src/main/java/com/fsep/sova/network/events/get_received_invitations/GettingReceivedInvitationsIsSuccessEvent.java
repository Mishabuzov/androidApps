package com.fsep.sova.network.events.get_received_invitations;

import com.fsep.sova.models.ReceivedInvitation;

import java.util.List;

public class GettingReceivedInvitationsIsSuccessEvent {
    private List<ReceivedInvitation> mReceivedInvitations;

    public GettingReceivedInvitationsIsSuccessEvent(List<ReceivedInvitation> receivedInvitations) {
        mReceivedInvitations = receivedInvitations;
    }

    public List<ReceivedInvitation> getReceivedInvitations() {
        return mReceivedInvitations;
    }
}
