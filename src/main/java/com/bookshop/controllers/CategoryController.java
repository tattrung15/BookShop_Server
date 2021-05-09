package com.bookshop.controllers;

import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.dto.CategoryDTO;
import com.bookshop.dto.PaginationDTO;
import com.bookshop.exceptions.DuplicateRecordException;
import com.bookshop.exceptions.InvalidException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.ConvertObject;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/categories")
@Transactional(rollbackFor = Exception.class)
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam(name = "page", required = false) Integer pageNum,
                                              @RequestParam(name = "search", required = false) String search) {
        if (search != null) {
            List<Category> categories = categoryRepository.findByNameContaining(search);
            if (categories.size() == 0) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(categories);
        }
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

    @GetMapping("/{slug}/products")
    public ResponseEntity<?> getProductsBySlugOfCategory(@PathVariable("slug") String slug,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNum) {
        if (slug.compareTo("sach-moi") == 0) {
            Page<Product> pageProducts = productRepository
                    .findAll(PageRequest.of(pageNum, 20, Sort.by("id").descending()));
            List<Product> products = pageProducts.getContent();
            List<ProductImage> productImages = new LinkedList<>();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductImages().isEmpty()) {
                    continue;
                }
                ProductImage productImage = new ProductImage();
                productImage.setId(products.get(i).getProductImages().get(0).getId());
                productImage.setLink(products.get(i).getProductImages().get(0).getLink());
                productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
                productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
                productImage.setProduct(products.get(i));
                productImages.add(productImage);
            }
            PaginationDTO paginationDTO = new PaginationDTO();
            paginationDTO.setData(productImages);
            paginationDTO.setTotalOfPage(pageProducts.getTotalPages());
            return ResponseEntity.ok().body(paginationDTO);
        }
        Category category = categoryRepository.findBySlug(slug);
        if (category == null) {
            throw new NotFoundException("Not found category by slug: " + slug);
        }
        Page<Product> pageProducts = productRepository.findByCategoryId(category.getId(), PageRequest.of(pageNum, 20));
        List<Product> products = pageProducts.getContent();
        List<ProductImage> productImages = new LinkedList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductImages().isEmpty()) {
                continue;
            }
            ProductImage productImage = new ProductImage();
            productImage.setId(products.get(i).getProductImages().get(0).getId());
            productImage.setLink(products.get(i).getProductImages().get(0).getLink());
            productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
            productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
            productImage.setProduct(products.get(i));
            productImages.add(productImage);
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(productImages);
        paginationDTO.setTotalOfPage(pageProducts.getTotalPages());
        return ResponseEntity.ok().body(paginationDTO);
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

        if (!optionalCategory.get().getProducts().isEmpty()) {
            throw new InvalidException("Delete failed");
        }

        categoryRepository.deleteById(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(optionalCategory.get());
    }
}
