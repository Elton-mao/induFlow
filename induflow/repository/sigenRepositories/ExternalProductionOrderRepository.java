package com.compoldata.induflow.repository.sigenRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.integration.sigen.model.ExternalProductionOrder;

@Repository
public interface ExternalProductionOrderRepository extends JpaRepository<ExternalProductionOrder, String> {
    Optional<ExternalProductionOrder> findByProductionOrderNumber(String productionOrderNumber);
} 
