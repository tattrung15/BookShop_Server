package com.bookshop.base;

import java.util.List;

public interface BaseService<E, D> {
    Long countAll();

    E create(D t);

    List<E> createMany(Iterable<E> entities);
}
