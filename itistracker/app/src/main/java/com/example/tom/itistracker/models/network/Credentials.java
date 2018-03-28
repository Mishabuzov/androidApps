package com.example.tom.itistracker.models.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Tom on 25.03.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials {

    private String login;

    private String password;

    private String type;

    public Credentials(String login, String password, String type) {
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
