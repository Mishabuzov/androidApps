package com.fsep.sova.network.events;

public class BaseErrorEvent {
    private int responseCode;
    private String message;

    public BaseErrorEvent(int responseCode) {
        this.responseCode = responseCode;
    }

    public BaseErrorEvent(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
