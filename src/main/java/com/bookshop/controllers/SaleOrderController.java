package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.SaleOrder;
import com.bookshop.dao.User;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.services.SaleOrderService;
import com.bookshop.specifications.GenericSpecification;
import com.bookshop.specifications.SearchCriteria;
import com.bookshop.specifications.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/sale-orders")
public class SaleOrderController extends BaseController<SaleOrder> {

    @Autowired
    private SaleOrderService saleOrderService;

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
}
