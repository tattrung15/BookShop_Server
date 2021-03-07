package com.bookshop.repositories;

import com.bookshop.dao.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
