package com.bookshop.services;

import com.bookshop.dao.ProductRate;
import com.bookshop.dto.ProductRateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

public interface ProductRateService {
    ProductRate findOne(GenericSpecification<ProductRate> specification);

    ProductRate create(ProductRateDTO productRateDTO);

    PaginateDTO<ProductRate> getList(Integer page, Integer perPage, GenericSpecification<ProductRate> specification);
}
