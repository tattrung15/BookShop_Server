package com.bookshop.dto;

import com.bookshop.constants.Common;
import com.bookshop.constants.ProductRateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRateDTO {
    @NotNull
    @Min(1)
    private Long productId;

    private Long userId;

    @NotNull
    @Min(ProductRateEnum.MIN)
    @Max(ProductRateEnum.MAX)
    private Integer value;

    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String comment;
}
