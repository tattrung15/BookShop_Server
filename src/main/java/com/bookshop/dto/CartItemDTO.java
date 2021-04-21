package com.bookshop.dto;

import com.bookshop.dao.Product;
import com.bookshop.dao.ProductImage;
import com.bookshop.dao.SaleOrder;

public class CartItemDTO {
	private Long orderItemId;
	private Product product;
	private SaleOrder saleOrder;
	private Integer quantity;
	private ProductImage productImage;

	public CartItemDTO() {
	}

	public CartItemDTO(Long orderItemId, Product product, SaleOrder saleOrder, Integer quantity,
			ProductImage productImage) {
		super();
		this.orderItemId = orderItemId;
		this.product = product;
		this.saleOrder = saleOrder;
		this.quantity = quantity;
		this.productImage = productImage;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

}
