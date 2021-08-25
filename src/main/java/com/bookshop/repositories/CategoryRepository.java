package com.bookshop.repositories;

import com.bookshop.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findBySlug(String slug);

    List<Category> findByNameContaining(String categoryName);
}