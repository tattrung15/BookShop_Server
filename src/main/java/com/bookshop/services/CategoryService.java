package com.bookshop.services;

import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

public interface CategoryService {
    Category findBySlug(String slug);

    Category create(CategoryDTO categoryDTO);

    PaginateDTO<Category> getList(Integer page, Integer perPage, GenericSpecification<Category> specification);
}
