package com.bookshop.dto;

import com.bookshop.constants.Common;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResetPasswordDTO {

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String username;
}
