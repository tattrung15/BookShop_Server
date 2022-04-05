package com.bookshop.controllers;

import com.bookshop.base.BaseController;
import com.bookshop.constants.Common;
import com.bookshop.constants.ProductTypeEnum;
import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.ProductUpdateDTO;
import com.bookshop.dto.pagination.PaginateDTO;
import com.bookshop.exceptions.AppException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.services.CategoryService;
import com.bookshop.services.ProductImageService;
import com.bookshop.services.ProductService;
import com.bookshop.services.StorageService;
import com.bookshop.specifications.GenericSpecification;
import com.bookshop.specifications.JoinCriteria;
import com.bookshop.specifications.SearchOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.JoinType;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public ResponseEntity<?> getListProducts(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "perPage", required = false) Integer perPage,
            @RequestParam(name = "productType", required = false) String productType,
            @RequestParam(name = "ids", required = false) List<Integer> ids,
            HttpServletRequest request) {

        GenericSpecification<Product> specification = new GenericSpecification<Product>().getBasicQuery(request);

        if (productType != null) {
            if (productType.equals(ProductTypeEnum.HAVE_IMAGE)) {
                specification.buildJoin(new JoinCriteria(SearchOperation.NOT_NULL, "productImages", "id", null, JoinType.LEFT));
            } else if (productType.equals(ProductTypeEnum.NO_IMAGE)) {
                specification.buildJoin(new JoinCriteria(SearchOperation.NULL, "productImages", "id", null, JoinType.LEFT));
            } else if (productType.equals(ProductTypeEnum.NO_IMAGE_ALL)) {
                specification.buildJoin(new JoinCriteria(SearchOperation.NULL, "productImages", "id", null, JoinType.LEFT));
                List<Product> products = productService.findAll(specification);
                return this.resListSuccess(products);
            }
        }

        if (ids != null && ids.size() > 0) {
            if (page == null || page <= 0) {
                page = 1;
            }
            if (perPage == null || perPage <= 0) {
                perPage = Common.PAGING_DEFAULT_LIMIT;
            }
            String numberString = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
            List<Product> products = productService.findByIdsWithOrder(ids, numberString, PageRequest.of(page - 1, perPage));
            return this.resListSuccess(products);
        }

        PaginateDTO<Product> paginateProducts = productService.getList(page, perPage, specification);

        return this.resPagination(paginateProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByIdOrSlug(@PathVariable("id") Object id) {
        Product product;
        try {
            Long productId = Long.parseLong((String) id);
            product = productService.findById(productId);

        } catch (Exception e) {
            product = productService.findBySlug(id.toString());
        }

        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        return this.resSuccess(product);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> createNewProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product oldProduct = productService.findBySlug(productDTO.getTitle());
        if (oldProduct != null) {
            throw new AppException("Product has already exists");
        }

        Category category = categoryService.findById(productDTO.getCategoryId());

        if (category == null) {
            throw new NotFoundException("Not found category");
        }

        Product product = productService.create(productDTO);

        return this.resSuccess(product);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductUpdateDTO productUpdateDTO,
                                           @PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);

        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        if (productUpdateDTO.getCategoryId() != null) {
            Category category = categoryService.findById(productUpdateDTO.getCategoryId());
            if (category == null) {
                throw new NotFoundException("Not found category");
            }
        }

        Product savedProduct = productService.update(productUpdateDTO, product);

        return this.resSuccess(savedProduct);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        if (!product.getProductImages().isEmpty() || !product.getProductRates().isEmpty() || !product.getOrderItems().isEmpty()) {
            throw new AppException("Cannot delete product");
        }

        productService.deleteById(productId);

        return this.resSuccess(product);
    }

    @DeleteMapping("/{productId}/product-images")
    @PreAuthorize("@userAuthorizer.isAdmin(authentication)")
    @SecurityRequirement(name = "Authorization")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteProductImageByProductId(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);

        if (product == null) {
            throw new NotFoundException("Not found product");
        }

        List<ProductImage> productImageList = product.getProductImages();

        productImageService.deleteByProductId(productId);
        storageService.deleteFilesByPrefix(String.valueOf(productId), Common.PRODUCT_IMAGE_UPLOAD_PATH);

        return this.resListSuccess(productImageList);
    }
}
