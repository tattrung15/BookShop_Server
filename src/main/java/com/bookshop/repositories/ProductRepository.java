package com.bookshop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookshop.dao.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findBySlug(String slug);

	Page<Product> findByCategoryId(Long category_id, Pageable pageable);

	List<Product> findAllByOrderByQuantityPurchasedDesc(Pageable pageable);

	List<Product> findByTitleContaining(String title);
}
