package com.bookshop.services;

import com.bookshop.dao.SaleOrder;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.specifications.GenericSpecification;

public interface SaleOrderService {
    SaleOrder findOne(GenericSpecification<SaleOrder> specification);

    PaginateDTO<SaleOrder> getList(Integer page, Integer perPage, GenericSpecification<SaleOrder> specification);
}
