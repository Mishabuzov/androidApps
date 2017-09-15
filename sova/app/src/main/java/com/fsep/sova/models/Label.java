package com.fsep.sova.models;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fsep.sova.R;

public enum Label {

    NEEDS_REVIEW("needs_review", R.string.chat_status_readable_needs_review, R.color.chat_needs_review_status),
    APPROVED("approved", R.string.chat_status_readable_approved, R.color.chat_approved_status),
    IN_PROGRESS("in_progress", R.string.chat_status_readable_approved, R.color.chat_in_progress_status),
    EMPTY("");

    private String mValue;
    private int mHumanReadableValue;
    private int mStatusColor;

    Label(String value) {
        mValue = value;
    }

    Label(String value, @StringRes int humanReadableValue, @ColorRes int statusColor) {
        mValue = value;
        mHumanReadableValue = humanReadableValue;
        mStatusColor = statusColor;
    }

    public int getHumanReadableValue() {
        return mHumanReadableValue;
    }

    public int getStatusColor() {
        return mStatusColor;
    }

    public static Label getEnum(String value) {
        for (Label v : values()) {
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
