package com.bookshop.services;

import com.bookshop.dao.OrderItem;
import com.bookshop.specifications.GenericSpecification;

public interface OrderItemService {
    OrderItem findById(Long orderItemId);

    OrderItem findOne(GenericSpecification<OrderItem> specification);

    OrderItem createOrUpdate(OrderItem orderItem);
}
