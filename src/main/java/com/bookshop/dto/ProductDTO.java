package com.bookshop.dto;

import com.bookshop.constants.Common;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String title;

    @NotBlank
    @Length(max = 100000)
    private String longDescription;

    @NotNull
    @Min(1)
    private Long categoryId;

    @NotNull
    @Min(1)
    private Long price;

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String author;

    @NotNull
    @Min(0)
    private Integer currentNumber;

    @NotNull
    @Min(1)
    private Integer numberOfPage;
}
