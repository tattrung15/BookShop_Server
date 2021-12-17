package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductRate;
import com.bookshop.dto.ProductRateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.ProductRateRepository;
import com.bookshop.repositories.ProductRepository;
import com.bookshop.services.ProductRateService;
import com.bookshop.specifications.GenericSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRateServiceImpl extends BasePagination<ProductRate, ProductRateRepository> implements ProductRateService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRateRepository productRateRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductRateServiceImpl(ProductRateRepository productRateRepository) {
        super(productRateRepository);
    }

    @Override
    public ProductRate findOne(GenericSpecification<ProductRate> specification) {
        return productRateRepository.findOne(specification).orElse(null);
    }

    @Override
    public ProductRate create(ProductRateDTO productRateDTO) {
        Product product = productRepository.findById(productRateDTO.getProductId()).orElse(null);
        ProductRate productRate = mapper.map(productRateDTO, ProductRate.class);
        productRate.setProduct(product);
        return productRateRepository.save(productRate);
    }

    @Override
    public PaginateDTO<ProductRate> getList(Integer page, Integer perPage, GenericSpecification<ProductRate> specification) {
        return this.paginate(page, perPage, specification);
    }
}
