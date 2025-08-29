package com.compoldata.induflow.repository.appRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.compoldata.induflow.model.ProductionOrder;
import com.compoldata.induflow.model.enums.ProductionOrderStatus;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder,Long> {
        List<ProductionOrder> findByStatus(ProductionOrderStatus status);
        Optional<ProductionOrder> findByProductionOrderNumber(String  productionOrderNumber);
        List<ProductionOrder> findByStatusIn(List<ProductionOrderStatus> statuses) ;

}
