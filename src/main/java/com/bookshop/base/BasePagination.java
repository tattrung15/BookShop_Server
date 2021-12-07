package com.bookshop.base;

import com.bookshop.constants.Common;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.dto.pagination.PaginationDTO.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BasePagination<E, R extends JpaRepository<E, ?>> {

    private R repository;

    public BasePagination() {
    }

    public BasePagination(R repository) {
        this.repository = repository;
    }

    public PaginateDTO<E> paginate(Integer page, Integer perPage) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (perPage == null || perPage <= 0) {
            perPage = Common.PAGING_DEFAULT_LIMIT;
        }
        Page<E> pageData = repository.findAll(PageRequest.of(page, perPage, Sort.by("createdAt").descending()));

        Pagination pagination = new Pagination(page, perPage, pageData.getTotalPages(), pageData.getTotalElements());
        return new PaginateDTO<>(pageData, pagination);
    }

    public PaginateDTO<E> paginate(Integer page, Integer perPage, Page<E> pageData) {
        Pagination pagination = new Pagination(page, perPage, pageData.getTotalPages(), pageData.getTotalElements());
        return new PaginateDTO<>(pageData, pagination);
    }
}
