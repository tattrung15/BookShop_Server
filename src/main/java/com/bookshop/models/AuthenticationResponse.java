package com.bookshop.models;

public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private String username;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt, Long userId, String username) {
        this.jwt = jwt;
        this.userId = userId;
        this.username = username;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
