package com.fsep.sova.network.events.get_sent_invitations;

import com.fsep.sova.models.SentInvitation;

import java.util.List;

public class GettingSentInvitationsIsSuccessEvent {
    private List<SentInvitation> mSentInvitations;

    public GettingSentInvitationsIsSuccessEvent(List<SentInvitation> sentInvitations) {
        mSentInvitations = sentInvitations;
    }

    public List<SentInvitation> getSentInvitations() {
        return mSentInvitations;
    }
}
