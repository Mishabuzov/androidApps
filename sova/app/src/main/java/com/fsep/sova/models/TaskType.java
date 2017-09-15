package com.fsep.sova.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum TaskType {

    PRIVATE("private"),
    PUBLIC("public"),
    //TODO delete this field
    STRING("string");

    private String mValue;

    TaskType(String value) {
        mValue = value;
    }

    public static TaskType getEnum(String value) {
        for (TaskType v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }
        //TODO DEBUG;
        return null;
//        throw new IllegalArgumentException();
    }

    @Override
    @JsonValue
    public String toString() {
        return mValue;
    }

}
