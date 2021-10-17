package com.bookshop.base;

public interface BaseService<E, D> {
    Long countAll();

    E create(D t);
}
