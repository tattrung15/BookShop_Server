package com.bookshop.services;

import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

public interface UserService {
    Long countAll();

    User create(UserDTO userDTO);

    void createAdminAccount(User user);

    PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification);
}
