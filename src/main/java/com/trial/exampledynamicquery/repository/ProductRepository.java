// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.repository;

import com.trial.exampledynamicquery.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The id of {@link ProductEntity} is a UUID string, so the repository is typed with {@code String}.
 * {@link JpaRepository} brings Query by Example, {@link JpaSpecificationExecutor} brings Specifications.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity> {
}
