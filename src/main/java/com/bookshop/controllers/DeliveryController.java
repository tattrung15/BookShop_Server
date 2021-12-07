package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Delivery;
import com.bookshop.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController extends BaseController<Delivery> {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<?> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.findAll();
        return this.resListSuccess(deliveries);
    }
}
