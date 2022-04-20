package com.bookshop;

import com.bookshop.configs.MailInfoProperties;
import com.bookshop.configs.StorageProperties;
import com.bookshop.configs.UserInfoProperties;
import com.bookshop.dao.User;
import com.bookshop.services.DeliveryService;
import com.bookshop.services.StorageService;
import com.bookshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, UserInfoProperties.class, MailInfoProperties.class})
public class BookShopServerApplication {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryService deliveryService;

    public static void main(String[] args) {
        SpringApplication.run(BookShopServerApplication.class, args);
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, UserInfoProperties userInfo) {
        return (args -> {
            storageService.init();

            if (userService.countAll() == 0) {
                User user = mapper.map(userInfo, User.class);
                userService.createAdminAccount(user);
                log.info("admin account has been created");
            }

            if (deliveryService.countAll() == 0) {
                deliveryService.seedData();
            }
        });
    }
}
