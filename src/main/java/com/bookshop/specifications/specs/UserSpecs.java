package com.bookshop.specifications.specs;

import com.bookshop.dao.User;
import com.bookshop.specifications.QuerySpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecs extends QuerySpecification<User> {

    public Specification<User> likeUsernameSpec(String username) {
        return super.like(username, "username");
    }

}
