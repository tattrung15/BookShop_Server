package com.bookshop.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.JoinType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JoinCriteria {
    private SearchOperation searchOperation;
    private String joinColumnName;
    private String key;
    private Object value;
    private JoinType joinType;
}
