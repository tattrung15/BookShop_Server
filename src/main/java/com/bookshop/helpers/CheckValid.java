package com.bookshop.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bookshop.dto.SignUpDTO;
import com.bookshop.dto.UserDTO;
import com.bookshop.exceptions.InvalidException;

public class CheckValid {
	private static final String regexUsername = "^[a-zA-Z0-9]+([._]?[a-zA-Z0-9]+)*$";
	private static final String regexPassword = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,}$";
	private static final String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	private static final String regexPhone = "^[0-9\\-\\+]{9,15}$";

	static Pattern pattern;
	static Matcher matcher;

	public static Boolean isUser(UserDTO userDTO) {
		pattern = Pattern.compile(regexUsername);
		matcher = pattern.matcher(userDTO.getUsername());
		if (!matcher.find()) {
			throw new InvalidException("Invalid username");
		}

		pattern = Pattern.compile(regexPassword);
		matcher = pattern.matcher(userDTO.getPassword());
		if (!matcher.find()) {
			throw new InvalidException("Invalid password");
		}

		pattern = Pattern.compile(regexEmail);
		matcher = pattern.matcher(userDTO.getEmail());
		if (!matcher.find()) {
			throw new InvalidException("Invalid email");
		}

		pattern = Pattern.compile(regexPhone);
		matcher = pattern.matcher(userDTO.getPhone());
		if (!matcher.find()) {
			throw new InvalidException("Invalid phone");
		}

		return true;
	}

	public static Boolean isEmail(String email) {
		pattern = Pattern.compile(regexEmail);
		matcher = pattern.matcher(email);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static Boolean isPhone(String phone) {
		pattern = Pattern.compile(regexPhone);
		matcher = pattern.matcher(phone);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static Boolean checkSignUp(SignUpDTO signUpDTO) {
		if (signUpDTO.getFirstName() == null || signUpDTO.getFirstName().trim() == "") {
			throw new InvalidException("Invalid firstname");
		}
		if (signUpDTO.getLastName() == null || signUpDTO.getLastName().trim() == "") {
			throw new InvalidException("Invalid lastname");
		}
		if (signUpDTO.getAddress() == null || signUpDTO.getAddress().trim() == "") {
			throw new InvalidException("Invalid address");
		}
		pattern = Pattern.compile(regexUsername);
		matcher = pattern.matcher(signUpDTO.getUsername());
		if (!matcher.find()) {
			throw new InvalidException("Invalid username");
		}

		pattern = Pattern.compile(regexPassword);
		matcher = pattern.matcher(signUpDTO.getPassword());
		if (!matcher.find()) {
			throw new InvalidException("Invalid password");
		}

		pattern = Pattern.compile(regexEmail);
		matcher = pattern.matcher(signUpDTO.getEmail());
		if (!matcher.find()) {
			throw new InvalidException("Invalid email");
		}

		pattern = Pattern.compile(regexPhone);
		matcher = pattern.matcher(signUpDTO.getPhone());
		if (!matcher.find()) {
			throw new InvalidException("Invalid phone");
		}
		return true;
	}

	public static Boolean checkUpdateProfile(UserDTO userDTO) {
		if (userDTO.getFirstName() == null || userDTO.getFirstName().trim() == "") {
			throw new InvalidException("Invalid firstname");
		}
		if (userDTO.getLastName() == null || userDTO.getLastName().trim() == "") {
			throw new InvalidException("Invalid lastname");
		}
		if (userDTO.getAddress() == null || userDTO.getAddress().trim() == "") {
			throw new InvalidException("Invalid address");
		}

		pattern = Pattern.compile(regexEmail);
		matcher = pattern.matcher(userDTO.getEmail());
		if (!matcher.find()) {
			throw new InvalidException("Invalid email");
		}

		pattern = Pattern.compile(regexPhone);
		matcher = pattern.matcher(userDTO.getPhone());
		if (!matcher.find()) {
			throw new InvalidException("Invalid phone");
		}
		return true;
	}
}
