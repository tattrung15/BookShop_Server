package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Delivery;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dao.User;
import com.bookshop.dto.DeliveryDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.DeliveryService;
import com.bookshop.services.SaleOrderService;
import com.bookshop.specifications.GenericSpecification;
import com.bookshop.specifications.SearchCriteria;
import com.bookshop.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/sale-orders")
public class SaleOrderController extends BaseController<SaleOrder> {

    @Autowired
    private SaleOrderService saleOrderService;

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping
    @PreAuthorize("@userAuthorizer.isMember(authentication)")
    public ResponseEntity<?> getListSaleOrdersForMember(@RequestParam(name = "page", required = false) Integer page,
                                                        @RequestParam(name = "perPage", required = false) Integer perPage,
                                                        HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        GenericSpecification<SaleOrder> specification = new GenericSpecification<SaleOrder>().getBasicQuery(request);
        specification.add(new SearchCriteria("user", requestedUser.getId(), SearchOperation.EQUAL));

        PaginateDTO<SaleOrder> paginateSaleOrders = saleOrderService.getList(page, perPage, specification);

        return this.resPagination(paginateSaleOrders);
    }

    @GetMapping("/admin")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> getListSaleOrdersForAdmin(@RequestParam(name = "page", required = false) Integer page,
                                                       @RequestParam(name = "perPage", required = false) Integer perPage,
                                                       HttpServletRequest request) {

        GenericSpecification<SaleOrder> specification = new GenericSpecification<SaleOrder>().getBasicQuery(request);

        PaginateDTO<SaleOrder> paginateSaleOrders = saleOrderService.getList(page, perPage, specification);

        return this.resPagination(paginateSaleOrders);
    }

    @GetMapping("/{saleOrderId}")
    @PreAuthorize("@userAuthorizer.isMember(authentication)")
    public ResponseEntity<?> getSaleOrderByIdForMember(@PathVariable("saleOrderId") Long saleOrderId, HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        GenericSpecification<SaleOrder> specification = new GenericSpecification<SaleOrder>().getBasicQuery(request);
        specification.add(new SearchCriteria("user", requestedUser.getId(), SearchOperation.EQUAL));
        specification.add(new SearchCriteria("id", saleOrderId, SearchOperation.EQUAL));

        SaleOrder saleOrder = saleOrderService.findOne(specification);

        return this.resSuccess(saleOrder);
    }

    @GetMapping("/admin/{saleOrderId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> getSaleOrderByIdForAdmin(@PathVariable("saleOrderId") Long saleOrderId) {
        SaleOrder saleOrder = saleOrderService.findById(saleOrderId);

        return this.resSuccess(saleOrder);
    }

    @PatchMapping("/{saleOrderId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    public ResponseEntity<?> editSaleOrderDelivery(@PathVariable("saleOrderId") Long saleOrderId,
                                                   @RequestBody @Valid DeliveryDTO deliveryDTO) {
        SaleOrder saleOrder = saleOrderService.findById(saleOrderId);

        if (saleOrder == null) {
            throw new NotFoundException("Not found sale order");
        }

        Delivery delivery = deliveryService.findById(deliveryDTO.getDeliveryId());

        if (delivery == null) {
            throw new NotFoundException("Not found delivery");
        }

        saleOrder.setDelivery(delivery);
        SaleOrder savedSaleOrder = saleOrderService.update(saleOrder);

        return this.resSuccess(savedSaleOrder);
    }
}
