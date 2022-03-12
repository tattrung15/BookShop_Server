package com.bookshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BannerStatusDTO {
    @NotNull
    private Boolean isActive;
}
