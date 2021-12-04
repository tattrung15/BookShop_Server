package com.bookshop.services;

import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.pagination.PaginateDTO;

public interface UserService {
    Long countAll();

    User create(UserDTO userDTO);

    void createAdminAccount(User user);

    PaginateDTO<User> getListUsers(Integer page, Integer perPage);

    PaginateDTO<User> getUsersByUsername(Integer page, Integer perPage, String username);
}
