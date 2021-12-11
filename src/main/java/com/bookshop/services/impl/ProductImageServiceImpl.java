package com.bookshop.services.impl;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageService productImageService;

    @Override
    public List<ProductImage> createMany(Product product, MultipartFile[] files) {
        return null;
    }
}
