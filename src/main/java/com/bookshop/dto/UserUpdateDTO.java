package com.bookshop.dto;

import com.bookshop.constants.Common;
import com.bookshop.constants.RoleEnum;
import com.bookshop.validators.IsIn;
import com.bookshop.validators.NullOrNotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO {

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String firstName;

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String lastName;

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String username;

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String address;

    @NullOrNotEmpty(message = "is invalid")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&_]{8,255}$", message = "is invalid")
    private String password;

    private Long amount;

    @IsIn(value = {RoleEnum.ADMIN, RoleEnum.MEMBER}, message = "is invalid")
    private String role;

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String email;

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String phone;
}
