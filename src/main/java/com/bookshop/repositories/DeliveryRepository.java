package com.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookshop.dao.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	Delivery findByIndex(String index);
}
