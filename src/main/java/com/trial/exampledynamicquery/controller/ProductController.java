package com.trial.exampledynamicquery.controller;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/productlist/byfilter")
    public List<ProductEntity> getProductListByFilter(ProductEntity productEntity) {
        return productService.getProductListByFilter(productEntity);
    }
}
