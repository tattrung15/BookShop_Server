package com.bookshop.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationDTO<T> {
    private T data;
    private Pagination pagination;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Pagination {
        private Integer page;
        private Integer perPage;
        private Integer lastPage;
        private Long total;
    }
}