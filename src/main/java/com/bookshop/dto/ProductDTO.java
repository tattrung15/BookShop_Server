package com.bookshop.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {
    @NotBlank
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
    private String author;

    @NotNull
    @Min(0)
    private Integer currentNumber;

    @NotNull
    @Min(1)
    private Integer numberOfPage;
}
