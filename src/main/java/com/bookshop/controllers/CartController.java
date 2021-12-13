package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Delivery;
import com.bookshop.dao.Product;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dao.User;
import com.bookshop.dto.OrderItemDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.DeliveryService;
import com.bookshop.services.OrderItemService;
import com.bookshop.services.ProductService;
import com.bookshop.services.SaleOrderService;
import com.bookshop.specifications.GenericSpecification;
import com.bookshop.specifications.SearchCriteria;
import com.bookshop.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/carts")
public class CartController extends BaseController<Object> {

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleOrderService saleOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    @PreAuthorize("@userAuthorizer.isMember(authentication)")
    public ResponseEntity<?> createCart(@RequestBody @Valid OrderItemDTO orderItemDTO, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");

        Product product = productService.findById(orderItemDTO.getProductId()).orElse(null);
        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        Delivery delivery = deliveryService.findByAddedToCartState();

        GenericSpecification<SaleOrder> specification = new GenericSpecification<>();
        specification.add(new SearchCriteria("user", user.getId(), SearchOperation.EQUAL));
        specification.add(new SearchCriteria("delivery", delivery.getId(), SearchOperation.EQUAL));
        SaleOrder saleOrder = saleOrderService.findOne(specification);

        return this.resSuccess(saleOrder);
    }
}
