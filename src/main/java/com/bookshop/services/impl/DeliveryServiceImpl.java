package com.bookshop.services.impl;

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
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Override
    @Transactional
    public void seedData() {
        Delivery delivery1 = new Delivery(null, "DaThemVaoGio", "Đã thêm vào giỏ", null, null, null);
        Delivery delivery2 = new Delivery(null, "ChoXacNhan", "Chờ xác nhận", null, null, null);
        Delivery delivery3 = new Delivery(null, "DangGiaoHang", "Đang giao hàng", null, null, null);
        Delivery delivery4 = new Delivery(null, "DaGiao", "Đã giao", null, null, null);
        Delivery delivery5 = new Delivery(null, "DaHuy", "Đã hủy", null, null, null);
        deliveryRepository.saveAll(Arrays.asList(delivery1, delivery2, delivery3, delivery4, delivery5));
    }
}
