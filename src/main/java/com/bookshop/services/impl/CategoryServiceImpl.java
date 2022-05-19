package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.CategoryUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.services.CategoryService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Category> findAll(GenericSpecification<Category> specification) {
        return categoryRepository.findAll(specification);
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

        if (categoryDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentCategoryId()).orElse(null);

            if (parentCategory == null) {
                throw new NotFoundException("Not found parent category");
            }

            category.setParentCategory(parentCategory);
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category update(CategoryUpdateDTO categoryUpdateDTO, Category currentCategory) {
        Category updated = mapper.map(categoryUpdateDTO, Category.class);
        mapper.map(updated, currentCategory);
        if (categoryUpdateDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryUpdateDTO.getParentCategoryId()).orElse(null);

            if (parentCategory == null) {
                throw new NotFoundException("Not found parent category");
            }

            currentCategory.setParentCategory(parentCategory);
        } else {
            currentCategory.setParentCategory(null);
        }
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
