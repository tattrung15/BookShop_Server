package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.constants.Common;
import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.CategoryUpdateDTO;
import com.bookshop.dto.GetCategoryDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.StringHelper;
import com.bookshop.services.CategoryService;
import com.bookshop.services.ProductService;
import com.bookshop.specifications.GenericSpecification;
import com.bookshop.specifications.SearchCriteria;
import com.bookshop.specifications.SearchOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController extends BaseController<Category> {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getListCategories(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            @RequestParam(name = "fetchType", required = false) Integer fetchType,
            HttpServletRequest request) {

        GenericSpecification<Category> specification = new GenericSpecification<Category>().getBasicQuery(request);

        if (fetchType != null) {
            if (fetchType.equals(Common.FETCH_TYPE_ADMIN)) {
                PaginateDTO<Category> paginateCategories = categoryService.getList(page, perPage, specification);
                return this.resPagination(paginateCategories);
            } else if (fetchType.equals(Common.FETCH_TYPE_USER)) {
                specification.add(new SearchCriteria("parentCategory", null, SearchOperation.NULL));
                List<Category> categories = categoryService.findAll(specification);
                List<GetCategoryDTO> getCategoryDTOs = new ArrayList<>();
                for (Category category : categories) {
                    GetCategoryDTO getCategoryDTO = new GetCategoryDTO();
                    getCategoryDTO.setId(category.getId());
                    getCategoryDTO.setName(category.getName());
                    getCategoryDTO.setDescription(category.getDescription());
                    getCategoryDTO.setSlug(category.getSlug());
                    getCategoryDTO.setIsAuthor(category.getIsAuthor());
                    getCategoryDTO.setLinkedCategories(category.getLinkedCategories());
                    getCategoryDTO.setCreatedAt(category.getCreatedAt());
                    getCategoryDTO.setUpdatedAt(category.getUpdatedAt());
                    getCategoryDTOs.add(getCategoryDTO);
                }
                return this.resListSuccess(getCategoryDTOs);
            } else if (fetchType.equals(Common.FETCH_TYPE_ALL)) {
                List<Category> categories = categoryService.findAll(specification);
                return this.resListSuccess(categories);
            }
        }

        PaginateDTO<Category> paginateCategories = categoryService.getList(page, perPage, specification);

        return this.resPagination(paginateCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryByIdOrSlug(@PathVariable("id") Object id) {
        Category category;
        try {
            Long categoryId = Long.parseLong((String) id);
            category = categoryService.findById(categoryId);
        } catch (Exception e) {
            category = categoryService.findBySlug(id.toString());
        }

        return this.resSuccess(category);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        Category oldCategory = categoryService.findBySlug(StringHelper.toSlug(categoryDTO.getName()));

        if (oldCategory != null) {
            throw new AppException("Category has already exists");
        }

        Category category = categoryService.create(categoryDTO);
        return this.resSuccess(category);
    }

    @PatchMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryUpdateDTO categoryUpdateDTO,
                                            @PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findById(categoryId);

        if (category == null) {
            throw new NotFoundException("Not found category");
        }

        Category savedCategory = categoryService.update(categoryUpdateDTO, category);

        return this.resSuccess(savedCategory);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category == null) {
            throw new NotFoundException("Not found category");
        }

        if (category.getLinkedCategories().size() != 0) {
            throw new AppException("Cannot delete category");
        }

        if (!category.getProducts().isEmpty()) {
            throw new AppException("Cannot delete category");
        }

        categoryService.deleteById(categoryId);

        return this.resSuccess(category);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable("id") Object id,
                                                   @RequestParam(name = "page", required = false) Integer page,
                                                   @RequestParam(name = "perPage", required = false) Integer perPage,
                                                   HttpServletRequest request) {
        Category category;
        try {
            Long categoryId = Long.parseLong((String) id);
            category = categoryService.findById(categoryId);
        } catch (Exception e) {
            category = categoryService.findBySlug(id.toString());
        }

        if (category == null) {
            throw new NotFoundException("Not found category");
        }

        GenericSpecification<Product> specification = new GenericSpecification<Product>().getBasicQuery(request);
        specification.add(new SearchCriteria("category", category.getId(), SearchOperation.EQUAL));

        PaginateDTO<Product> paginateProducts = productService.getList(page, perPage, specification);

        return this.resPagination(paginateProducts);
    }
}
