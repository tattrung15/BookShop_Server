package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductRate;
import com.bookshop.dao.User;
import com.bookshop.dto.ProductRateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.ProductRateService;
import com.bookshop.services.ProductService;
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
@RequestMapping("/api/product-rates")
public class ProductRateController extends BaseController<ProductRate> {

    @Autowired
    private ProductRateService productRateService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getListProductRatesByProductId(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            @RequestParam(name = "productId") Long productId,
            HttpServletRequest request) {

        GenericSpecification<ProductRate> specification = new GenericSpecification<ProductRate>().getBasicQuery(request);
        specification.add(new SearchCriteria("product", productId, SearchOperation.EQUAL));

        PaginateDTO<ProductRate> paginateProductRates = productRateService.getList(page, perPage, specification);

        return this.resPagination(paginateProductRates);
    }

    @GetMapping("/detail")
    @PreAuthorize("@userAuthorizer.isMember(authentication)")
    public ResponseEntity<?> getProductRateByProductId(@RequestParam(name = "productId") Long productId, HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        GenericSpecification<ProductRate> specification = new GenericSpecification<ProductRate>().getBasicQuery(request);
        specification.add(new SearchCriteria("product", productId, SearchOperation.EQUAL));
        specification.add(new SearchCriteria("user", requestedUser.getId(), SearchOperation.EQUAL));

        ProductRate productRate = productRateService.findOne(specification);

        return this.resSuccess(productRate);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.isMember(authentication)")
    public ResponseEntity<?> createProductRate(@RequestBody @Valid ProductRateDTO productRateDTO, HttpServletRequest request) {
        User requestedUser = (User) request.getAttribute("user");

        Product product = productService.findById(productRateDTO.getProductId());

        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        GenericSpecification<ProductRate> specification = new GenericSpecification<ProductRate>().getBasicQuery(request);
        specification.add(new SearchCriteria("product", productRateDTO.getProductId(), SearchOperation.EQUAL));
        specification.add(new SearchCriteria("user", requestedUser.getId(), SearchOperation.EQUAL));

        ProductRate productRate = productRateService.findOne(specification);

        if (productRate != null) {
            throw new AppException("You has already rated");
        }

        productRateDTO.setUserId(requestedUser.getId());

        ProductRate newProductRate = productRateService.create(productRateDTO);

        return this.resSuccess(newProductRate);
    }
}
