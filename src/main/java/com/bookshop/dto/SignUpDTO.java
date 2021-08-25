package com.bookshop.dto;

public class SignUpDTO {

	private String firstName;

	private String lastName;

	private String username;

	private String address;

	private String password;

	private String email;

	private String phone;

	public SignUpDTO() {
	}

	public SignUpDTO(String firstName, String lastName, String username, String address, String password, String email,
			String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.address = address;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
