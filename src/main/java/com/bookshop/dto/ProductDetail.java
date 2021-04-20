package com.bookshop.dto;

import java.util.List;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;

public class ProductDetail {

	private Product product;
	private List<ProductImage> productImages;

	public ProductDetail() {
	}

	public ProductDetail(Product product, List<ProductImage> productImages) {
		super();
		this.product = product;
		this.productImages = productImages;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

}
