package com.bookshop.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.OrderItem;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dao.User;
import com.bookshop.dto.CartItemDTO;
import com.bookshop.dto.SaleOrderDTO;
import com.bookshop.dto.SaleOrderResponseDTO;
import com.bookshop.exceptions.InvalidException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.repositories.OrderItemRepository;
import com.bookshop.repositories.ProductRepository;
import com.bookshop.repositories.SaleOrderRepository;
import com.bookshop.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DeliveryRepository deliveryRepository;

	@GetMapping("/{userId}/{slugDelivery}")
	public ResponseEntity<?> getSaleOrderByUserIdAndDelivery(@PathVariable("userId") Long userId,
			@PathVariable("slugDelivery") String slugDelivery) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new NotFoundException("Not found user with userId " + userId);
		}

		Delivery delivery = deliveryRepository.findByIndex(slugDelivery);
		if (delivery == null) {
			throw new NotFoundException("Not found delivery with slug delivery " + slugDelivery);
		}

		List<SaleOrder> saleOrders = saleOrderRepository.findByUserIdAndDelivery(userId, delivery);
		if (saleOrders.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		List<SaleOrderResponseDTO> saleOrderResponseDTOs = new LinkedList<>();
		for (int i = 0; i < saleOrders.size(); i++) {
			SaleOrderResponseDTO saleOrderResponseDTO = new SaleOrderResponseDTO();
			saleOrderResponseDTO.setId(saleOrders.get(i).getId());
			saleOrderResponseDTO.setCreateAt(saleOrders.get(i).getCreateAt());
			saleOrderResponseDTO.setUpdateAt(saleOrders.get(i).getUpdateAt());
			saleOrderResponseDTO.setCustomerAddress(saleOrders.get(i).getCustomerAddress());
			saleOrderResponseDTO.setDelivery(saleOrders.get(i).getDelivery());
			saleOrderResponseDTO.setPhone(saleOrders.get(i).getPhone());
			saleOrderResponseDTO.setOrderItems(saleOrders.get(i).getOrderItems());
			saleOrderResponseDTO.setUser(saleOrders.get(i).getUser());
			saleOrderResponseDTO
					.setProductImage(saleOrders.get(i).getOrderItems().get(0).getProduct().getProductImages().get(0));
			saleOrderResponseDTOs.add(saleOrderResponseDTO);
		}
		return ResponseEntity.status(200).body(saleOrderResponseDTOs);
	}

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

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> postOrder(@RequestBody SaleOrderDTO saleOrderDTO) {
		Optional<User> optionalUser = userRepository.findById(saleOrderDTO.getUserId());
		if (!optionalUser.isPresent()) {
			throw new NotFoundException("Not found user with userId " + saleOrderDTO.getUserId());
		}

		User user = optionalUser.get();

		if (user.getAmount() < saleOrderDTO.getTotalAmount()) {
			throw new InvalidException("Not enough money");
		}

		Delivery delivery = deliveryRepository.findByIndex("DaThemVaoGio");

		SaleOrder saleOrder = saleOrderRepository.findByUserIdAndDeliveryId(user.getId(), delivery.getId());

		if (saleOrder == null) {
			throw new NotFoundException("Not found sale order by saleOrderId " + saleOrderDTO.getSaleOrderId());
		}

		user.setAmount(user.getAmount() - saleOrderDTO.getTotalAmount());

		Delivery deliveryWaitConfirm = deliveryRepository.findByIndex("ChoXacNhan");

		saleOrder.setDelivery(deliveryWaitConfirm);
		SaleOrder newSaleOrder = saleOrderRepository.save(saleOrder);

		return ResponseEntity.status(200).body(newSaleOrder);
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
