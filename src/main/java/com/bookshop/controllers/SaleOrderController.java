package com.bookshop.controllers;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.OrderItem;
import com.bookshop.dao.ProductImage;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dto.DeliveryDTO;
import com.bookshop.dto.OrderDetail;
import com.bookshop.dto.OrderItemDetailDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.repositories.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sale-orders")
public class SaleOrderController {

    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping
    public ResponseEntity<?> getAllSaleOrders(@RequestParam(name = "search", required = false) Long saleOrderId) {
        if (saleOrderId != null) {
            Optional<SaleOrder> saleOrders = saleOrderRepository.findById(saleOrderId);
            if (!saleOrders.isPresent()) {
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(200).body(Collections.singletonList(saleOrders.get()));
        }
        List<SaleOrder> saleOrders = saleOrderRepository.findAll();
        if (saleOrders.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(saleOrders);
    }

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

    @PatchMapping("/{saleOrderId}")
    public ResponseEntity<?> editSaleOrderDelivery(@PathVariable("saleOrderId") Long saleOrderId, @RequestBody DeliveryDTO deliveryDTO) {

        Optional<SaleOrder> optionalSaleOrder = saleOrderRepository.findById(saleOrderId);
        if (!optionalSaleOrder.isPresent()) {
            throw new NotFoundException("Not found sale order by saleOrderId " + saleOrderId);
        }

        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryDTO.getDeliveryId());
        if (!optionalDelivery.isPresent()) {
            throw new NotFoundException("Not found delivery by deliveryId " + deliveryDTO.getDeliveryId());
        }

        SaleOrder saleOrder = optionalSaleOrder.get();
        saleOrder.setDelivery(optionalDelivery.get());

        SaleOrder newSaleOrder = saleOrderRepository.save(saleOrder);

        return ResponseEntity.status(200).body(newSaleOrder);
    }
}
