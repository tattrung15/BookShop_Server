package com.bookshop.specifications.executors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface JpaSpecificationExecutor<T> {
    Page<T> findAll(Specification<T> spec, Pageable pageable);
}
