package com.bookshop.services;

import com.bookshop.dao.Delivery;

import java.util.List;

public interface DeliveryService {
    Long countAll();

    List<Delivery> findAll();

    void seedData();
}
