package com.bookshop.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationResponseDTO<T> {
    private Integer status;
    private String message;
    private PaginationDTO<T> result;
}
