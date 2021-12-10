package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.UserUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.UserService;
import com.bookshop.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users")
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> getListUsers(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            HttpServletRequest request) {

        GenericSpecification<User> specification = new GenericSpecification<User>().getBasicQuery(request);

        PaginateDTO<User> paginateUsers = userService.getList(page, perPage, specification);

        return this.resPagination(paginateUsers);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        User oldUser = userService.findByUsername(userDTO.getUsername());

        if (oldUser != null) {
            throw new AppException("User has already exists");
        }

        User user = userService.create(userDTO);
        return this.resSuccess(user);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication) || @userAuthorizer.isYourself(authentication, #userId)")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Not found user");
        }
        return this.resSuccess(user.get());
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication) || @userAuthorizer.isYourself(authentication, #userId)")
    public ResponseEntity<?> editUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO, @PathVariable("userId") Long userId) {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Not found user");
        }

        User savedUser = userService.update(userUpdateDTO, optionalUser.get());

        return this.resSuccess(savedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Not found user");
        }

        if (!optionalUser.get().getSaleOrders().isEmpty()) {
            throw new AppException("Delete failed");
        }

        userService.deleteById(userId);

        return this.resSuccess(optionalUser.get());
    }
}
