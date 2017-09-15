package com.fsep.sova.network.events.updatetask;


import com.fsep.sova.network.events.BaseErrorEvent;

public class UpdatingTaskErrorEvent extends BaseErrorEvent {
    public UpdatingTaskErrorEvent(int responseCode, String message) {
        super(responseCode, message);
    }
}
