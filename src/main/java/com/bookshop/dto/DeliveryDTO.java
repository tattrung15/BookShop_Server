package com.bookshop.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class DeliveryDTO {
    @NotNull
    @Min(1)
    private Long deliveryId;
}
