package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.CategoryUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.services.CategoryService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BasePagination<Category, CategoryRepository> implements CategoryService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category findBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    @Override
    public Category create(CategoryDTO categoryDTO) {
        Category category = mapper.map(categoryDTO, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(CategoryUpdateDTO categoryUpdateDTO, Category currentCategory) {
        Category updated = mapper.map(categoryUpdateDTO, Category.class);
        mapper.map(updated, currentCategory);
        return categoryRepository.save(currentCategory);
    }

    @Override
    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public PaginateDTO<Category> getList(Integer page, Integer perPage, GenericSpecification<Category> specification) {
        return this.paginate(page, perPage, specification);
    }
}
