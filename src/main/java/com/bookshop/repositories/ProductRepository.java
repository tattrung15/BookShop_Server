package com.bookshop.repositories;

import com.bookshop.dao.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Product findBySlug(String slug);

    @Query(value = "SELECT * " +
            "FROM products " +
            "WHERE id in ?1 " +
            "ORDER BY POSITION(id\\:\\:text in ?2)", nativeQuery = true)
    List<Product> findByIdsWithOrder(List<Integer> whereIds, String positionIds, Pageable pageable);
}
