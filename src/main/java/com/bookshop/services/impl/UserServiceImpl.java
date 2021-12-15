package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.User;
import com.bookshop.dto.SignUpDTO;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.UserUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.UserRepository;
import com.bookshop.services.UserService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BasePagination<User, UserRepository> implements UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public void createAdminAccount(User user) {
        userRepository.save(user);
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public User create(SignUpDTO signUpDTO) {
        User user = mapper.map(signUpDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public User update(UserUpdateDTO userUpdateDTO, User currentUser) {
        User updated = mapper.map(userUpdateDTO, User.class);
        mapper.map(updated, currentUser);
        if (userUpdateDTO.getPassword() != null) {
            currentUser.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }
        return userRepository.save(currentUser);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Long countAll() {
        return userRepository.count();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification) {
        return this.paginate(page, perPage, specification);
    }
}
