package com.trial.exampledynamicquery.service;

import com.trial.exampledynamicquery.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getProductListByFilter(ProductEntity productEntity);
}
