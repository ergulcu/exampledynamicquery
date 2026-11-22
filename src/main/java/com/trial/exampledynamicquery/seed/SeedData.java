// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.seed;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads demo products once the application has started (not in a constructor, so it runs
 * after the context and the schema are ready). Turn it off with {@code app.seed.enabled=false}.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SeedData implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SeedData.class);

    static final Map<String, List<String>> PRODUCTS_BY_TYPE = new LinkedHashMap<>();

    static {
        PRODUCTS_BY_TYPE.put("electronic", List.of("phone", "laptop", "television", "keyboard", "monitor"));
        PRODUCTS_BY_TYPE.put("food", List.of("cheese", "yogurt", "apple", "butter", "bread", "chocolate", "banana"));
        PRODUCTS_BY_TYPE.put("clothes", List.of("t-shirt", "jacket"));
    }

    private final ProductRepository productRepository;

    public SeedData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            return;
        }
        List<ProductEntity> products = new ArrayList<>();
        int number = 0;
        for (var entry : PRODUCTS_BY_TYPE.entrySet()) {
            for (String name : entry.getValue()) {
                number++;
                products.add(new ProductEntity(name + number, name, entry.getKey()));
            }
        }
        productRepository.saveAll(products);
        log.info("Loaded {} demo products", products.size());
    }
}
