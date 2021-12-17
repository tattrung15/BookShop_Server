package com.bookshop.exceptions;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private Integer status;
    private String message;
}
