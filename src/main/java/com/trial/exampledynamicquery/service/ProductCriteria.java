// Copyright 2024 Mehmet ERGULCU
// SPDX-License-Identifier: Apache-2.0

package com.trial.exampledynamicquery.service;

import java.util.List;

/**
 * Criteria for the Specification based search. Unlike Query by Example it can express
 * "one of these types" or "starts with", which a probe object cannot.
 */
public record ProductCriteria(String nameContains, List<String> types, String productNumberPrefix) {
}
