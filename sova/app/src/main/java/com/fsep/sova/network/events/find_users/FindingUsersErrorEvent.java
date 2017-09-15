package com.fsep.sova.network.events.find_users;

import com.fsep.sova.network.events.BaseErrorEvent;

public class FindingUsersErrorEvent extends BaseErrorEvent {
    public FindingUsersErrorEvent(int responseCode) {
        super(responseCode);
    }

    public FindingUsersErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
