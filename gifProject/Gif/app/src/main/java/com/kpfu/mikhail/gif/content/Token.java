package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("access_token")
    private String stringToken;

    @SerializedName("resource_owner")
    private String username;

    public Token(String stringToken) {
        this.stringToken = stringToken;
    }

    public Token(String stringToken, String username) {
        this.stringToken = stringToken;
        this.username = username;
    }

    @NonNull
    public String getStringToken() {
        return stringToken;
    }

    public void setStringToken(String stringToken) {
        this.stringToken = stringToken;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
