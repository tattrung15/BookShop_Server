package com.bookshop.services;

import org.springframework.security.core.Authentication;

public interface UserAuthorizer {
    boolean authorizeAdmin(Authentication authentication, String role);

    boolean authorizeGetUserById(Authentication authentication, String role, Long userId);
}
