package com.bookshop.dto;

import com.bookshop.dao.Category;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class GetCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String slug;
    private Boolean isAuthor;
    private Set<Category> linkedCategories;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
