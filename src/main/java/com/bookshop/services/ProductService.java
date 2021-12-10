package com.bookshop.services;

import com.bookshop.dao.Product;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.ProductUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

    Product findBySlug(String slug);

    Product create(ProductDTO productDTO);

    Product update(ProductUpdateDTO productUpdateDTO, Product currentProduct);

    void deleteById(Long productId);

    PaginateDTO<Product> getList(Integer page, Integer perPage, GenericSpecification<Product> specification);
}
