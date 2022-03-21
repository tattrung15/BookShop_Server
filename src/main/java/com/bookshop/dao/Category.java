package com.bookshop.dao;

import com.bookshop.helpers.StringHelper;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(nullable = false)
    private String name;

    @Nationalized
    @Length(max = 100000)
    private String description;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private Boolean isAuthor;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    @JsonManagedReference
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Category> linkedCategories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @PrePersist
    public void prePersist() {
        this.slug = StringHelper.toSlug(this.name);
        if (this.isAuthor == null) {
            this.isAuthor = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.slug = StringHelper.toSlug(this.name);
        if (this.isAuthor == null) {
            this.isAuthor = false;
        }
    }
}
