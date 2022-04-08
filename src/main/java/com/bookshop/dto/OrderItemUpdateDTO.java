package com.bookshop.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderItemUpdateDTO {
    @NotNull
    @Min(1)
    private Integer quantity;
}
