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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/sale-orders")
public class SaleOrderController extends BaseController<SaleOrder> {

    @Autowired
    private SaleOrderService saleOrderService;

    @GetMapping
    public ResponseEntity<?> getListSaleOrders(@RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "perPage", required = false) Integer perPage,
                                               HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");
        GenericSpecification<SaleOrder> specification = new GenericSpecification<SaleOrder>().getBasicQuery(request);
        specification.add(new SearchCriteria("user", requestedUser.getId(), SearchOperation.EQUAL));

        PaginateDTO<SaleOrder> paginateSaleOrders = saleOrderService.getList(page, perPage, specification);

        return this.resPagination(paginateSaleOrders);
    }
}
