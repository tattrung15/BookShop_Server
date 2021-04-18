package com.bookshop.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Nationalized
	@Column(name = "title", nullable = false)
	private String title;

	@Nationalized
	@Column(name = "short_description")
	private String shortDescription;

	@Nationalized
	@Column(name = "long_description", nullable = false)
	@Length(max = 1000)
	private String longDescription;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProductImage> productImages;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProductRating> productRatings;

	@Column(name = "price", nullable = false)
	private Long price;

	@Nationalized
	@Column(name = "author")
	private String author;

	@Column(name = "current_number", nullable = false)
	private Integer currentNumber;

	@Column(name = "number_of_page", nullable = false)
	private Integer numberOfPage;

	@Column(name = "slug", nullable = false)
	private String slug;

	@Column(name = "quantity_purchased")
	private Integer quantityPurchased;

	@CreationTimestamp
	private Timestamp createAt;

	@UpdateTimestamp
	private Timestamp updateAt;

	public Product() {
	}

	public Product(Long id, String title, String shortDescription, @Length(max = 1000) String longDescription,
			Category category, List<ProductImage> productImages, List<OrderItem> orderItems,
			List<ProductRating> productRatings, Long price, String author, Integer currentNumber, Integer numberOfPage,
			String slug, Integer quantityPurchased, Timestamp createAt, Timestamp updateAt) {
		super();
		this.id = id;
		this.title = title;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.category = category;
		this.productImages = productImages;
		this.orderItems = orderItems;
		this.productRatings = productRatings;
		this.price = price;
		this.author = author;
		this.currentNumber = currentNumber;
		this.numberOfPage = numberOfPage;
		this.slug = slug;
		this.quantityPurchased = quantityPurchased;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductImage> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<ProductRating> getProductRatings() {
		return productRatings;
	}

	public void setProductRatings(List<ProductRating> productRatings) {
		this.productRatings = productRatings;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}

	public Integer getNumberOfPage() {
		return numberOfPage;
	}

	public void setNumberOfPage(Integer numberOfPage) {
		this.numberOfPage = numberOfPage;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Integer getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(Integer quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
