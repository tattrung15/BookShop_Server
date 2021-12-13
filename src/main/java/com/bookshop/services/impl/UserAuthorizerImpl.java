package com.bookshop.services.impl;

import com.bookshop.constants.RoleEnum;
import com.bookshop.services.UserAuthorizer;
import com.bookshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service("userAuthorizer")
public class UserAuthorizerImpl implements UserAuthorizer {

    @Autowired
    private UserService userService;

    @Override
    public boolean isAdmin(Authentication authentication) {
        return Arrays.toString(authentication.getAuthorities().toArray()).contains(RoleEnum.ADMIN);
    }

    @Override
    public boolean isMember(Authentication authentication) {
        return Arrays.toString(authentication.getAuthorities().toArray()).contains(RoleEnum.MEMBER);
    }

    @Override
    public boolean isYourself(Authentication authentication, Long userId) {
        User userAuth = (User) authentication.getPrincipal();
        com.bookshop.dao.User user = userService.findByUsername(userAuth.getUsername());
        if (!Objects.equals(user.getId(), userId)) {
            throw new AccessDeniedException("Access denied");
        }
        return true;
    }
}
