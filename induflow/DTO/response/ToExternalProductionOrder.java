package com.compoldata.induflow.DTO.response;

import java.math.BigDecimal;

import com.compoldata.induflow.integration.sigen.model.ExternalProductionOrder;

public record ToExternalProductionOrder(
    String productionOrderNumber,
    String materialCode,
    String productionOrderStatus,
    BigDecimal amount,
    String description
) {
    public static ToExternalProductionOrder toExternalProductionOrder(ExternalProductionOrder productionOrder){
            
        return new ToExternalProductionOrder(
                productionOrder.getProductionOrderNumber(),
                productionOrder.getMaterialCode(),
                productionOrder.getProductionOrderStatus(),
                productionOrder.getAmount(),
                productionOrder.getDescription()
             );
    }
}
