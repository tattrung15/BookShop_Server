package com.bookshop.repositories;

import com.bookshop.dao.Product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findBySlug(String slug);

	List<Product> findAllByOrderByQuantityPurchasedDesc(Pageable pageable);
}
