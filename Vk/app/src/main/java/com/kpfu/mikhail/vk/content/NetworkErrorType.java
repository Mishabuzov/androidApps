package com.kpfu.mikhail.vk.content;

import android.support.annotation.StringRes;

import com.kpfu.mikhail.vk.R;

public enum NetworkErrorType {

    HTTP_EXCEPTION(8, R.string.unidentified_problems_message, "HTTP EXCEPTION!\n"),

    LOGIN_EXCEPTION(5, R.string.login_unsuccess_error, "LOGIN EXCEPTION!\n"),

    SERVER_EXCEPTION(10, R.string.unidentified_problems_message, "SERVER EXCEPTION!\n"),

    UNEXPECTED_ERROR(1, R.string.unidentified_problems_message, "UNEXPECTED ERROR!\n");

    private int mExceptionCode;

    @StringRes
    private int mExceptionMessage;

    private String mLogMessage;

    NetworkErrorType(int exceptionCode, int exceptionMessage, String logMessage) {
        mExceptionCode = exceptionCode;
        mExceptionMessage = exceptionMessage;
        mLogMessage = logMessage;
    }

    public int getExceptionCode() {
        return mExceptionCode;
    }

    public int getExceptionMessage() {
        return mExceptionMessage;
    }

    public String getLogMessage() {
        return mLogMessage;
    }

}
