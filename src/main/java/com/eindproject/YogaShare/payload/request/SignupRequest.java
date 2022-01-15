package com.eindproject.YogaShare.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class SignupRequest {

    //attributes
    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    @Email
    private String email;

    private Set<String> authority;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getAuthority() {
        return this.authority;
    }

    public void setAuthority(Set<String> authority) {
        this.authority = authority;
    }
}
