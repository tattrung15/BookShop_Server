package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Product;
import com.bookshop.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController<Product> {

    @PostMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> createNewProduct(@RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok().body(productDTO);
    }
}
