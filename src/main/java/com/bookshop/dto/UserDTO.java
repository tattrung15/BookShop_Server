package com.bookshop.dto;

import com.bookshop.constants.Common;
import com.bookshop.constants.RoleEnum;
import com.bookshop.validators.IsIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String firstName;

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String lastName;

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String username;

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String address;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,255}$", message = "is invalid")
    private String password;

    private Long amount;

    @IsIn(value = {RoleEnum.ADMIN, RoleEnum.MEMBER}, message = "is invalid")
    private String role;

    @NotBlank
    @Email
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String email;

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String phone;
}
