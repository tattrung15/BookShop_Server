package com.bookshop.repositories;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
    SaleOrder findByUserIdAndDeliveryId(Long userId, Long deliveryId);

    List<SaleOrder> findByUserIdAndDelivery(Long userId, Delivery delivery);
}
