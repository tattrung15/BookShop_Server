package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.constants.Common;
import com.bookshop.dao.Product;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.ProductRepository;
import com.bookshop.services.ProductService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return productRepository.findBySlug(slug);
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
        if (page == null || page <= 0) {
            page = 1;
        }
        if (perPage == null || perPage <= 0) {
            perPage = Common.PAGING_DEFAULT_LIMIT;
        }
        Page<Product> pageData = productRepository.findAll(specification, PageRequest.of(page - 1, perPage, Sort.by("createdAt").descending()));
        return this.paginate(page, perPage, pageData);
    }
}
