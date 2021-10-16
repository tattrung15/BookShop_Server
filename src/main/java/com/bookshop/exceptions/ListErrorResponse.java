package com.bookshop.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListErrorResponse {
    private Integer status;
    private String message;
    private List<ErrorDetails> errors;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ErrorDetails {
        private String fieldName;
        private String message;
    }
}
