package com.bookshop.controllers;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.OrderItem;
import com.bookshop.dao.Product;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dao.User;
import com.bookshop.dto.OrderItemDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.repositories.OrderItemRepository;
import com.bookshop.repositories.ProductRepository;
import com.bookshop.repositories.SaleOrderRepository;
import com.bookshop.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/carts")
@Transactional(rollbackFor = Exception.class)
public class CartController {

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

	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getOrderItemsByUserId(@PathVariable("userId") Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new NotFoundException("Not found user with userId " + userId);
		}

		Delivery delivery = deliveryRepository.findByIndex("DaThemVaoGio");
		User user = optionalUser.get();

		SaleOrder saleOrder = saleOrderRepository.findByUserIdAndDeliveryId(user.getId(), delivery.getId());

		if (saleOrder == null) {
			throw new NotFoundException("Not found cart with userId " + userId);
		}

		return ResponseEntity.status(200).body(saleOrder.getOrderItems());
	}

	@PostMapping
	public ResponseEntity<?> postCart(@RequestBody OrderItemDTO orderItemDTO) {
		Optional<User> optionalUser = userRepository.findById(orderItemDTO.getUserId());
		if (!optionalUser.isPresent()) {
			throw new NotFoundException("Not found user with userId " + orderItemDTO.getUserId());
		}
		Optional<Product> optionalProduct = productRepository.findById(orderItemDTO.getProductId());
		if (!optionalProduct.isPresent()) {
			throw new NotFoundException("Not found product with productId " + orderItemDTO.getProductId());
		}

		Delivery delivery = deliveryRepository.findByIndex("DaThemVaoGio");

		User user = optionalUser.get();
		Product product = optionalProduct.get();

		// tìm sản phẩm đã được thêm vào giỏ hàng
		SaleOrder oldSaleOrder = saleOrderRepository.findByUserIdAndDeliveryId(user.getId(), delivery.getId());
		if (oldSaleOrder != null) {

			// sản phẩm đã có trong giỏ hàng
			OrderItem oldOrderItem = orderItemRepository.findBySaleOrderIdAndProductId(oldSaleOrder.getId(),
					product.getId());
			if (oldOrderItem != null) {
				oldOrderItem.setQuantity(oldOrderItem.getQuantity() + orderItemDTO.getQuantity());
				orderItemRepository.save(oldOrderItem);
			} else {
				OrderItem orderItem = new OrderItem();
				orderItem.setSaleOrder(oldSaleOrder);
				orderItem.setProduct(product);
				orderItem.setQuantity(orderItemDTO.getQuantity());
				orderItemRepository.save(orderItem);
			}

			return ResponseEntity.status(201).body(oldSaleOrder.getOrderItems());
		}

		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setUser(user);
		saleOrder.setDelivery(delivery);
		saleOrder.setCustomerAddress(user.getAddress());
		saleOrder.setPhone(user.getPhone());

		SaleOrder newSaleOrder = saleOrderRepository.save(saleOrder);

		OrderItem orderItem = new OrderItem();
		orderItem.setSaleOrder(newSaleOrder);
		orderItem.setProduct(product);
		orderItem.setQuantity(orderItemDTO.getQuantity());
		OrderItem newOrderItem = orderItemRepository.save(orderItem);

		newSaleOrder.setOrderItems(Arrays.asList(orderItem));
		return ResponseEntity.status(201).body(newSaleOrder.getOrderItems());
	}
}
