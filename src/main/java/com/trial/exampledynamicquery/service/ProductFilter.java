// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.service;

/**
 * Probe for Query by Example. Null fields are ignored; the others are matched
 * exactly or, with {@link MatchMode#CONTAINS}, as case-insensitive substrings.
 */
public record ProductFilter(String productNumber, String name, String type) {

    public enum MatchMode {
        EXACT,
        CONTAINS
    }
}
