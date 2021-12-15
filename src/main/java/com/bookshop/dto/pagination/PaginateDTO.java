package com.bookshop.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import com.bookshop.dto.pagination.PaginationDTO.Pagination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginateDTO<T> {
    private Page<T> pageData;
    private Pagination pagination;
}
