package com.bookshop.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.criteria.JoinType;

@AllArgsConstructor
@Data
public class JoinCriteria {
    private SearchOperation searchOperation;
    private String joinColumnName;
    private String key;
    private Object value;
    private JoinType joinType;
}
