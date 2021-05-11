package com.bookshop.repositories;

import com.bookshop.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySlug(String slug);

    Page<Product> findByCategoryId(Long category_id, Pageable pageable);

    List<Product> findAllByOrderByQuantityPurchasedDesc(Pageable pageable);

    List<Product> findByTitleContaining(String title);
}
