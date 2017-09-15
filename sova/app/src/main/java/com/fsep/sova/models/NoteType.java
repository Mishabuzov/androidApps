package com.fsep.sova.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NoteType {
    TASK("task"),
    POST("post"),
    EVENT("event");

    private String mValue;

    NoteType(String value) {
        this.mValue = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return mValue;
    }

    public static NoteType getEnum(String value) {
        for (NoteType v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }
        return null;
    }
}
