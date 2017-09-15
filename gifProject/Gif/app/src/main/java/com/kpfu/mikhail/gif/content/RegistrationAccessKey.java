package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class RegistrationAccessKey {

    @SerializedName("access_key")
    private String mAccessKey;

    public RegistrationAccessKey(String accessKey) {
        mAccessKey = accessKey;
    }

    @NonNull
    public String getAccessKey() {
        return mAccessKey;
    }

    public void setAccessKey(String accessKey) {
        mAccessKey = accessKey;
    }
}
