package com.bookshop.services;

import com.bookshop.dao.Product;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.ProductUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

public interface ProductService {
    Product findById(Long productId);

    Product findBySlug(String slug);

    Product create(ProductDTO productDTO);

    Product update(ProductUpdateDTO productUpdateDTO, Product currentProduct);

    void updateCurrentNumber(Product product);

    void deleteById(Long productId);

    PaginateDTO<Product> getList(Integer page, Integer perPage, GenericSpecification<Product> specification);
}
