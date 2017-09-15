package com.fsep.sova.network.events.get_participants_of_the_event;

import com.fsep.sova.models.UserInfo;

import java.util.List;

public class GettingParticipantsOfTheEventIsSuccessEvent {
    private List<UserInfo> mParticipantList;

    public GettingParticipantsOfTheEventIsSuccessEvent(List<UserInfo> participantList) {
        mParticipantList = participantList;
    }

    public List<UserInfo> getParticipantList() {
        return mParticipantList;
    }
}
