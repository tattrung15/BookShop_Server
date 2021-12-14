package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.OrderItem;
import com.bookshop.dao.Product;
import com.bookshop.dto.OrderItemDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.OrderItemService;
import com.bookshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController extends BaseController<OrderItem> {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @PatchMapping("/{orderItemId}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateQuantity(@PathVariable("orderItemId") Long orderItemId,
                                            @RequestBody @Valid OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemService.findById(orderItemId);
        if (orderItem == null) {
            throw new NotFoundException("Not found order item");
        }

        Product product = productService.findById(orderItemDTO.getProductId());
        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        int currentNumber = product.getCurrentNumber() + orderItem.getQuantity();
        if (currentNumber < orderItemDTO.getQuantity()) {
            throw new AppException("Not enough quantity");
        }

        Integer updatedCurrentNumber = (product.getCurrentNumber() + orderItem.getQuantity()) - orderItemDTO.getQuantity();

        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItemService.createOrUpdate(orderItem);

        product.setCurrentNumber(updatedCurrentNumber);
        productService.updateCurrentNumber(product);

        return ResponseEntity.ok().body(orderItem);
    }
}
