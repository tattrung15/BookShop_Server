package com.bookshop.dto;

import com.bookshop.validators.NullOrNotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryUpdateDTO {

    @NullOrNotEmpty(message = "name is invalid")
    private String name;

    @Length(max = 100000)
    private String description;
}
