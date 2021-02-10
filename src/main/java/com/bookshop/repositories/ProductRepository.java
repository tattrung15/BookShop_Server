package com.bookshop.repositories;

import com.bookshop.dao.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySlug(String slug);
}
