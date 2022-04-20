package com.bookshop.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("mail")
@Getter
@Setter
public class MailInfoProperties {
    private String username;
    private String password;
}
