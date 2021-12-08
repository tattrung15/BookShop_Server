package com.bookshop.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {

    @NotBlank
    private String name;

    @Length(max = 100000)
    private String description;
}
