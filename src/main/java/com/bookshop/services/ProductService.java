package com.bookshop.services;

import com.bookshop.dao.Product;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.ProductUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

import java.util.List;

public interface ProductService {
    List<Product> findAll(GenericSpecification<Product> specification);

    Product findById(Long productId);

    List<Product> findByIdsWithOrder(List<Integer> whereIds, String positionIds);

    Product findBySlug(String slug);

    Product create(ProductDTO productDTO);

    Product update(ProductUpdateDTO productUpdateDTO, Product currentProduct);

    void update(Product product);

    void deleteById(Long productId);

    PaginateDTO<Product> getList(Integer page, Integer perPage, GenericSpecification<Product> specification);
}
