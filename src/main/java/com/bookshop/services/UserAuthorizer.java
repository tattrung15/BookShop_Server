package com.bookshop.services;

import org.springframework.security.core.Authentication;

public interface UserAuthorizer {
    boolean isAdmin(Authentication authentication);

    boolean isMember(Authentication authentication);

    boolean isYourself(Authentication authentication, Long userId);
}
