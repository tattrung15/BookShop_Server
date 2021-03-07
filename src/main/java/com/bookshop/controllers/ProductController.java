package com.bookshop.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.dao.Category;
import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.dto.ProductDTO;
import com.bookshop.exceptions.DuplicateRecordException;
import com.bookshop.exceptions.NotFoundException;
import com.bookshop.helpers.ConvertObject;
import com.bookshop.repositories.CategoryRepository;
import com.bookshop.repositories.ProductRepository;

@RestController
@RequestMapping("/api/products")
@Transactional(rollbackFor = Exception.class)
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public ResponseEntity<?> getAllProducts(@RequestParam(name = "page", required = false) Integer pageNum) {
		if (pageNum != null) {
			Page<Product> page = productRepository.findAll(PageRequest.of(pageNum.intValue(), 10));
			//
			List<ProductImage> listProdcutImages = new LinkedList<ProductImage>();

			List<Product> listProducts = page.getContent();
			for (int i = 0; i < listProducts.size(); i++) {
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
		List<ProductImage> listProdcutImages = new LinkedList<ProductImage>();

		for (int i = 0; i < products.size(); i++) {
			listProdcutImages.add(products.get(i).getProductImages().get(0));
		}
		//

		if (products.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(listProdcutImages);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductBySlug(@PathVariable("id") Object id) {
		Product product;
		try {
			Long productId = Long.parseLong((String) id);
			product = productRepository.findById(productId).get();
		} catch (Exception e) {
			product = productRepository.findBySlug(id.toString());
		}

		if (product == null) {
			throw new NotFoundException("Product not found");
		}

		ProductImage productImage = product.getProductImages().get(0);

		return ResponseEntity.ok().body(productImage);
	}

	@PostMapping
	@PreAuthorize("@userAuthorizer.authorizeAdmin(authentication, 'ADMIN')")
	public ResponseEntity<?> createNewProduct(@RequestBody ProductDTO productDTO) {
		Product oldProduct = productRepository.findBySlug(ConvertObject.toSlug(productDTO.getTitle()));
		if (oldProduct != null) {
			throw new DuplicateRecordException("Product has already exists");
		}
		Product product = ConvertObject.fromProductDTOToProductDAO(productDTO);
		Category category = categoryRepository.findById(productDTO.getCategoryId()).get();
		product.setCategory(category);

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
			product.setShortDescription(product.getLongDescription().substring(0, 60));
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
		productRepository.deleteById(productId);

		return ResponseEntity.status(HttpStatus.OK).body(optionalProduct.get());
	}
}
