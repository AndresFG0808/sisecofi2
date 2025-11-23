package com.sisecofi.reportedocumental.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CommonPageHelper<T> extends Page<T>{

	@Override
    default boolean hasContent() {
        return !getContent().isEmpty();
    }

    @Override
    default boolean hasNext() {
        return false;
    }

    @Override
    default boolean hasPrevious() {
        return false;
    }

    @Override
    default Pageable nextPageable() {
        return null;
    }

    @Override
    default Pageable previousPageable() {
        return null;
    }

    @Override
    default Sort getSort() {
        return Sort.unsorted();
    }
}