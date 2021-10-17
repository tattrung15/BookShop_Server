package com.bookshop.specifications.impl;

import com.bookshop.dao.User;
import com.bookshop.specifications.QuerySpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UsersSpecs implements QuerySpecification<User> {

    @Override
    public Specification<User> like(String value, String fieldName) {
        return QuerySpecification.super.like(value, fieldName);
    }

    public Specification<User> likeUsernameSpec(String username) {
        return like(username, "username");
    }

}
