package com.bookshop.dto;

import com.bookshop.dao.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
