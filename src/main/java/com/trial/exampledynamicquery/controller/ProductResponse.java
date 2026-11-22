// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.controller;

import com.trial.exampledynamicquery.entity.ProductEntity;

public record ProductResponse(String id, String productNumber, String name, String type) {

    static ProductResponse from(ProductEntity entity) {
        return new ProductResponse(entity.getId(), entity.getProductNumber(), entity.getName(), entity.getType());
    }
}
