package com.bookshop.dto;

import com.bookshop.constants.Common;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDTO {
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
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,}$", message = "Password is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String password;

    @NotBlank
    @Email
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String email;

    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid phone number")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String phone;
}
