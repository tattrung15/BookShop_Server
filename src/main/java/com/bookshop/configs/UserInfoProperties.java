package com.bookshop.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("user")
@Getter
@Setter
public class UserInfoProperties {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String address;
    private Long amount;
    private String role;
    private String email;
    private String phone;
}
