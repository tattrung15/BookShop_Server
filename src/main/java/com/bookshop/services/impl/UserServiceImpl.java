package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.constants.Common;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Long countAll() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public PaginateDTO<User> getList(Integer page, Integer perPage, GenericSpecification<User> specification) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (perPage == null || perPage <= 0) {
            perPage = Common.PAGING_DEFAULT_LIMIT;
        }
        Page<User> pageData = userRepository.findAll(specification, PageRequest.of(page - 1, perPage, Sort.by("createdAt").descending()));
        return this.paginate(page, perPage, pageData);
    }
}
