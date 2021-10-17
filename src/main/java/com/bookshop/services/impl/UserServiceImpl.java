package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.UserRepository;
import com.bookshop.services.UserService;
import com.bookshop.specifications.impl.UsersSpecs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BasePagination<User, UserRepository> implements UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UsersSpecs usersSpecs;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public User createAdminAccount(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(UserDTO userDTO) {
        User user = mapper.map(userDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public List<User> createMany(Iterable<User> entities) {
        return userRepository.saveAll(entities);
    }

    @Override
    public Long countAll() {
        return userRepository.count();
    }

    @Override
    public PaginateDTO<User> getListUsers(Integer page, Integer perPage) {
        return this.paginate(page, perPage);
    }

    @Override
    public PaginateDTO<User> getUsersByUsername(Integer page, Integer perPage, String username) {
        Page<User> pageData = userRepository.findAll(usersSpecs.likeUsernameSpec(username), PageRequest.of(page, perPage));
        return this.paginate(page, perPage, pageData);
    }
}
