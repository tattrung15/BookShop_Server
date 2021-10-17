package com.bookshop.services;

import com.bookshop.base.BaseService;
import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.pagination.PaginateDTO;

public interface UserService extends BaseService<User, UserDTO> {
    User createAdminAccount(User user);

    PaginateDTO<User> getListUsers(Integer page, Integer perPage);
}
