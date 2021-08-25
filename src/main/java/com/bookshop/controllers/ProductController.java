package com.bookshop.controllers;

import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.dto.ProductDTO;
import com.bookshop.dto.ProductDetail;
import com.bookshop.exceptions.DuplicateRecordException;
import com.bookshop.exceptions.InvalidException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.CheckValid;
import com.bookshop.helpers.ConvertObject;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Transactional(rollbackFor = Exception.class)
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "page", required = false) Integer pageNum,
                                            @RequestParam(name = "type", required = false) String type,
                                            @RequestParam(name = "search", required = false) String search) {
        if (search != null) {
            List<Product> products = productRepository.findByTitleContaining(search);

            if (products.size() == 0) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(products);
        }
        if (type != null) {
            if (type.compareTo("without-image") == 0) {
                List<Product> products = productRepository.findAll();

                if (products.size() == 0) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok().body(products);
            }
        }
        if (pageNum != null) {
            Page<Product> page = productRepository.findAll(PageRequest.of(pageNum.intValue(), 20));
            //
            List<ProductImage> listProdcutImages = new LinkedList<ProductImage>();

            List<Product> listProducts = page.getContent();
            for (int i = 0; i < listProducts.size(); i++) {
                if (listProducts.get(i).getProductImages().isEmpty()) {
                    continue;
                }
                listProdcutImages.add(listProducts.get(i).getProductImages().get(0));
            }
            //
            if (page.getNumberOfElements() == 0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok().body(listProdcutImages);
        }

        List<Product> products = productRepository.findAll();
        //
        List<ProductImage> listProductImages = new LinkedList<ProductImage>();

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductImages().isEmpty()) {
                continue;
            }
            listProductImages.add(products.get(i).getProductImages().get(0));
        }
        //

        if (products.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(listProductImages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductBySlug(@PathVariable("id") Object id) {
        Product product;
        try {
            Long productId = Long.parseLong((String) id);
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (!optionalProduct.isPresent()) {
                throw new NotFoundException("Not found product with product id " + productId);
            }
            product = optionalProduct.get();
        } catch (Exception e) {
            product = productRepository.findBySlug(id.toString());
        }

        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        ProductDetail productDetail = new ProductDetail(product, product.getProductImages());

        return ResponseEntity.ok().body(productDetail);
    }

    @PostMapping
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDTO productDTO) {
        Product oldProduct = productRepository.findBySlug(ConvertObject.toSlug(productDTO.getTitle()));
        if (oldProduct != null) {
            throw new DuplicateRecordException("Product has already exists");
        }
        Product product = ConvertObject.fromProductDTOToProductDAO(productDTO);

        Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategoryId());

        if (!optionalCategory.isPresent()) {
            throw new NotFoundException("Not found category with category id " + productDTO.getCategoryId());
        }

        product.setCategory(optionalCategory.get());

        CheckValid.checkProduct(product);

        Product newProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> editProduct(@RequestBody ProductDTO productDTO,
                                         @PathVariable("productId") Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found");
        }
        Product product = optionalProduct.get();

        if (productDTO.getTitle() != null) {
            product.setTitle(productDTO.getTitle().trim().replaceAll("\\s+", " "));
            product.setSlug(ConvertObject.toSlug(productDTO.getTitle().trim().replaceAll("\\s+", " ")));
        }
        if (productDTO.getLongDescription() != null) {
            product.setLongDescription(productDTO.getLongDescription().trim().replaceAll("\\s+", " "));
            if (product.getLongDescription().length() > 60) {
                product.setShortDescription(product.getLongDescription().substring(0, 60));
            } else {
                product.setShortDescription(product.getLongDescription());
            }
        }
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).get();
            product.setCategory(category);
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getAuthor() != null) {
            product.setAuthor(productDTO.getAuthor().trim().replaceAll("\\s+", " "));
        }
        if (productDTO.getCurrentNumber() != null) {
            product.setCurrentNumber(productDTO.getCurrentNumber());
        }

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found");
        }

        if (!optionalProduct.get().getProductImages().isEmpty()) {
            throw new InvalidException("Delete failed");
        }

        productRepository.deleteById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(optionalProduct.get());
    }
}
