package com.bookshop.controllers;

import com.bookshop.dao.*;
import com.bookshop.dto.CartItemDTO;
import com.bookshop.dto.SaleOrderDTO;
import com.bookshop.dto.SaleOrderResponseDTO;
import com.bookshop.exceptions.InvalidException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.DeliveryRepository;
import com.bookshop.repositories.OrderItemRepository;
import com.bookshop.repositories.SaleOrderRepository;
import com.bookshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

        List<SaleOrderResponseDTO> saleOrdersResponseDTO = new LinkedList<>();
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
            saleOrdersResponseDTO.add(saleOrderResponseDTO);
        }
        return ResponseEntity.status(200).body(saleOrdersResponseDTO);
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

        List<OrderItem> orderItems = saleOrder.getOrderItems();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            Integer currentQuantity = orderItem.getQuantity();
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.getCurrentNumber() < currentQuantity) {
                throw new InvalidException("Not enough quantity");
            }
            currentProduct.setQuantityPurchased(currentProduct.getQuantityPurchased() + currentQuantity);
            currentProduct.setCurrentNumber(currentProduct.getCurrentNumber() - currentQuantity);
        }

        user.setAmount(user.getAmount() - saleOrderDTO.getTotalAmount());

        Delivery deliveryWaitConfirm = deliveryRepository.findByIndex("ChoXacNhan");

        saleOrder.setDelivery(deliveryWaitConfirm);
        saleOrderRepository.save(saleOrder);

        return ResponseEntity.status(200).body(saleOrder.getOrderItems());
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
