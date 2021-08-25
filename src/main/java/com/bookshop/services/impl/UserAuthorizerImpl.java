package com.bookshop.services.impl;

import com.bookshop.exceptions.ForbiddenException;
import com.bookshop.repositories.UserRepository;
import com.bookshop.services.UserAuthorizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service("userAuthorizer")
public class UserAuthorizerImpl implements UserAuthorizer {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean authorizeAdmin(Authentication authentication, String role) {
		Object[] objectAuthentication = authentication.getAuthorities().toArray();
		if (objectAuthentication[0].toString().compareTo(role) != 0) {
			throw new ForbiddenException("Access denied");
		}
		return true;
	}

	@Override
	public boolean authorizeGetUserById(Authentication authentication, String role, Long userId) {
		Object[] objectAuthentication = authentication.getAuthorities().toArray();
		if (objectAuthentication[0].toString().compareTo(role) == 0) {
			return true;
		}

		User userAuth = (User) authentication.getPrincipal();
		com.bookshop.dao.User user = userRepository.findByUsername(userAuth.getUsername());

		if (user.getId() != userId) {
			throw new ForbiddenException("Access denied");
		}
		return true;
	}

	@Override
	public boolean authorizeUser(Authentication authentication, Long userId) {
		User userAuthentication = (User) authentication.getPrincipal();

		com.bookshop.dao.User user = userRepository.findByUsername(userAuthentication.getUsername());

		if (user.getId() != userId) {
			throw new ForbiddenException("Access denied");
		}
		return true;
	}
}
