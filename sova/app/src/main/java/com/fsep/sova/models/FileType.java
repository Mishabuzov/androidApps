package com.fsep.sova.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FileType {

    IMAGE("image"),
    COVER("cover"),
    VIDEO("video"),
    FILE("file");

    private String mValue;

    FileType(String value) {
        mValue = value;
    }

    public static FileType getEnum(String value) {
        for (FileType v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }
        //TODO DEBUG;
//        return null;
        throw new IllegalArgumentException();
    }

    @Override
    @JsonValue
    public String toString() {
        return mValue;
    }
}
