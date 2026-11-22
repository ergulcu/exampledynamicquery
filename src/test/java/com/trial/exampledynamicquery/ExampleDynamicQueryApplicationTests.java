// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery;

import com.trial.exampledynamicquery.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;

/** Starts the whole application with the demo data and calls both endpoints over HTTP. */
@SpringBootTest
@AutoConfigureMockMvc
class ExampleDynamicQueryApplicationTests {

    @Autowired
    private MockMvcTester mvc;

    @Autowired
    private ProductRepository repository;

    @Test
    void seedDataIsLoaded() {
        assertThat(repository.count()).isEqualTo(14);
    }

    @Test
    void byFilterReturnsAllFood() {
        assertThat(mvc.get().uri("/productlist/byfilter?type=food"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.length()").isEqualTo(7);
    }

    @Test
    void bySpecFindsByPrefixAcrossTypes() {
        assertThat(mvc.get().uri("/productlist/byspec?productNumberPrefix=b"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[*].name").asArray().containsExactlyInAnyOrder("butter", "bread", "banana");
    }
}
