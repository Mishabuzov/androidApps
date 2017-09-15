package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginForm {

    @SerializedName("grant_type")
    private String grantType;

    private String username;

    private String password;

    public LoginForm(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
