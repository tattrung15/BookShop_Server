package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.CategoryUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.ConvertString;
import com.bookshop.services.CategoryService;
import com.bookshop.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController extends BaseController<Category> {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getListCategories(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            HttpServletRequest request) {

        GenericSpecification<Category> specification = new GenericSpecification<Category>().getBasicQuery(request);

        PaginateDTO<Category> paginateCategories = categoryService.getList(page, perPage, specification);

        return this.resPagination(paginateCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryByIdOrSlug(@PathVariable("id") Object id) {
        Category category;
        try {
            Long categoryId = Long.parseLong((String) id);
            category = categoryService.findById(categoryId).orElse(null);
        } catch (Exception e) {
            category = categoryService.findBySlug(id.toString());
        }

        if (category == null) {
            throw new NotFoundException("Category not found");
        }
        return this.resSuccess(category);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        Category oldCategory = categoryService.findBySlug(ConvertString.toSlug(categoryDTO.getName()));

        if (oldCategory != null) {
            throw new AppException("Category has already exists");
        }

        Category category = categoryService.create(categoryDTO);
        return this.resSuccess(category);
    }

    @PatchMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> editCategory(@RequestBody @Valid CategoryUpdateDTO categoryUpdateDTO,
                                          @PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findById(categoryId).orElse(null);

        if (category == null) {
            throw new NotFoundException("Category not found");
        }

        Category savedCategory = categoryService.update(categoryUpdateDTO, category);

        return this.resSuccess(savedCategory);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category == null) {
            throw new NotFoundException("Category not found");
        }

        if (!category.getProducts().isEmpty()) {
            throw new AppException("Delete failed");
        }

        categoryService.deleteById(categoryId);

        return this.resSuccess(category);
    }
}
