package com.fsep.sova.network.events.getowntasks;


import com.fsep.sova.network.events.BaseErrorEvent;

public class GettingOwnTasksErrorEvent extends BaseErrorEvent {
    public GettingOwnTasksErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
