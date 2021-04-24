package com.bookshop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.exceptions.NotFoundException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product-images")
@Transactional(rollbackFor = Exception.class)
public class ProductImageController {

	@Autowired
	private Cloudinary cloudinary;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping
	public ResponseEntity<?> getProductImages(@RequestParam(name = "page", required = false) Integer pageNum,
			@RequestParam(name = "pid", required = false) Long productId,
			@RequestParam(name = "search", required = false) String search) {
		if (search != null) {
			List<Product> products = productRepository.findByTitleContaining(search);

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
		if (pageNum != null && productId == null) {
			Page<Product> pageProducts = productRepository.findAll(PageRequest.of(pageNum.intValue(), 20));
			if (pageProducts.getNumberOfElements() == 0) {
				return ResponseEntity.noContent().build();
			}
			List<Product> products = pageProducts.getContent();
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
	public ResponseEntity<?> getProductBestSelling() {
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
	public ResponseEntity<?> getProductImageById(@PathVariable("imageId") Long imageId) {
		Optional<ProductImage> optionalProductImage = productImageRepository.findById(imageId);
		if (!optionalProductImage.isPresent()) {
			throw new NotFoundException("Product Image not found");
		}
		return ResponseEntity.ok().body(optionalProductImage.get());
	}

	@PostMapping
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
}
