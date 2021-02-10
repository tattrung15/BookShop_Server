package com.bookshop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.repositories.ProductImageRepository;
import com.bookshop.repositories.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<?> getAllProductImages(@RequestParam(name = "page", required = false) Integer pageNum) {
        if (pageNum != null) {
            Page<ProductImage> page = productImageRepository.findAll(PageRequest.of(pageNum.intValue(), 10));
            if (page.getNumberOfElements() == 0) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(page.getContent());
        }
        List<ProductImage> productImages = productImageRepository.findAll();
        if (productImages.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(productImages);
    }

    @PostMapping
    public ResponseEntity<?> createNewImages(@RequestParam("productId") Long productId,
            @RequestParam("files") MultipartFile[] files) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();
        Product product = productRepository.findById(productId).get();
        for (int i = 0; i < files.length; i++) {
            Map<?, ?> cloudinaryMap = cloudinary.uploader().upload(files[i].getBytes(), ObjectUtils.emptyMap());
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setLink(cloudinaryMap.get("secure_url").toString());
            productImage.setPublicId(cloudinaryMap.get("public_id").toString());
            ProductImage newProductImage = productImageRepository.save(productImage);
            productImages.add(newProductImage);
        }
        return ResponseEntity.status(201).body(productImages);
    }
}
