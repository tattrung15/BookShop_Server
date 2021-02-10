package com.bookshop;

import com.bookshop.dao.User;
import com.bookshop.repositories.UserRepository;
import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
public class BookshopApplication implements CommandLineRunner {

	@Value("${cloudinary.url}")
	private String cloudinaryUrl;

	@Value("${user.firstName}")
	private String firstName;

	@Value("${user.lastName}")
	private String lastName;

	@Value("${user.username}")
	private String username;

	@Value("${user.password}")
	private String password;

	@Value("${user.address}")
	private String address;

	@Value("${user.amount}")
	private Long amount;

	@Value("${user.role}")
	private String role;

	@Value("${user.email}")
	private String email;

	@Value("${user.phone}")
	private String phone;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BookshopApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinary(){
		return new Cloudinary(cloudinaryUrl);
	}

	@Bean
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
		return encodingFilter;
	}

	@Override
	public void run(String... args) throws Exception {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			user = new User(null, firstName, lastName, username, address, passwordEncoder.encode(password), amount,
					role, email, phone, null, null, null);
			userRepository.save(user);
			System.out.println("Created account admin with username: " + user.getUsername());
		}
	}
}
