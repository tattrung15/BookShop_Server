package com.bookshop.services.impl;

import com.bookshop.constants.Common;
import com.bookshop.dao.Delivery;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public Long countAll() {
        return deliveryRepository.count();
    }

    @Override
    public Delivery findById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElse(null);
    }

    @Override
    public Delivery findByIndex(String index) {
        return deliveryRepository.findByIndex(index);
    }

    @Override
    public Delivery findByAddedToCartState() {
        return this.findByIndex(Common.DELIVERY_ADDED_TO_CART_INDEX);
    }

    @Override
    public Delivery findByWaitingToConfirmState() {
        return this.findByIndex(Common.DELIVERY_WAITING_TO_CONFIRM_INDEX);
    }

    @Override
    public Delivery findByCancelState() {
        return this.findByIndex(Common.DELIVERY_CANCELED_INDEX);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Override
    @Transactional
    public void seedData() {
        Delivery delivery1 = new Delivery(null, Common.DELIVERY_ADDED_TO_CART_INDEX, Common.DELIVERY_ADDED_TO_CART_VALUE, null, null, null);
        Delivery delivery2 = new Delivery(null, Common.DELIVERY_WAITING_TO_CONFIRM_INDEX, Common.DELIVERY_WAITING_TO_CONFIRM_VALUE, null, null, null);
        Delivery delivery3 = new Delivery(null, Common.DELIVERY_DELIVERING_INDEX, Common.DELIVERY_DELIVERING_VALUE, null, null, null);
        Delivery delivery4 = new Delivery(null, Common.DELIVERY_DELIVERED_INDEX, Common.DELIVERY_DELIVERED_VALUE, null, null, null);
        Delivery delivery5 = new Delivery(null, Common.DELIVERY_CANCELED_INDEX, Common.DELIVERY_CANCELED_VALUE, null, null, null);
        deliveryRepository.saveAll(Arrays.asList(delivery1, delivery2, delivery3, delivery4, delivery5));
    }
}
