package com.trial.exampledynamicquery.service;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Override
    public List<ProductEntity> getProductListByFilter(ProductEntity productEntity) {
        return productRepository.findAll(Example.of(productEntity));
    }
}
