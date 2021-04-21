package com.bookshop.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.dao.OrderItem;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dto.CartItemDTO;
import com.bookshop.exceptions.InvalidException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.repositories.OrderItemRepository;
import com.bookshop.repositories.ProductRepository;
import com.bookshop.repositories.SaleOrderRepository;
import com.bookshop.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderItemController {

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@PatchMapping("/{orderItemId}")
	public ResponseEntity<?> updateQuantity(@PathVariable("orderItemId") Long orderItemId,
			@RequestBody CartItemDTO cartItemDTO) {
		Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItemId);
		if (!optionalOrderItem.isPresent()) {
			throw new NotFoundException("Not found order item with orderItemId " + orderItemId);
		}
		OrderItem orderItem = optionalOrderItem.get();

		Integer currentNumber = orderItem.getProduct().getCurrentNumber();
		Integer quantity = cartItemDTO.getQuantity();
		if (currentNumber < quantity) {
			throw new InvalidException("Not enough quantity");
		}

		orderItem.setQuantity(quantity);
		orderItemRepository.save(orderItem);

		Optional<SaleOrder> optionalSaleOrder = saleOrderRepository.findById(orderItem.getSaleOrder().getId());
		return ResponseEntity.status(200).body(optionalSaleOrder.get().getOrderItems());
	}

	@DeleteMapping("/{orderItemId}")
	public ResponseEntity<?> deleteOrderItem(@PathVariable("orderItemId") Long orderItemId) {
		Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItemId);
		if (!optionalOrderItem.isPresent()) {
			throw new NotFoundException("Not found order item with orderItemId " + orderItemId);
		}
		OrderItem orderItem = optionalOrderItem.get();

		orderItemRepository.delete(orderItem);

		Optional<SaleOrder> optionalSaleOrder = saleOrderRepository.findById(orderItem.getSaleOrder().getId());
		if (optionalSaleOrder.get().getOrderItems().size() == 0) {
			saleOrderRepository.delete(optionalSaleOrder.get());
			return ResponseEntity.status(200).body(optionalSaleOrder.get().getOrderItems());
		}
		return ResponseEntity.status(200).body(optionalSaleOrder.get().getOrderItems());
	}
}
