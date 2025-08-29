package com.compoldata.induflow.repository.appRepositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.compoldata.induflow.model.ProductionOrderLog;

public interface ProductionOrderLogRepository extends JpaRepository<ProductionOrderLog, Long> {
    @Query("SELECT l FROM ProductionOrderLog l WHERE l.productionOrder.id = :orderId")
    List<ProductionOrderLog> findLogsByProductionOrderId(@Param("orderId") Long orderId);
}
