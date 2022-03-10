package com.bookshop.services;

import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.CategoryUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(GenericSpecification<Category> specification);

    Category findById(Long categoryId);

    Category findBySlug(String slug);

    Category create(CategoryDTO categoryDTO);

    Category update(CategoryUpdateDTO categoryUpdateDTO, Category currentCategory);

    void deleteById(Long categoryId);

    PaginateDTO<Category> getList(Integer page, Integer perPage, GenericSpecification<Category> specification);
}
