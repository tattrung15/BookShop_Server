package com.bookshop.controllers;

import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.dto.ProductDetail;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.repositories.ProductImageRepository;
import com.bookshop.repositories.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getProductImages(@RequestParam(name = "page", required = false) Integer pageNum,
                                              @RequestParam(name = "pid", required = false) Long productId,
                                              @RequestParam(name = "search", required = false) String search,
                                              @RequestParam(name = "category", required = false) String slugCategory,
                                              @RequestParam(name = "type", required = false) String type) {
        if (type != null) {
            if (search != null) {
                if (type.compareTo("have-image") == 0) {
                    List<Product> products = productRepository.findByTitleContaining(search);

                    List<ProductDetail> productsHaveImages = new LinkedList<>();

                    for (int i = 0; i < products.size(); i++) {
                        if (!products.get(i).getProductImages().isEmpty()) {
                            ProductDetail productDetail = new ProductDetail(products.get(i), products.get(i).getProductImages());
                            productsHaveImages.add(productDetail);
                        }
                    }

                    if (productsHaveImages.size() == 0) {
                        return ResponseEntity.noContent().build();
                    }
                    return ResponseEntity.ok().body(productsHaveImages);
                }
                if (type.compareTo("no-image") == 0) {
                    List<Product> products = productRepository.findByTitleContaining(search);

                    List<ProductDetail> productsNoImages = new LinkedList<>();

                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getProductImages().isEmpty()) {
                            ProductDetail productDetail = new ProductDetail(products.get(i), products.get(i).getProductImages());
                            productsNoImages.add(productDetail);
                        }
                    }

                    if (productsNoImages.size() == 0) {
                        return ResponseEntity.noContent().build();
                    }
                    return ResponseEntity.ok().body(productsNoImages);
                }
            }
            if (type.compareTo("have-image") == 0) {
                List<Product> products = productRepository.findAll();

                List<ProductDetail> productsHaveImages = new LinkedList<>();

                for (int i = 0; i < products.size(); i++) {
                    if (!products.get(i).getProductImages().isEmpty()) {
                        ProductDetail productDetail = new ProductDetail(products.get(i), products.get(i).getProductImages());
                        productsHaveImages.add(productDetail);
                    }
                }

                if (productsHaveImages.size() == 0) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok().body(productsHaveImages);
            }
            if (type.compareTo("no-image") == 0) {
                List<Product> products = productRepository.findAll();

                List<ProductDetail> productsNoImages = new LinkedList<>();

                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProductImages().isEmpty()) {
                        ProductDetail productDetail = new ProductDetail(products.get(i), products.get(i).getProductImages());
                        productsNoImages.add(productDetail);
                    }
                }

                if (productsNoImages.size() == 0) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok().body(productsNoImages);
            }
        }
        if (slugCategory != null) {
            Category category = categoryRepository.findBySlug(slugCategory);

            if (category == null) {
                throw new NotFoundException("Not found category by slug " + slugCategory);
            }
            Page<Product> pageProducts = productRepository.findByCategoryId(category.getId(), PageRequest.of(0, 4));

            List<Product> products = pageProducts.getContent();

            List<ProductImage> productImages = new LinkedList<>();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductImages().isEmpty()) {
                    continue;
                }
                ProductImage productImage = new ProductImage();
                productImage.setId(products.get(i).getProductImages().get(0).getId());
                productImage.setLink(products.get(i).getProductImages().get(0).getLink());
                productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
                productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
                productImage.setProduct(products.get(i));
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        }
        if (search != null) {
            List<Product> products = productRepository.findByTitleContaining(search);

            List<ProductImage> productImages = new LinkedList<>();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductImages().isEmpty()) {
                    continue;
                }
                ProductImage productImage = new ProductImage();
                productImage.setId(products.get(i).getProductImages().get(0).getId());
                productImage.setLink(products.get(i).getProductImages().get(0).getLink());
                productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
                productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
                productImage.setProduct(products.get(i));
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        }
        if (pageNum != null && productId == null) {
            Page<Product> pageProducts = productRepository.findAll(PageRequest.of(pageNum.intValue(), 20));
            if (pageProducts.getNumberOfElements() == 0) {
                return ResponseEntity.noContent().build();
            }
            List<Product> products = pageProducts.getContent();
            List<ProductImage> productImages = new LinkedList<>();
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductImages().isEmpty()) {
                    continue;
                }
                ProductImage productImage = new ProductImage();
                productImage.setId(products.get(i).getProductImages().get(0).getId());
                productImage.setLink(products.get(i).getProductImages().get(0).getLink());
                productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
                productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
                productImage.setProduct(products.get(i));
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        }

        if (productId != null) {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (!optionalProduct.isPresent()) {
                throw new NotFoundException("Product Image with productId not found");
            }
            List<ProductImage> list = productImageRepository.findByProduct(optionalProduct.get());
            if (list.size() == 0) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(list);
        }

        List<Product> products = productRepository.findAll();
        List<ProductImage> productImages = new LinkedList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductImages().isEmpty()) {
                continue;
            }
            ProductImage productImage = new ProductImage();
            productImage.setId(products.get(i).getProductImages().get(0).getId());
            productImage.setLink(products.get(i).getProductImages().get(0).getLink());
            productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
            productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
            productImage.setProduct(products.get(i));
            productImages.add(productImage);
        }
        return ResponseEntity.ok().body(productImages);
    }

    @GetMapping("/best-selling")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getProductsBestSelling() {
        List<Product> products = productRepository.findAllByOrderByQuantityPurchasedDesc(PageRequest.of(0, 4));
        if (products.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        List<ProductImage> productImages = new LinkedList<>();
        for (int i = 0; i < products.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setId(products.get(i).getProductImages().get(0).getId());
            productImage.setLink(products.get(i).getProductImages().get(0).getLink());
            productImage.setCreateAt(products.get(i).getProductImages().get(0).getCreateAt());
            productImage.setUpdateAt(products.get(i).getProductImages().get(0).getUpdateAt());
            productImage.setProduct(products.get(i));
            productImages.add(productImage);
        }
        return ResponseEntity.ok().body(productImages);
    }

    @GetMapping("/{imageId}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getProductImageById(@PathVariable("imageId") Long imageId) {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(imageId);
        if (!optionalProductImage.isPresent()) {
            throw new NotFoundException("Product Image not found");
        }
        return ResponseEntity.ok().body(optionalProductImage.get());
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> createNewImages(@RequestParam("productId") Long productId,
                                             @RequestParam("files") MultipartFile[] files) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found with productId");
        }
        Product product = optionalProduct.get();
        for (int i = 0; i < files.length; i++) {
            Map<?, ?> cloudinaryMap = cloudinary.uploader().upload(files[i].getBytes(), ObjectUtils.emptyMap());
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setLink(cloudinaryMap.get("secure_url").toString());
            productImage.setPublicId(cloudinaryMap.get("public_id").toString());
            ProductImage newProductImage = productImageRepository.save(productImage);
            productImages.add(newProductImage);
        }
        return ResponseEntity.status(201).body(productImages);
    }

    @PatchMapping
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> editProductImageByProductId(@RequestParam("productId") Long productId,
                                                         @RequestParam(name = "files") MultipartFile[] files) throws IOException {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found with productId");
        }
        Product product = optionalProduct.get();
        List<ProductImage> list = product.getProductImages();

        List<ProductImage> newProductImages;

        if (files.length == list.size()) {
            for (int i = 0; i < files.length; i++) {
                cloudinary.uploader().destroy(list.get(i).getPublicId(), ObjectUtils.emptyMap());
                Map<?, ?> cloudinaryMap = cloudinary.uploader().upload(files[i].getBytes(), ObjectUtils.emptyMap());
                list.get(i).setLink(cloudinaryMap.get("secure_url").toString());
                list.get(i).setPublicId(cloudinaryMap.get("public_id").toString());
            }
        } else if (files.length < list.size()) {
            int i;
            for (i = 0; i < files.length; i++) {
                cloudinary.uploader().destroy(list.get(i).getPublicId(), ObjectUtils.emptyMap());
                Map<?, ?> cloudinaryMap = cloudinary.uploader().upload(files[i].getBytes(), ObjectUtils.emptyMap());
                list.get(i).setLink(cloudinaryMap.get("secure_url").toString());
                list.get(i).setPublicId(cloudinaryMap.get("public_id").toString());
            }

            for (int j = i; j < list.size(); j++) {
                cloudinary.uploader().destroy(list.get(i).getPublicId(), ObjectUtils.emptyMap());

                productImageRepository.deleteById(list.get(i).getId());

                list.remove(i);
            }
        } else {
            int i;
            for (i = 0; i < list.size(); i++) {
                cloudinary.uploader().destroy(list.get(i).getPublicId(), ObjectUtils.emptyMap());
                Map<?, ?> cloudinaryMap = cloudinary.uploader().upload(files[i].getBytes(), ObjectUtils.emptyMap());
                list.get(i).setLink(cloudinaryMap.get("secure_url").toString());
                list.get(i).setPublicId(cloudinaryMap.get("public_id").toString());
            }

            for (int j = i; j < files.length; j++) {
                Map<?, ?> cloudinaryMap = cloudinary.uploader().upload(files[i].getBytes(), ObjectUtils.emptyMap());
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setLink(cloudinaryMap.get("secure_url").toString());
                productImage.setPublicId(cloudinaryMap.get("public_id").toString());
                ProductImage newProductImage = productImageRepository.save(productImage);
                list.add(newProductImage);
            }
        }

        newProductImages = productImageRepository.saveAll(list);
        return ResponseEntity.status(200).body(newProductImages);
    }

    @DeleteMapping("/{imageId}")
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> deleteProductImage(@PathVariable("imageId") Long imageId) throws IOException {
        Optional<ProductImage> optionalProductImage = productImageRepository.findById(imageId);
        if (!optionalProductImage.isPresent()) {
            throw new NotFoundException("Product Image not found");
        }
        ProductImage productImage = optionalProductImage.get();

        cloudinary.uploader().destroy(productImage.getPublicId(), ObjectUtils.emptyMap());
        productImageRepository.deleteById(imageId);

        return ResponseEntity.status(200).body(optionalProductImage.get());
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> deleteProductImagesByProductId(@PathVariable("productId") Long productId) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found by product id " + productId);
        }
        List<ProductImage> productImages = optionalProduct.get().getProductImages();

        for (int i = 0; i < productImages.size(); i++) {
            ProductImage productImage = productImages.get(i);
            cloudinary.uploader().destroy(productImage.getPublicId(), ObjectUtils.emptyMap());
            productImageRepository.deleteById(productImage.getId());
        }

        return ResponseEntity.status(200).body(productImages);
    }
}
