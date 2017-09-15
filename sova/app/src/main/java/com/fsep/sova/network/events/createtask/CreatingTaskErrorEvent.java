package com.fsep.sova.network.events.createtask;


import com.fsep.sova.network.events.BaseErrorEvent;

public class CreatingTaskErrorEvent extends BaseErrorEvent {
    public CreatingTaskErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
