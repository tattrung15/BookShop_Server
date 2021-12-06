package com.bookshop.dto;

import com.bookshop.constants.RoleEnum;
import com.bookshop.validators.IsIn;
import com.bookshop.validators.NullOrNotEmpty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserUpdateDTO {

    @NullOrNotEmpty(message = "firstName is invalid")
    private String firstName;

    @NullOrNotEmpty(message = "lastName is invalid")
    private String lastName;

    @NullOrNotEmpty(message = "username is invalid")
    private String username;

    @NullOrNotEmpty(message = "address is invalid")
    private String address;

    @NullOrNotEmpty(message = "password is invalid")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,}$", message = "password is invalid")
    private String password;

    private Long amount;

    @IsIn(value = {RoleEnum.ADMIN, RoleEnum.MEMBER}, message = "role is invalid")
    private String role;

    @NullOrNotEmpty(message = "email is invalid")
    private String email;

    @NullOrNotEmpty(message = "phone is invalid")
    private String phone;
}
