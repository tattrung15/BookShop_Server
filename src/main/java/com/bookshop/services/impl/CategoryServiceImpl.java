package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.constants.Common;
import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.CategoryUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.services.CategoryService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
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
        if (page == null || page <= 0) {
            page = 1;
        }
        if (perPage == null || perPage <= 0) {
            perPage = Common.PAGING_DEFAULT_LIMIT;
        }
        Page<Category> pageData = categoryRepository.findAll(specification, PageRequest.of(page - 1, perPage, specification.getSort()));
        return this.paginate(page, perPage, pageData);
    }
}
