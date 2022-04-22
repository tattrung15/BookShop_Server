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
public class CategoryDTO {

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String name;

    @Length(max = 100000)
    private String description;

    private Boolean isAuthor;

    private Long parentCategoryId;
}
