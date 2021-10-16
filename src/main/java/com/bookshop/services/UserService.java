package com.bookshop.services;

import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;

import java.util.List;

public interface UserService {
    User create(UserDTO userDTO);
    List<User> getListUsers();
}
