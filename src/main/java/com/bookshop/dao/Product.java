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

	@CreationTimestamp
	private Timestamp createAt;

	@UpdateTimestamp
	private Timestamp updateAt;

	public Product() {
	}

	public Product(Long id, String title, String shortDescription, String longDescription, Category category,
			List<ProductImage> productImages, List<OrderItem> orderItems, List<ProductRating> productRatings,
			Long price, String author, Integer currentNumber, Integer numberOfPage, String slug, Timestamp createAt,
			Timestamp updateAt) {
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
		this.createAt = createAt;
		this.updateAt = updateAt;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductImage> getProductImages() {
		return this.productImages;
	}

	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<ProductRating> getProductRatings() {
		return this.productRatings;
	}

	public void setProductRatings(List<ProductRating> productRatings) {
		this.productRatings = productRatings;
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

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Timestamp getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return this.updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}
}
