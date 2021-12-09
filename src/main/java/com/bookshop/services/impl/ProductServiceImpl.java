package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.Product;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.helpers.ConvertString;
import com.bookshop.repositories.ProductRepository;
import com.bookshop.services.ProductService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl extends BasePagination<Product, ProductRepository> implements ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product findBySlug(String slug) {
        return productRepository.findBySlug(ConvertString.toSlug(slug));
    }

    @Override
    public Product create(ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public PaginateDTO<Product> getList(Integer page, Integer perPage, GenericSpecification<Product> specification) {
        return this.paginate(page, perPage, specification);
    }
}
