package com.bookshop.services;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    void deleteByProductId(Long productId);

    List<ProductImage> createOrUpdateMany(Product product, MultipartFile[] files);
}
