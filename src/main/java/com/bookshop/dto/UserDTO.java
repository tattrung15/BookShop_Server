package com.bookshop.dto;

import com.bookshop.constants.RoleEnum;
import com.bookshop.validators.IsIn;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,}$", message = "password is invalid")
    private String password;

    private Long amount;

    @IsIn(value = {RoleEnum.ADMIN, RoleEnum.MEMBER}, message = "role is invalid")
    private String role;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;
}
