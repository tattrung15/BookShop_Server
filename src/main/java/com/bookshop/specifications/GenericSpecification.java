package com.bookshop.specifications;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GenericSpecification<T> implements Specification<T> {

    private List<SearchCriteria> listSearchCriteria;
    private JoinCriteria joinCriteria;
    private Sort sort = Sort.by("createdAt").descending();

    public GenericSpecification() {
        this.listSearchCriteria = new ArrayList<>();
        this.joinCriteria = null;
    }

    public void add(SearchCriteria searchCriteria) {
        this.listSearchCriteria.add(searchCriteria);
    }

    public Sort getSort() {
        return this.sort;
    }

    public void buildSort(String field, SortType sortType) {
        if (sortType.equals(SortType.ASC)) {
            this.sort = Sort.by(field).ascending();
        } else if (sortType.equals(SortType.DESC)) {
            this.sort = Sort.by(field).descending();
        }
    }

    public void buildJoin(JoinCriteria joinCriteria) {
        this.joinCriteria = joinCriteria;
    }

    public GenericSpecification<T> getBasicQuery(HttpServletRequest request) {
        GenericSpecification<T> specification = new GenericSpecification<>();

        for (Map.Entry<String, String[]> item : request.getParameterMap().entrySet()) {
            if (item.getKey().contains("like")) {
                specification.add(new SearchCriteria(StringUtils.substringBetween(item.getKey(), "[", "]"),
                        StringUtils.substringBetween(Arrays.toString(item.getValue()), "[", "]"),
                        SearchOperation.LIKE));
            }
            if (item.getKey().contains("equal")) {
                try {
                    specification.add(new SearchCriteria(StringUtils.substringBetween(item.getKey(), "[", "]"),
                            Long.parseLong(StringUtils.substringBetween(Arrays.toString(item.getValue()), "[", "]")),
                            SearchOperation.EQUAL));
                } catch (NumberFormatException e) {
                    specification.add(new SearchCriteria(StringUtils.substringBetween(item.getKey(), "[", "]"),
                            StringUtils.substringBetween(Arrays.toString(item.getValue()), "[", "]"),
                            SearchOperation.EQUAL));
                }
            }
            if (item.getKey().contains("sort")) {
                String field = StringUtils.substringBetween(Arrays.toString(item.getValue()), "[", "]");
                if (field.contains("-")) {
                    specification.buildSort(field.substring(1), SortType.DESC);
                } else {
                    specification.buildSort(field, SortType.ASC);
                }
            }
        }

        return specification;
    }

    private Predicate buildJoinPredicate(SearchOperation searchOperation, String key, Object value,
                                         CriteriaBuilder builder,
                                         Join<Object, Object> join) {
        if (searchOperation.equals(SearchOperation.GREATER_THAN)) {
            return builder.greaterThan(join.get(key), value.toString());
        } else if (searchOperation.equals(SearchOperation.LESS_THAN)) {
            return builder.lessThan(join.get(key), value.toString());
        } else if (searchOperation.equals(SearchOperation.GREATER_THAN_EQUAL)) {
            return builder.greaterThanOrEqualTo(join.get(key), value.toString());
        } else if (searchOperation.equals(SearchOperation.LESS_THAN_EQUAL)) {
            return builder.lessThanOrEqualTo(join.get(key), value.toString());
        } else if (searchOperation.equals(SearchOperation.NOT_EQUAL)) {
            return builder.notEqual(join.get(key), value.toString());
        } else if (searchOperation.equals(SearchOperation.EQUAL)) {
            return builder.equal(join.get(key), value.toString());
        } else if (searchOperation.equals(SearchOperation.LIKE)) {
            return builder.like(builder.lower(join.get(key)), "%" + value.toString().toLowerCase() + "%");
        } else if (searchOperation.equals(SearchOperation.LIKE_END)) {
            return builder.like(builder.lower(join.get(key)), value.toString().toLowerCase() + "%");
        } else if (searchOperation.equals(SearchOperation.NULL)) {
            return builder.isNull(join.get(key));
        } else if (searchOperation.equals(SearchOperation.NOT_NULL)) {
            return builder.isNotNull(join.get(key));
        }
        return null;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (this.joinCriteria != null) {
            Join<Object, Object> join = root.join(this.joinCriteria.getJoinColumnName(), this.joinCriteria.getJoinType());
            predicates.add(
                    this.buildJoinPredicate(
                            this.joinCriteria.getSearchOperation(),
                            this.joinCriteria.getKey(),
                            this.joinCriteria.getValue(),
                            builder,
                            join
                    )
            );
        }

        for (SearchCriteria criteria : listSearchCriteria) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.LIKE)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.LIKE_END)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.NULL)) {
                predicates.add(builder.isNull(root.get(criteria.getKey())));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_NULL)) {
                predicates.add(builder.isNotNull(root.get(criteria.getKey())));
            }
        }

        query.distinct(true);

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
