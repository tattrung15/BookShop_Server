package com.bookshop.dto;

public class PaginationDTO {

	private Object data;
	private Integer totalOfPage;

	public PaginationDTO() {
	}

	public PaginationDTO(Object data, Integer totalOfPage) {
		super();
		this.data = data;
		this.totalOfPage = totalOfPage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getTotalOfPage() {
		return totalOfPage;
	}

	public void setTotalOfPage(Integer totalOfPage) {
		this.totalOfPage = totalOfPage;
	}

}
