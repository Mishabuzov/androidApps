package com.kpfu.mikhail.gif.content;

import android.support.annotation.NonNull;

public class RegistrationForm {

    private String username;

    private String password;

    private String email;

    public RegistrationForm(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
