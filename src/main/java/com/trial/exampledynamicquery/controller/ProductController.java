// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.controller;

import com.trial.exampledynamicquery.service.ProductCriteria;
import com.trial.exampledynamicquery.service.ProductFilter;
import com.trial.exampledynamicquery.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productlist")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Query by Example: {@code /productlist/byfilter?type=food&name=apple}.
     * With {@code match=contains} the given fields are matched as case-insensitive substrings.
     */
    @GetMapping("/byfilter")
    public List<ProductResponse> byFilter(@RequestParam(required = false) String productNumber,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(defaultValue = "EXACT") ProductFilter.MatchMode match) {
        return productService.findByExample(new ProductFilter(productNumber, name, type), match).stream()
                .map(ProductResponse::from)
                .toList();
    }

    /**
     * Specifications: {@code /productlist/byspec?nameContains=an&types=food,clothes&productNumberPrefix=ba}.
     */
    @GetMapping("/byspec")
    public List<ProductResponse> bySpecification(@RequestParam(required = false) String nameContains,
                                                 @RequestParam(required = false) List<String> types,
                                                 @RequestParam(required = false) String productNumberPrefix) {
        return productService.findBySpecification(new ProductCriteria(nameContains, types, productNumberPrefix)).stream()
                .map(ProductResponse::from)
                .toList();
    }
}
