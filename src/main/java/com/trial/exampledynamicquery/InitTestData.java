package com.trial.exampledynamicquery;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component

public class InitTestData {

        private final Map<String,List<String>> productsByTypeMap=new HashMap<>();

        private final ProductRepository productRepository;

        public InitTestData(ProductRepository productRepository) {
            this.productRepository = productRepository;
            loadProducts();
        }

        private void loadProducts() {
            initSeedData();
            int i=0;
            for (var entry:productsByTypeMap.entrySet()) {
                String type=entry.getKey();
                for (String productName:entry.getValue()) {
                    i++;
                    productRepository.save(new ProductEntity(null, productName+i, productName,type));
                }
            }
        }

        private void initSeedData(){
            productsByTypeMap.put("electronic", Arrays.asList("phone", "laptop", "television", "keyboard", "monitor"));
            productsByTypeMap.put("food", Arrays.asList("cheese", "yogurt", "apple", "butter", "bread", "chocolate", "banana"));
            productsByTypeMap.put("clothes", Arrays.asList("t-shirt", "jacket"));
        }




}
