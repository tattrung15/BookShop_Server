package com.bookshop;

import com.bookshop.configs.StorageProperties;
import com.bookshop.configs.UserInfoProperties;
import com.bookshop.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, UserInfoProperties.class})
public class BookShopServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShopServerApplication.class, args);
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }



    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args -> {
            storageService.init();
        });
    }
}
