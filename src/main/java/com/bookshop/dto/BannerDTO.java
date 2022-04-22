package com.bookshop.dto;

import com.bookshop.constants.BannerTypeEnum;
import com.bookshop.constants.Common;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BannerDTO {
    @NotBlank
    @Length(max = Common.STRING_LENGTH_LIMIT)
    private String title;

    @NotNull
    @Range(min = BannerTypeEnum.CATEGORY, max = BannerTypeEnum.PRODUCT)
    private Integer type;
}
