package com.bookshop.dto;

import java.sql.Timestamp;
import java.util.List;

import com.bookshop.dao.Delivery;
import com.bookshop.dao.OrderItem;
import com.bookshop.dao.ProductImage;
import com.bookshop.dao.User;

public class SaleOrderResponseDTO {
	private Long id;

	private String customerAddress;

	private String phone;

	private User user;

	private Delivery delivery;

	private List<OrderItem> orderItems;

	private ProductImage productImage;

	private Timestamp createAt;

	private Timestamp updateAt;

	public SaleOrderResponseDTO() {
	}

	public SaleOrderResponseDTO(Long id, String customerAddress, String phone, User user, Delivery delivery,
			List<OrderItem> orderItems, ProductImage productImage, Timestamp createAt, Timestamp updateAt) {
		super();
		this.id = id;
		this.customerAddress = customerAddress;
		this.phone = phone;
		this.user = user;
		this.delivery = delivery;
		this.orderItems = orderItems;
		this.productImage = productImage;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

}
