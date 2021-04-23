package com.bookshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.SaleOrder;

@Repository
public interface SaleOrderRepository extends JpaRepository<SaleOrder, Long> {
	SaleOrder findByUserIdAndDeliveryId(Long userId, Long deliveryId);

	List<SaleOrder> findByUserIdAndDelivery(Long userId, Delivery delivery);
}
