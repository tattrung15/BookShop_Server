package com.bookshop.dto;

public class ProductDTO {
    private String title;
    
    private String longDescription;

    private Long categoryId;

    private Long price;

    private String author;

    private Integer currentNumber;

    private Integer numberOfPage;

    public ProductDTO() {
    }

    public ProductDTO(String title, String longDescription, Long categoryId, Long price,
            String author, Integer currentNumber, Integer numberOfPage) {
        this.title = title;
        this.longDescription = longDescription;
        this.categoryId = categoryId;
        this.price = price;
        this.author = author;
        this.currentNumber = currentNumber;
        this.numberOfPage = numberOfPage;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getNumberOfPage() {
        return this.numberOfPage;
    }

    public void setNumberOfPage(Integer numberOfPage) {
        this.numberOfPage = numberOfPage;
    }
}
