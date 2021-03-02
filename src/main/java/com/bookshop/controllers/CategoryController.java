package com.bookshop.controllers;

import java.util.List;
import java.util.Optional;

import com.bookshop.dao.Category;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.exceptions.DuplicateRecordException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.ConvertObject;
import com.bookshop.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/categories")
@Transactional(rollbackFor = Exception.class)
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(name = "page", required = false) Integer pageNum) {
        if (pageNum != null) {
            Page<Category> page = categoryRepository.findAll(PageRequest.of(pageNum.intValue(), 10));
            if (page.getNumberOfElements() == 0) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(page.getContent());
        }
        List<Category> categories = categoryRepository.findAll();
        if (categories.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryBySlug(@PathVariable("id") Object id) {
        Category category;
        try {
            Long categoryId = Long.parseLong((String) id);
            category = categoryRepository.findById(categoryId).get();
        } catch (Exception e) {
            category = categoryRepository.findBySlug(id.toString());
        }

        if (category == null) {
            throw new NotFoundException("Category not found");
        }
        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> createNewCategory(@RequestBody CategoryDTO categoryDTO) {
        Category oldCategory = categoryRepository.findBySlug(ConvertObject.toSlug(categoryDTO.getName()));
        if (oldCategory != null) {
            throw new DuplicateRecordException("Category has already exists");
        }
        Category category = ConvertObject.fromCategoryDTOToCategoryDAO(categoryDTO);

        Category newCategory = categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PatchMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> editUser(@RequestBody CategoryDTO categoryDTO,
            @PathVariable("categoryId") Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (!optionalCategory.isPresent()) {
            throw new NotFoundException("Category not found");
        }
        Category category = optionalCategory.get();

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
            category.setSlug(ConvertObject.toSlug(categoryDTO.getName()));
        }
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }

        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            throw new NotFoundException("Category not found");
        }
        categoryRepository.deleteById(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(optionalCategory.get());
    }
}
