package com.bookshop.dto;

import com.bookshop.constants.Common;
import com.bookshop.validators.NullOrNotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryUpdateDTO {

    @NullOrNotEmpty(message = "is invalid")
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String name;

    @Length(max = 100000)
    private String description;
}
