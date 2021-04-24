package com.bookshop.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.dao.OrderItem;
import com.bookshop.dao.ProductImage;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dto.OrderDetail;
import com.bookshop.dto.OrderItemDetailDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.SaleOrderRepository;

@RestController
@RequestMapping("/api/sale-orders")
public class SaleOrderController {

	@Autowired
	private SaleOrderRepository saleOrderRepository;

	@GetMapping("/{saleOrderId}")
	public ResponseEntity<?> getSaleOrderById(@PathVariable("saleOrderId") Long saleOrderId) {
		Optional<SaleOrder> optionalSaleOrder = saleOrderRepository.findById(saleOrderId);
		if (!optionalSaleOrder.isPresent()) {
			throw new NotFoundException("Not found sale order by id " + saleOrderId);
		}

		SaleOrder saleOrder = optionalSaleOrder.get();

		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(saleOrder.getId());
		orderDetail.setCreateAt(saleOrder.getCreateAt());
		orderDetail.setUpdateAt(saleOrder.getUpdateAt());
		orderDetail.setCustomerAddress(saleOrder.getCustomerAddress());
		orderDetail.setDelivery(saleOrder.getDelivery());
		orderDetail.setUser(saleOrder.getUser());
		orderDetail.setPhone(saleOrder.getPhone());

		List<OrderItemDetailDTO> orderItemDetailDTOs = new LinkedList<>();

		for (int i = 0; i < saleOrder.getOrderItems().size(); i++) {
			OrderItem orderItem = saleOrder.getOrderItems().get(i);
			ProductImage productImage = orderItem.getProduct().getProductImages().get(0);

			OrderItemDetailDTO orderItemDetailDTO = new OrderItemDetailDTO();
			orderItemDetailDTO.setOrderItem(orderItem);
			orderItemDetailDTO.setProductImage(productImage);

			orderItemDetailDTOs.add(orderItemDetailDTO);
		}

		orderDetail.setOrderItems(orderItemDetailDTOs);

		return ResponseEntity.status(200).body(orderDetail);
	}
}
