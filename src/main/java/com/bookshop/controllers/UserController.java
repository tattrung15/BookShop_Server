package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.User;
import com.bookshop.dto.UserDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getListUsers(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            @RequestParam(name = "usernameLike", required = false) String usernameLike) {

        PaginateDTO<User> paginateUsers;

        if (usernameLike != null) {
            paginateUsers = userService.getUsersByUsername(page, perPage, usernameLike);
        } else {
            paginateUsers = userService.getListUsers(page, perPage);
        }

        return this.resPagination(paginateUsers);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.create(userDTO);
        return this.resSuccess(user);
    }
}
