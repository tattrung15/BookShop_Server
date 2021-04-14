package com.bookshop;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.User;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.repositories.UserRepository;
import com.cloudinary.Cloudinary;

import java.util.Arrays;

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
	private DeliveryRepository deliveryRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BookshopApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary(cloudinaryUrl);
	}

	@Bean
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
		return encodingFilter;
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0) {
			User user = new User(null, firstName, lastName, username, address, passwordEncoder.encode(password), amount,
					role, email, phone, null, null, null);
			userRepository.save(user);
			System.out.println("Created account admin with username: " + user.getUsername());
		}
		if (deliveryRepository.count() == 0) {
			Delivery delivery1 = new Delivery(null, "ChoXacNhan", "Chờ xác nhận", null, null, null);
			Delivery delivery2 = new Delivery(null, "DangGiaoHang", "Đang giao hàng", null, null, null);
			Delivery delivery3 = new Delivery(null, "DaGiao", "Đã giao", null, null, null);
			Delivery delivery4 = new Delivery(null, "DaHuy", "Đã hủy", null, null, null);
			deliveryRepository.saveAll(Arrays.asList(delivery1, delivery2, delivery3, delivery4));
		}
	}
}
