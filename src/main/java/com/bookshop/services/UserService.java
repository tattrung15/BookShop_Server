package com.bookshop.services;

import com.bookshop.dao.User;
import com.bookshop.dto.SignUpDTO;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.UserUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

import java.util.Optional;

public interface UserService {
    Long countAll();

    Optional<User> findById(Long id);

    User findByUsername(String username);

    User create(UserDTO userDTO);

    User create(SignUpDTO signUpDTO);

    User update(UserUpdateDTO userUpdateDTO, User currentUser);

    void deleteById(Long userId);

    void createAdminAccount(User user);

    PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification);
}
