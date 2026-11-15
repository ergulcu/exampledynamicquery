// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.controller;

import com.trial.exampledynamicquery.entity.ProductEntity;
import com.trial.exampledynamicquery.service.ProductCriteria;
import com.trial.exampledynamicquery.service.ProductFilter;
import com.trial.exampledynamicquery.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void byFilterPassesTheProbeAndMatchMode() {
        when(productService.findByExample(any(), any()))
                .thenReturn(List.of(new ProductEntity("apple3", "apple", "food")));

        assertThat(mvc.get().uri("/productlist/byfilter?type=food&name=app&match=CONTAINS"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].productNumber").isEqualTo("apple3");

        verify(productService).findByExample(new ProductFilter(null, "app", "food"), ProductFilter.MatchMode.CONTAINS);
    }

    @Test
    void byFilterDefaultsToExactMatch() {
        when(productService.findByExample(any(), any())).thenReturn(List.of());

        assertThat(mvc.get().uri("/productlist/byfilter")).hasStatusOk().bodyJson().isEqualTo("[]");

        verify(productService).findByExample(new ProductFilter(null, null, null), ProductFilter.MatchMode.EXACT);
    }

    @Test
    void byFilterRejectsAnUnknownMatchMode() {
        assertThat(mvc.get().uri("/productlist/byfilter?match=fuzzy")).hasStatus4xxClientError();
    }

    @Test
    void bySpecSplitsTheTypeList() {
        when(productService.findBySpecification(any())).thenReturn(List.of());

        assertThat(mvc.get().uri("/productlist/byspec?types=food,clothes&nameContains=an")).hasStatusOk();

        verify(productService).findBySpecification(eq(new ProductCriteria("an", List.of("food", "clothes"), null)));
    }
}
