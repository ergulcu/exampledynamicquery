// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.service;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductServiceImplTest {

    @Autowired
    private ProductRepository repository;

    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        repository.saveAll(List.of(
                new ProductEntity("phone1", "phone", "electronic"),
                new ProductEntity("laptop2", "laptop", "electronic"),
                new ProductEntity("apple3", "apple", "food"),
                new ProductEntity("banana4", "banana", "food"),
                new ProductEntity("jacket5", "jacket", "clothes"),
                new ProductEntity("100%_6", "odd_name", "misc")));
        service = new ProductServiceImpl(repository);
    }

    @Test
    void exampleWithOnlyTypeMatchesThatType() {
        List<ProductEntity> result = service.findByExample(new ProductFilter(null, null, "food"),
                ProductFilter.MatchMode.EXACT);

        assertThat(result).extracting(ProductEntity::getName).containsExactly("apple", "banana");
    }

    @Test
    void exampleCombinesFieldsWithAnd() {
        List<ProductEntity> result = service.findByExample(new ProductFilter(null, "apple", "electronic"),
                ProductFilter.MatchMode.EXACT);

        assertThat(result).isEmpty();
    }

    @Test
    void emptyExampleReturnsEverything() {
        List<ProductEntity> result = service.findByExample(new ProductFilter(" ", null, ""),
                ProductFilter.MatchMode.EXACT);

        assertThat(result).hasSize(6);
    }

    @Test
    void containsModeIsCaseInsensitive() {
        List<ProductEntity> result = service.findByExample(new ProductFilter(null, "AN", null),
                ProductFilter.MatchMode.CONTAINS);

        assertThat(result).extracting(ProductEntity::getName).containsExactly("banana");
    }

    @Test
    void specificationCanSelectSeveralTypes() {
        List<ProductEntity> result = service.findBySpecification(
                new ProductCriteria(null, List.of("food", "clothes"), null));

        assertThat(result).extracting(ProductEntity::getType).containsOnly("food", "clothes").hasSize(3);
    }

    @Test
    void specificationCombinesNameAndPrefix() {
        List<ProductEntity> result = service.findBySpecification(new ProductCriteria("A", null, "ba"));

        assertThat(result).extracting(ProductEntity::getProductNumber).containsExactly("banana4");
    }

    @Test
    void specificationTreatsLikeWildcardsLiterally() {
        assertThat(service.findBySpecification(new ProductCriteria("_", null, null)))
                .extracting(ProductEntity::getName).containsExactly("odd_name");
        assertThat(service.findBySpecification(new ProductCriteria(null, null, "100%")))
                .extracting(ProductEntity::getName).containsExactly("odd_name");
    }

    @Test
    void specificationWithoutCriteriaReturnsEverything() {
        assertThat(service.findBySpecification(new ProductCriteria(null, List.of(), null))).hasSize(6);
    }
}
