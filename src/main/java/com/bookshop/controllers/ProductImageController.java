package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.FileHelper;
import com.bookshop.services.ProductImageService;
import com.bookshop.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@SecurityRequirement(name = "Authorization")
public class ProductImageController extends BaseController<ProductImage> {

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createOrUpdateProductImages(@RequestParam("productId") Long productId,
                                                         @RequestParam("files") MultipartFile[] files) {

        for (MultipartFile file : files) {
            if (!FileHelper.isAllowImageType(file.getOriginalFilename())) {
                throw new AppException("This file type is not allowed");
            }
        }

        Product product = productService.findById(productId);

        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        List<ProductImage> productImages = productImageService.createOrUpdateMany(product, files);

        return this.resListSuccess(productImages);
    }
}
