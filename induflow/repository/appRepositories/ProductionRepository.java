package com.compoldata.induflow.repository.appRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.model.Product;

@Repository
public interface ProductionRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByProductCode(String productCode);
}
