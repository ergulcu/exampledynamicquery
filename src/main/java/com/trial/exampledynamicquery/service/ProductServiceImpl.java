// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.service;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.repository.ProductRepository;
import com.trial.exampledynamicquery.repository.ProductSpecifications;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private static final Sort DEFAULT_SORT = Sort.by("type", "productNumber");

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> findByExample(ProductFilter filter, ProductFilter.MatchMode matchMode) {
        ProductEntity probe = new ProductEntity(blankToNull(filter.productNumber()), blankToNull(filter.name()),
                blankToNull(filter.type()));
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        if (matchMode == ProductFilter.MatchMode.CONTAINS) {
            matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();
        }
        return productRepository.findAll(Example.of(probe, matcher), DEFAULT_SORT);
    }

    @Override
    public List<ProductEntity> findBySpecification(ProductCriteria criteria) {
        Specification<ProductEntity> spec = Specification.allOf(
                ProductSpecifications.nameContains(criteria.nameContains()),
                ProductSpecifications.typeIn(criteria.types()),
                ProductSpecifications.productNumberStartsWith(criteria.productNumberPrefix()));
        return productRepository.findAll(spec, DEFAULT_SORT);
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
