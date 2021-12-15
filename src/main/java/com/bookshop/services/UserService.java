package com.bookshop.services;

import com.bookshop.dao.User;
import com.bookshop.dto.SignUpDTO;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.UserUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

public interface UserService {
    Long countAll();

    User findById(Long userId);

    User findByUsername(String username);

    User create(UserDTO userDTO);

    User create(SignUpDTO signUpDTO);

    User update(UserUpdateDTO userUpdateDTO, User currentUser);

    User update(User user);

    void deleteById(Long userId);

    void createAdminAccount(User user);

    PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification);
}
