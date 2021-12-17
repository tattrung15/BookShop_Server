package com.bookshop.dto;

import com.bookshop.constants.Common;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String name;

    @Length(max = 100000)
    private String description;
}
