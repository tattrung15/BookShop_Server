package com.bookshop.services;

import com.bookshop.dao.OrderItem;
import com.bookshop.specifications.GenericSpecification;

public interface OrderItemService {
    OrderItem findOne(GenericSpecification<OrderItem> specification);

    OrderItem createOrUpdate(OrderItem orderItem);
}
