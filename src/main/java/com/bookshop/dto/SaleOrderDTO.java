package com.bookshop.dto;

public class SaleOrderDTO {
	private Long userId;
	private Long saleOrderId;
	private Long totalAmount;

	public SaleOrderDTO() {
	}

	public SaleOrderDTO(Long userId, Long saleOrderId, Long totalAmount) {
		super();
		this.userId = userId;
		this.saleOrderId = saleOrderId;
		this.totalAmount = totalAmount;
	}

	public Long getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(Long saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

}
