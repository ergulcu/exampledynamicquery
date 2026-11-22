// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.service;

import com.trial.exampledynamicquery.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> findByExample(ProductFilter filter, ProductFilter.MatchMode matchMode);

    List<ProductEntity> findBySpecification(ProductCriteria criteria);
}
