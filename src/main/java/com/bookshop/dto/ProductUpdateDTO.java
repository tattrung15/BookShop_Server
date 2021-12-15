package com.bookshop.dto;

import com.bookshop.validators.NullOrNotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

@Data
public class ProductUpdateDTO {

    @NullOrNotEmpty(message = "title is invalid")
    private String title;

    @NullOrNotEmpty(message = "longDescription is invalid")
    @Length(max = 100000)
    private String longDescription;

    @NullOrNotEmpty(message = "categoryId is invalid")
    @Min(1)
    private Long categoryId;

    @NullOrNotEmpty(message = "price is invalid")
    @Min(1)
    private Long price;

    @NullOrNotEmpty(message = "author is invalid")
    private String author;

    @NullOrNotEmpty(message = "currentNumber is invalid")
    @Min(0)
    private Integer currentNumber;

    @NullOrNotEmpty(message = "numberOfPage is invalid")
    @Min(1)
    private Integer numberOfPage;
}
