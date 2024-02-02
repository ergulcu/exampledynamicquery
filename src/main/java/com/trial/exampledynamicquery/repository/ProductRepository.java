package com.trial.exampledynamicquery.repository;

import com.trial.exampledynamicquery.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
}
