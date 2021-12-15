package com.bookshop.repositories;

import com.bookshop.dao.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<ProductImage> {
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM product_images WHERE product_id = ?", nativeQuery = true)
    void deleteByProductId(Long productId);
}
