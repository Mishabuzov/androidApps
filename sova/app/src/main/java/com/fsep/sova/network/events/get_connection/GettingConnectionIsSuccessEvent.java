package com.fsep.sova.network.events.get_connection;

public class GettingConnectionIsSuccessEvent {
    private String location;

    public GettingConnectionIsSuccessEvent(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
