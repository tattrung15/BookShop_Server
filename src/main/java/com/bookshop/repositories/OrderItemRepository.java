package com.bookshop.repositories;

import com.bookshop.dao.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM order_items WHERE id = ?", nativeQuery = true)
    void deleteById(Long orderItemId);
}
