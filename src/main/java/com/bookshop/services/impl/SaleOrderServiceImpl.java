package com.bookshop.services.impl;

import com.bookshop.base.BasePagination;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.repositories.SaleOrderRepository;
import com.bookshop.services.SaleOrderService;
import com.bookshop.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleOrderServiceImpl extends BasePagination<SaleOrder, SaleOrderRepository> implements SaleOrderService {

    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    public SaleOrderServiceImpl(SaleOrderRepository saleOrderRepository) {
        super(saleOrderRepository);
    }

    @Override
    public SaleOrder findOne(GenericSpecification<SaleOrder> specification) {
        return saleOrderRepository.findOne(specification).orElse(null);
    }

    @Override
    public SaleOrder findById(Long saleOrderId) {
        return saleOrderRepository.findById(saleOrderId).orElse(null);
    }

    @Override
    public SaleOrder create(SaleOrder saleOrder) {
        return saleOrderRepository.save(saleOrder);
    }

    @Override
    public SaleOrder update(SaleOrder saleOrder) {
        return saleOrderRepository.save(saleOrder);
    }

    @Override
    public void deleteById(Long saleOrderId) {
        saleOrderRepository.deleteById(saleOrderId);
    }

    @Override
    public PaginateDTO<SaleOrder> getList(Integer page, Integer perPage, GenericSpecification<SaleOrder> specification) {
        return this.paginate(page, perPage, specification);
    }
}
