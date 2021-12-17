package com.bookshop.repositories;

import com.bookshop.dao.ProductRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRateRepository extends JpaRepository<ProductRate, Long>, JpaSpecificationExecutor<ProductRate> {
}
