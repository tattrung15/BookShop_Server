package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.UserChangePasswordDTO;
import com.bookshop.dto.UserUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.UserService;
import com.bookshop.specifications.GenericSpecification;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
@SecurityRequirement(name = "Authorization")
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
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
        User user = userService.findById(userId);

        return this.resSuccess(user);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication) || @userAuthorizer.isYourself(authentication, #userId)")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO, @PathVariable("userId") Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException("Not found user");
        }

        User savedUser = userService.update(userUpdateDTO, user);

        return this.resSuccess(savedUser);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserChangePasswordDTO userChangePasswordDTO, HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        User user = userService.findByUsername(requestedUser.getUsername());

        if (!passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new AppException("oldPassword is incorrect");
        }

        user.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));

        User updatedUser = userService.update(user);

        return this.resSuccess(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException("Not found user");
        }

        if (!user.getSaleOrders().isEmpty()) {
            throw new AppException("Cannot delete user");
        }

        userService.deleteById(userId);

        return this.resSuccess(user);
    }
}
