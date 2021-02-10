package com.bookshop.dto;

public class ProductDTO {
    private String title;

    private String shortDescription;

    private String longDescription;

    private Long categoryId;

    private Long price;

    private String author;

    private Integer currentNumber;

    public ProductDTO() {
    }

    public ProductDTO(String title, String shortDescription, String longDescription, Long categoryId, Long price,
            String author, Integer currentNumber) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.categoryId = categoryId;
        this.price = price;
        this.author = author;
        this.currentNumber = currentNumber;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCurrentNumber() {
        return this.currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }
}
