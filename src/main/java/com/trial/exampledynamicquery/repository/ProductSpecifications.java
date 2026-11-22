// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.repository;

import com.trial.exampledynamicquery.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Locale;

/**
 * Reusable predicates. Each one returns {@link Specification#unrestricted()} when its input is empty,
 * so they can be combined freely with {@link Specification#allOf}.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<ProductEntity> nameContains(String text) {
        if (isBlank(text)) {
            return Specification.unrestricted();
        }
        String pattern = "%" + escapeLike(text.toLowerCase(Locale.ROOT)) + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), pattern, '\\');
    }

    public static Specification<ProductEntity> typeIn(Collection<String> types) {
        if (types == null || types.isEmpty()) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> root.get("type").in(types);
    }

    public static Specification<ProductEntity> productNumberStartsWith(String prefix) {
        if (isBlank(prefix)) {
            return Specification.unrestricted();
        }
        String pattern = escapeLike(prefix) + "%";
        return (root, query, cb) -> cb.like(root.get("productNumber"), pattern, '\\');
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    /** Escapes the LIKE wildcards so that user input is matched literally. */
    static String escapeLike(String value) {
        return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
