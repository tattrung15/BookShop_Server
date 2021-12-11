package com.bookshop.services;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> createMany(Product product, MultipartFile[] files);
}
