package com.bookshop.services.impl;

import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.repositories.UserRepository;
import com.bookshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    // TODO: pagination
    @Override
    public List<User> getListUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
