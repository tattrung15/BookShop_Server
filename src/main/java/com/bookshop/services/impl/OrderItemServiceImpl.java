package com.bookshop.services.impl;

import com.bookshop.dao.OrderItem;
import com.bookshop.repositories.OrderItemRepository;
import com.bookshop.services.OrderItemService;
import com.bookshop.specifications.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem findOne(GenericSpecification<OrderItem> specification) {
        return orderItemRepository.findOne(specification).orElse(null);
    }
}
