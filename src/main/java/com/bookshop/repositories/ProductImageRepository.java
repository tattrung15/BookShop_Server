package com.bookshop.repositories;

import java.util.List;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProduct(Product product);
}
