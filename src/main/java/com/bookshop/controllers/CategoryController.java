package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.helpers.ConvertString;
import com.bookshop.services.CategoryService;
import com.bookshop.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        Category oldCategory = categoryService.findBySlug(ConvertString.toSlug(categoryDTO.getName()));

        if (oldCategory != null) {
            throw new AppException("Category has already exists");
        }

        Category category = categoryService.create(categoryDTO);
        return this.resSuccess(category);
    }
}
