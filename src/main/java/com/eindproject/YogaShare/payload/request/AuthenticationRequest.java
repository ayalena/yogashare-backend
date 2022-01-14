package com.eindproject.YogaShare.payload.request;

import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {

    //attributes
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    //getters&setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
