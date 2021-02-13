package com.bookshop.models;

public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private String username;
    private String role;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt, Long userId, String username, String role) {
        this.jwt = jwt;
        this.userId = userId;
        this.username = username;
        this.role = role;
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
